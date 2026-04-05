#!/usr/bin/env python3
"""
Clean IBKR Guides Flex pages and write to docs/flex/.

Usage (run from repository root):
    python3 external-resource/ibkr-api/scripts/clean_ibkr_flex.py

Or with custom source/target directories:
    python3 external-resource/ibkr-api/scripts/clean_ibkr_flex.py \
        --src .firecrawl --dst external-resource/ibkr-api/docs/flex

The script reads raw Firecrawl scrape outputs from SRC_DIR, cleans IBKR Guides
page noise (header navigation, legal footer, UI artifacts), replaces cross-reference
URLs with local relative paths, and writes the cleaned Markdown to DST_DIR.
"""

import re, os, argparse

# source filename → target filename mapping
# Source files are expected under SRC_DIR (default: .firecrawl/)
FILES = {
    "ibkr-flex-overview.md":     "flex-queries.md",
    "ibkr-flex-web-service.md":  "flex-web-service.md",
    "ibkr-flex-v3.md":           "flex-web-service-v3.md",
    "ibkr-flex-v3-error.md":     "flex-web-service-v3-error-codes.md",
    "ibkr-flex-activity.md":     "create-activity-flex-query.md",
    "ibkr-flex-trade.md":        "create-trade-confirmation-flex-query.md",
    "ibkr-flex-run.md":          "run-flex-query.md",
    "ibkr-flex-edit.md":         "view-edit-delete-flex-queries.md",
    "ibkr-flex-delivery.md":     "delivery-settings-flex.md",
}

DEFAULT_SRC_DIR = ".firecrawl"
DEFAULT_TARGET_DIR = "external-resource/ibkr-api/docs/flex"

# URL → local filename mapping for cross-references
URL_TO_LOCAL = {
    "https://www.ibkrguides.com/clientportal/performanceandstatements/flex.htm":                 "flex-queries.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/flex-web-service.htm":     "flex-web-service.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/flex3.htm":                "flex-web-service-v3.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/flex3error.htm":           "flex-web-service-v3-error-codes.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/activityflex.htm":         "create-activity-flex-query.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/tradeflex.htm":            "create-trade-confirmation-flex-query.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/runflex.htm":              "run-flex-query.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/editflextemplate.htm":     "view-edit-delete-flex-queries.md",
    "https://www.ibkrguides.com/clientportal/performanceandstatements/deliverysettingsflex.htm": "delivery-settings-flex.md",
}


def clean(content: str) -> str:
    lines = content.split('\n')

    # === STEP 1: Remove header navigation noise ===
    # Find "You are here:" line — content starts after it
    content_start = 0
    for i, line in enumerate(lines):
        if line.strip() == 'You are here:':
            content_start = i + 1
            break

    if content_start > 0:
        # skip blank lines after marker
        while content_start < len(lines) and lines[content_start].strip() == '':
            content_start += 1
        lines = lines[content_start:]
    else:
        # fallback: find first H1 (======) or # heading
        for i, line in enumerate(lines):
            if line.startswith('# ') or (i > 0 and line.startswith('===')):
                # go back one line for the title if it's a setext heading
                start = max(0, i - 1) if line.startswith('===') else i
                lines = lines[start:]
                break

    # === STEP 2: Remove footer noise ===
    # IBKR Guides footer contains legal disclaimers for all subsidiaries.
    # Find the first line that starts the legal/subsidiary block.
    footer_markers = [
        '[Interactive Brokers LLC',
        'Interactive Brokers LLC. All rights reserved',
        'Interactive Brokers LLC is',
        'Interactive Brokers LLC Is',
        '© 20',  # copyright line
    ]
    footer_start = None
    for i, line in enumerate(lines):
        stripped = line.strip()
        for marker in footer_markers:
            if stripped.startswith(marker) or marker in stripped:
                # walk back over blank lines and horizontal rules
                j = i
                while j > 0 and lines[j-1].strip() in ('', '* * *', '---', '___'):
                    j -= 1
                footer_start = j
                break
        if footer_start is not None:
            break

    if footer_start is not None:
        lines = lines[:footer_start]

    # Also remove trailing "Top" link and blanks
    while lines and lines[-1].strip() in ('', 'Top'):
        lines.pop()

    content = '\n'.join(lines)

    # === STEP 3: Remove UI noise ===
    # Remove transparent.gif image references
    content = re.sub(r'!\[Closed\]\([^)]*transparent\.gif\)', '', content)
    content = re.sub(r'!\[Open\]\([^)]*transparent\.gif\)', '', content)
    # Remove "Filter:" standalone lines
    content = re.sub(r'\nFilter:\s*\n', '\n', content)
    # Remove "Submit Search" lines
    content = re.sub(r'\nSubmit Search\s*\n', '\n', content)
    # Remove "Loading ..." lines
    content = re.sub(r'\nLoading \.\.\.\s*\n', '\n', content)

    # === STEP 4: Replace cross-reference URLs with local paths ===
    for url, local_file in URL_TO_LOCAL.items():
        # Handle URL with anchor fragments
        content = content.replace(url + '#mc-main-content', f'./{local_file}')
        content = content.replace(url + '#', f'./{local_file}')
        content = content.replace(url, f'./{local_file}')

    # === STEP 5: Collapse excessive blank lines ===
    content = re.sub(r'\n{4,}', '\n\n\n', content)
    content = content.strip() + '\n'

    return content


def main():
    parser = argparse.ArgumentParser(
        description="Clean IBKR Guides Flex pages (remove nav/footer noise) and write to docs/flex/."
    )
    parser.add_argument(
        "--src", default=DEFAULT_SRC_DIR,
        help=f"Source directory containing raw Firecrawl outputs (default: {DEFAULT_SRC_DIR})"
    )
    parser.add_argument(
        "--dst", default=DEFAULT_TARGET_DIR,
        help=f"Target directory for cleaned Markdown files (default: {DEFAULT_TARGET_DIR})"
    )
    args = parser.parse_args()

    os.makedirs(args.dst, exist_ok=True)
    for src_name, dst_name in FILES.items():
        src_path = os.path.join(args.src, src_name)
        if not os.path.exists(src_path):
            print(f"  SKIP (not found): {src_path}")
            continue
        with open(src_path, 'r') as f:
            raw = f.read()
        cleaned = clean(raw)
        out_path = os.path.join(args.dst, dst_name)
        with open(out_path, 'w') as f:
            f.write(cleaned)
        print(f"  OK: {src_path} → {out_path} ({len(cleaned)} bytes)")


if __name__ == '__main__':
    main()
