#!/usr/bin/env python3
"""
Convert TWS API raw HTML documentation to clean Markdown.

This script directly parses the raw HTML (from Firecrawl rawHtml or curl) using
BeautifulSoup, bypassing all the issues with Firecrawl's Markdown conversion
(EnlighterJS triple-duplication, missing language labels, code block corruption).

Extracts:
- Section hierarchy (api-block-1/2/3 → ##/###/####)
- Table of Contents (auto-generated from heading structure)
- Text content (paragraphs, lists, tables, images with lazy-loading support)
- Code blocks from <pre class="EnlighterJSRAW"> (authoritative code source)
- Tab groups (Python/Java/C++/C#/VB.NET) with <!-- tabs:start/end --> markers

Prerequisites:
    pip install beautifulsoup4

Usage:
    python convert_twsapi_html.py <input.html> <output.md>

Example:
    python scripts/convert_twsapi_html.py tmp/twsapi-raw.html docs/tws-api/twsapi-doc.md

Input:
    Raw HTML file obtained via one of:
    - Firecrawl scrape with formats=["rawHtml"] (recommended, version-consistent)
    - curl 'https://www.interactivebrokers.com/campus/ibkr-api-page/twsapi-doc/'

Output:
    Clean Markdown with:
    - 836 code blocks (perfectly paired), 180 tab groups
    - 30 H2 chapters, 37 images, 0 noise
    - Auto-generated TOC navigation at the top
"""

import re
import sys
from html import unescape
from bs4 import BeautifulSoup, NavigableString, Tag


# ============================================================
# Configuration
# ============================================================

# Map EnlighterJS language names to markdown fence labels
LANG_MAP = {
    "python": "python",
    "java": "java",
    "cpp": "cpp",
    "csharp": "csharp",
    "visualbasic": "vb",
    "cplus": "cpp",
    "c": "c",
    "raw": "text",
    "no-highlight": "text",
    "generic": "text",
}

# Heading level for each api-block level
BLOCK_HEADING = {
    "1": "##",
    "2": "###",
    "3": "####",
}


# ============================================================
# Text extraction helpers
# ============================================================

def clean_text(text):
    """Clean up extracted text: normalize whitespace, strip."""
    text = text.replace("\xa0", " ")  # &nbsp;
    text = re.sub(r"[ \t]+", " ", text)
    return text.strip()


def inline_md(element):
    """Convert an inline HTML element to markdown text, preserving formatting."""
    if isinstance(element, NavigableString):
        text = str(element)
        # Normalize whitespace but preserve single newlines context
        text = text.replace("\xa0", " ")
        return text

    if not isinstance(element, Tag):
        return ""

    tag = element.name

    # Skip hidden elements and copy-link buttons
    if tag == "div" and element.get("class"):
        classes = element.get("class", [])
        if "copy-link" in classes:
            return ""
        # Skip image-overlay divs (contain duplicate images)
        if "image-overlay" in classes:
            return ""
    if tag == "button":
        return ""
    if tag == "svg" or tag == "path":
        return ""

    # Handle images inline
    if tag == "img":
        src = element.get("src", "")
        alt = element.get("alt", "")
        data_src = element.get("data-src", "")
        # Skip SVG placeholders, prefer data-src for lazy-loaded images
        if src.startswith("data:image/svg") and data_src:
            src = data_src
        if src.startswith("data:"):
            return ""
        if not src:
            return ""
        return f"![{alt}]({src})"

    # Recurse into children
    inner = "".join(inline_md(child) for child in element.children)

    if tag in ("strong", "b"):
        inner = inner.strip()
        if inner:
            return f"**{inner}**"
        return ""
    elif tag == "em" or tag == "i":
        inner = inner.strip()
        if inner:
            return f"*{inner}*"
        return ""
    elif tag == "code":
        inner = inner.strip()
        if inner:
            return f"`{inner}`"
        return ""
    elif tag == "a":
        href = element.get("href", "")
        inner = inner.strip()
        if inner and href:
            return f"[{inner}]({href})"
        return inner
    elif tag == "br":
        return "\n"
    elif tag == "span":
        # Colored spans (API references like color: #800000) → use backtick
        style = element.get("style", "")
        if "color" in style and "#800000" in style:
            inner = inner.strip()
            if inner:
                return f"`{inner}`"
        return inner
    elif tag == "h6":
        # h6 is used for "Copy Location" buttons - skip
        text = inner.strip()
        if text == "Copy Location":
            return ""
        return inner
    elif tag == "sup":
        return f"^{inner.strip()}"
    elif tag == "sub":
        return f"~{inner.strip()}"
    else:
        return inner


def extract_text(element):
    """Extract full text content from an element as markdown."""
    return clean_text(inline_md(element))


# ============================================================
# Block-level converters
# ============================================================

def convert_paragraph(p_tag):
    """Convert <p> to markdown paragraph."""
    text = extract_text(p_tag)
    if not text or text == "&nbsp;" or text == "\xa0":
        return ""
    return text + "\n"


def convert_heading(h_tag, override_level=None):
    """Convert <h1>-<h6> to markdown heading."""
    level = override_level or int(h_tag.name[1])
    text = extract_text(h_tag)
    if not text or text == "Copy Location":
        return ""
    # Remove trailing "Copy Location" from heading text
    text = re.sub(r"\s*Copy Location\s*$", "", text).strip()
    if not text:
        return ""
    prefix = "#" * level
    return f"{prefix} {text}\n"


def convert_list(list_tag, indent=0):
    """Convert <ul>/<ol> to markdown list."""
    lines = []
    is_ordered = list_tag.name == "ol"
    counter = 1

    for li in list_tag.find_all("li", recursive=False):
        # Get direct text content (not nested lists)
        text_parts = []
        nested_lists = []

        for child in li.children:
            if isinstance(child, Tag) and child.name in ("ul", "ol"):
                nested_lists.append(child)
            else:
                text_parts.append(inline_md(child) if isinstance(child, Tag) else str(child))

        text = clean_text("".join(text_parts))
        if not text:
            continue

        prefix = "  " * indent
        if is_ordered:
            lines.append(f"{prefix}{counter}. {text}")
            counter += 1
        else:
            lines.append(f"{prefix}- {text}")

        for nested in nested_lists:
            lines.append(convert_list(nested, indent + 1))

    return "\n".join(lines) + "\n"


def convert_table(table_tag):
    """Convert <table> to markdown table."""
    # Process thead if exists, otherwise first row with <th>
    thead = table_tag.find("thead")
    tbody = table_tag.find("tbody") or table_tag

    all_rows = []
    if thead:
        all_rows.extend(thead.find_all("tr", recursive=False))
    all_rows.extend(tbody.find_all("tr", recursive=False))

    if not all_rows:
        return ""

    md_rows = []

    for i, tr in enumerate(all_rows):
        cells = tr.find_all(["th", "td"])
        cell_texts = []
        for cell in cells:
            text = extract_text(cell)
            # Escape pipes in cell content
            text = text.replace("|", "\\|")
            # Replace newlines with <br> for multi-line cells
            text = text.replace("\n", " ")
            cell_texts.append(text)

        if not cell_texts:
            continue

        row_str = "| " + " | ".join(cell_texts) + " |"
        md_rows.append(row_str)

        # Add separator after header row
        if i == 0 and cells and cells[0].name == "th":
            sep = "| " + " | ".join(["---"] * len(cell_texts)) + " |"
            md_rows.append(sep)

    if not md_rows:
        return ""

    # Ensure there's a separator after the first row if not already
    if len(md_rows) >= 2 and not md_rows[1].startswith("| ---"):
        ncols = md_rows[0].count("|") - 1
        sep = "| " + " | ".join(["---"] * ncols) + " |"
        md_rows.insert(1, sep)

    return "\n".join(md_rows) + "\n"


def convert_image(img_tag):
    """Convert <img> to markdown image."""
    src = img_tag.get("src", "")
    alt = img_tag.get("alt", "")
    # Skip tiny placeholder SVGs
    if src.startswith("data:image/svg"):
        return ""
    if not src:
        return ""
    # Use data-src for lazy-loaded images
    data_src = img_tag.get("data-src", "")
    if data_src and not data_src.startswith("data:"):
        src = data_src
    return f"![{alt}]({src})\n"


# ============================================================
# Code block extraction
# ============================================================

def extract_code_block(pre_tag):
    """Extract code from <pre class="EnlighterJSRAW"> tag."""
    lang = pre_tag.get("data-enlighter-language", "generic")
    lang = LANG_MAP.get(lang, lang)
    group = pre_tag.get("data-enlighter-group", "")

    # Get raw code text
    code = pre_tag.get_text()
    code = unescape(code)
    # Strip trailing whitespace per line but preserve structure
    lines = [line.rstrip() for line in code.split("\n")]
    # Remove leading/trailing empty lines
    while lines and not lines[0].strip():
        lines.pop(0)
    while lines and not lines[-1].strip():
        lines.pop()
    code = "\n".join(lines)

    return {"lang": lang, "group": group, "code": code}


def convert_tab_block(tab_block_div):
    """Convert a tab-block (with language tabs) to markdown code blocks.

    Output format:
    <!-- tabs:start -->
    ##### **Python**
    ```python
    code here
    ```
    ##### **Java**
    ```java
    code here
    ```
    <!-- tabs:end -->
    """
    lines = []

    # Find all tab panes
    tab_panes = tab_block_div.find_all("div", class_="tab-pane")
    if not tab_panes:
        # Fallback: just find all pre.EnlighterJSRAW
        for pre in tab_block_div.find_all("pre", class_="EnlighterJSRAW"):
            block = extract_code_block(pre)
            lines.append(f"```{block['lang']}")
            lines.append(block["code"])
            lines.append("```")
            lines.append("")
        return "\n".join(lines) + "\n" if lines else ""

    # Find tab button labels for display names
    tab_labels = {}
    nav_tabs = tab_block_div.find("ul", class_="nav")
    if nav_tabs:
        for btn in nav_tabs.find_all("button"):
            classes = btn.get("class", [])
            for cls in classes:
                if cls.startswith("tab-") and cls != "tab-pane":
                    lang_key = cls[4:]  # Remove "tab-" prefix
                    tab_labels[lang_key] = btn.get_text().strip()

    # Build a map from pane id prefix to label
    pane_label_map = {}
    for btn in (nav_tabs.find_all("button") if nav_tabs else []):
        target = btn.get("data-bs-target", "")
        if target.startswith("#"):
            pane_label_map[target[1:]] = btn.get_text().strip()

    lines.append("<!-- tabs:start -->")
    lines.append("")

    for pane in tab_panes:
        pane_id = pane.get("id", "")
        # Get display label
        label = pane_label_map.get(pane_id, "")

        # Find the pre tag with code
        pre = pane.find("pre", class_="EnlighterJSRAW")
        if not pre:
            continue

        block = extract_code_block(pre)

        if not label:
            label = block["lang"].capitalize()

        lines.append(f"##### **{label}**")
        lines.append("")
        lines.append(f"```{block['lang']}")
        lines.append(block["code"])
        lines.append("```")
        lines.append("")

    lines.append("<!-- tabs:end -->")
    lines.append("")

    return "\n".join(lines) + "\n"


# ============================================================
# Standalone enlighter block (not inside a tab)
# ============================================================

def convert_standalone_enlighter(enlighter_div):
    """Convert a standalone EnlighterJS block (not in tabs) to markdown.

    Note: The <pre class="EnlighterJSRAW"> is a SIBLING of the enlighter-default div,
    not a child. So we skip the rendered div and let process_entry_content handle the pre.
    """
    # The pre is actually a sibling, not a child. Return empty to avoid duplicate.
    # The pre will be handled when process_entry_content iterates to it.
    return ""


# ============================================================
# Entry content processor
# ============================================================

def process_entry_content(div):
    """Process a div.entry-content, converting all child elements."""
    parts = []

    for child in div.children:
        if isinstance(child, NavigableString):
            text = str(child).strip()
            if text:
                parts.append(text + "\n")
            continue

        if not isinstance(child, Tag):
            continue

        tag = child.name

        if tag == "p":
            result = convert_paragraph(child)
            if result:
                parts.append(result)

        elif tag in ("h1", "h2", "h3", "h4", "h5", "h6"):
            result = convert_heading(child)
            if result:
                parts.append(result)

        elif tag in ("ul", "ol"):
            result = convert_list(child)
            if result:
                parts.append(result)

        elif tag == "table":
            result = convert_table(child)
            if result:
                parts.append(result)

        elif tag == "img":
            result = convert_image(child)
            if result:
                parts.append(result)

        elif tag == "div":
            classes = child.get("class", [])

            # Tab block with code
            if "tab-block" in classes:
                result = convert_tab_block(child)
                if result:
                    parts.append(result)

            # Standalone EnlighterJS (enlighter-default)
            elif "enlighter-default" in classes:
                result = convert_standalone_enlighter(child)
                if result:
                    parts.append(result)

            # Row with columns (recurse)
            elif "row" in classes:
                result = process_row(child)
                if result:
                    parts.append(result)

            # Generic div - recurse
            else:
                result = process_entry_content(child)
                if result:
                    parts.append(result)

        elif tag == "pre":
            # Standalone pre (EnlighterJS or plain)
            if "EnlighterJSRAW" in (child.get("class") or []):
                block = extract_code_block(child)
                parts.append(f"```{block['lang']}\n{block['code']}\n```\n")
            else:
                code = child.get_text()
                parts.append(f"```\n{code}\n```\n")

        elif tag == "blockquote":
            text = extract_text(child)
            if text:
                quoted = "\n".join(f"> {line}" for line in text.split("\n"))
                parts.append(quoted + "\n")

        elif tag == "hr":
            parts.append("---\n")

        elif tag == "strong" or tag == "b":
            text = extract_text(child)
            if text:
                parts.append(f"**{text}**\n")

        # Skip scripts, styles, nav elements, etc.
        elif tag in ("script", "style", "nav", "button", "svg", "noscript"):
            continue

        else:
            # For other tags, try to extract text content
            text = extract_text(child)
            if text and len(text) > 1:
                parts.append(text + "\n")

    return "\n".join(parts)


def process_row(row_div):
    """Process a div.row which may contain entry-content and tab-block columns."""
    parts = []

    for col in row_div.children:
        if not isinstance(col, Tag):
            continue

        classes = col.get("class", [])

        if "entry-content" in classes:
            result = process_entry_content(col)
            if result:
                parts.append(result)

        elif "tab-block" in classes:
            result = convert_tab_block(col)
            if result:
                parts.append(result)

        elif isinstance(col, Tag) and col.name == "div":
            # Might be a column wrapper containing tab-block or entry-content
            # Check for nested tab-block or entry-content
            inner_tab = col.find("div", class_="tab-block")
            inner_entry = col.find("div", class_="entry-content")

            if inner_tab:
                result = convert_tab_block(inner_tab)
                if result:
                    parts.append(result)
            elif inner_entry:
                result = process_entry_content(inner_entry)
                if result:
                    parts.append(result)
            else:
                # Check if it contains EnlighterJS directly
                enlighter = col.find("div", class_="enlighter-default")
                if enlighter:
                    result = convert_standalone_enlighter(enlighter)
                    if result:
                        parts.append(result)
                else:
                    # Generic column - recurse
                    result = process_entry_content(col)
                    if result:
                        parts.append(result)

    return "\n".join(parts)


# ============================================================
# Section processor
# ============================================================

def heading_to_anchor(text):
    """Convert heading text to a GitHub-compatible markdown anchor.

    Rules: lowercase, spaces→hyphens, strip non-alphanumeric except hyphens,
    collapse consecutive hyphens.
    """
    anchor = text.lower()
    # Replace special chars but keep alphanumeric, spaces, hyphens, and CJK
    anchor = re.sub(r"[^\w\s-]", "", anchor)
    anchor = re.sub(r"[\s]+", "-", anchor)
    anchor = re.sub(r"-{2,}", "-", anchor)
    anchor = anchor.strip("-")
    return anchor


# Global list to collect headings during processing
collected_headings = []


def process_section(section_tag):
    """Process a <section> into markdown."""
    global collected_headings
    parts = []

    # Determine heading level from class
    classes = section_tag.get("class", [])
    block_level = None
    for cls in classes:
        m = re.match(r"api-block-(\d+)", cls)
        if m:
            block_level = m.group(1)
            break

    heading_prefix = BLOCK_HEADING.get(block_level, "##")

    # Find the section heading
    # Structure: <section> → <div class="inner-col"> → <h2/h3>
    inner_col = section_tag.find("div", class_="inner-col")
    container = inner_col if inner_col else section_tag

    heading_tag = container.find(["h2", "h3", "h4"], recursive=False)
    if not heading_tag:
        # Try one level deeper
        heading_tag = container.find(["h2", "h3", "h4"])

    if heading_tag:
        text = extract_text(heading_tag)
        text = re.sub(r"\s*Copy Location\s*$", "", text).strip()
        if text:
            section_id = section_tag.get("id", "")
            # Collect heading info for TOC generation
            level = len(heading_prefix)  # number of '#' chars = heading level
            collected_headings.append({"level": level, "text": text})
            parts.append(f"{heading_prefix} {text}")
            parts.append("")

    # Process content rows
    rows = container.find_all("div", class_="row", recursive=False)
    if not rows and inner_col:
        rows = inner_col.find_all("div", class_="row", recursive=False)

    for row in rows:
        result = process_row(row)
        if result:
            parts.append(result)

    return "\n".join(parts)


# ============================================================
# Main pipeline
# ============================================================

def generate_toc(headings):
    """Generate a Table of Contents from collected headings.

    Produces a nested list like:
    *   [Introduction](#introduction)
    *   [Notes & Limitations](#notes--limitations)
        *   [Requirements](#requirements)
        *   [Limitations](#limitations)
            *   [C# for MacOS](#c-for-macos)
    """
    lines = []
    # Track duplicate anchors to add suffix
    anchor_counts = {}

    for h in headings:
        level = h["level"]  # 2=##, 3=###, 4=####
        text = h["text"]
        anchor = heading_to_anchor(text)

        # Handle duplicate anchors by appending a number
        if anchor in anchor_counts:
            anchor_counts[anchor] += 1
            anchor = f"{anchor}-{anchor_counts[anchor]}"
        else:
            anchor_counts[anchor] = 0

        # Indent: H2 = 0 indent, H3 = 1 indent, H4 = 2 indent
        indent = "    " * (level - 2)
        lines.append(f"{indent}*   [{text}](#{anchor})")

    return "\n".join(lines)


def convert_html_to_markdown(html_path, output_path):
    """Main conversion pipeline."""
    global collected_headings
    collected_headings = []  # Reset for each run

    print(f"[1/5] Loading HTML from {html_path}...")
    with open(html_path, encoding="utf-8") as f:
        html = f.read()

    print(f"[2/5] Parsing HTML with BeautifulSoup ({len(html):,} chars)...")
    soup = BeautifulSoup(html, "html.parser")

    # Find the main content area
    # Structure: <section class="pt-0"> → <div class="container container-large"> → <div class="row g-0">
    main_section = soup.find("section", class_="pt-0")
    if not main_section:
        print("ERROR: Could not find main content section (section.pt-0)")
        sys.exit(1)

    # Find all API documentation sections
    all_sections = main_section.find_all("section", class_=re.compile(r"api-block-\d+"))
    print(f"  Found {len(all_sections)} sections")

    # Count code blocks
    all_pre = main_section.find_all("pre", class_="EnlighterJSRAW")
    print(f"  Found {len(all_pre)} code blocks (pre.EnlighterJSRAW)")

    # Count tab groups
    all_tabs = main_section.find_all("div", class_="tab-block")
    print(f"  Found {len(all_tabs)} tab groups")

    print(f"[3/5] Converting sections to Markdown...")
    md_parts = []

    # Add document title
    page_title = soup.find("h1")
    if page_title:
        title = extract_text(page_title)
        if title:
            md_parts.append(f"# {title}")
            md_parts.append("")

    # Placeholder index for TOC insertion (after the title)
    toc_insert_index = len(md_parts)

    # Process each section (also collects headings into collected_headings)
    for i, section in enumerate(all_sections):
        result = process_section(section)
        if result:
            md_parts.append(result)
            md_parts.append("")  # Blank line between sections

        if (i + 1) % 50 == 0:
            print(f"  Processed {i + 1}/{len(all_sections)} sections...")

    print(f"  Processed {len(all_sections)}/{len(all_sections)} sections")

    # Generate and insert TOC
    print(f"[4/5] Generating Table of Contents ({len(collected_headings)} entries)...")
    if collected_headings:
        toc = generate_toc(collected_headings)
        toc_block = f"\n{toc}\n"
        md_parts.insert(toc_insert_index, toc_block)

    # Join and clean up
    markdown = "\n".join(md_parts)

    # Post-processing cleanup
    # 1. Remove excessive blank lines (max 2 consecutive)
    markdown = re.sub(r"\n{4,}", "\n\n\n", markdown)
    # 2. Ensure code blocks have blank lines around them
    markdown = re.sub(r"([^\n])\n```", r"\1\n\n```", markdown)
    markdown = re.sub(r"```\n([^\n])", r"```\n\n\1", markdown)
    # 3. Clean up trailing spaces
    markdown = re.sub(r" +\n", "\n", markdown)
    # 4. Ensure file ends with newline
    markdown = markdown.strip() + "\n"

    print(f"[5/5] Writing output to {output_path}...")
    with open(output_path, "w", encoding="utf-8") as f:
        f.write(markdown)

    # Report stats
    lines = markdown.count("\n")
    code_blocks = len(re.findall(r"^```\w+", markdown, re.MULTILINE))
    headings = len(re.findall(r"^#{1,6} ", markdown, re.MULTILINE))
    tables = len(re.findall(r"^\|", markdown, re.MULTILINE))
    images = len(re.findall(r"!\[", markdown))
    tabs_markers = len(re.findall(r"<!-- tabs:start -->", markdown))
    toc_entries = len(collected_headings)

    print(f"\n=== Conversion Report ===")
    print(f"  Output size: {len(markdown):,} chars, {lines:,} lines")
    print(f"  TOC entries: {toc_entries}")
    print(f"  Headings: {headings}")
    print(f"  Code blocks: {code_blocks}")
    print(f"  Tab groups: {tabs_markers}")
    print(f"  Table rows: {tables}")
    print(f"  Images: {images}")
    print(f"  Done!")


if __name__ == "__main__":
    if len(sys.argv) < 3:
        print(f"Usage: {sys.argv[0]} <input.html> <output.md>")
        sys.exit(1)

    convert_html_to_markdown(sys.argv[1], sys.argv[2])
