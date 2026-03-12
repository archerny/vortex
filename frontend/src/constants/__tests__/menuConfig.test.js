import { describe, it, expect } from 'vitest';
import {
  menuKeyToPath,
  pathToMenuKey,
  menuKeyToParent,
  menuItems,
} from '../menuConfig';

describe('menuConfig - 路由映射', () => {
  it('menuKeyToPath 应包含所有页面路由', () => {
    const expectedPaths = [
      'dashboard', 'cashflow', 'broker', 'trades', 'strategy',
      'anomaly', 'market-events', 'profit-stock', 'profit-strategy',
      'profit-account', 'settings',
    ];
    const actualPaths = Object.values(menuKeyToPath);
    expectedPaths.forEach((path) => {
      expect(actualPaths).toContain(path);
    });
  });

  it('pathToMenuKey 应与 menuKeyToPath 互为反转', () => {
    Object.entries(menuKeyToPath).forEach(([key, path]) => {
      expect(pathToMenuKey[path]).toBe(key);
    });
  });

  it('pathToMenuKey 的条目数量应与 menuKeyToPath 一致', () => {
    expect(Object.keys(pathToMenuKey).length).toBe(Object.keys(menuKeyToPath).length);
  });
});

describe('menuConfig - 父菜单映射', () => {
  it('menuKeyToParent 中的每个 key 应存在于 menuKeyToPath 中', () => {
    Object.keys(menuKeyToParent).forEach((key) => {
      expect(menuKeyToPath).toHaveProperty(key);
    });
  });

  it('子菜单应正确映射到父菜单组', () => {
    // 账户管理组
    expect(menuKeyToParent['2']).toBe('account');
    expect(menuKeyToParent['6']).toBe('account');

    // 交易管理组
    expect(menuKeyToParent['3']).toBe('trade');
    expect(menuKeyToParent['7']).toBe('trade');
    expect(menuKeyToParent['8']).toBe('trade');
    expect(menuKeyToParent['9']).toBe('trade');

    // 盈亏分析组
    expect(menuKeyToParent['4-1']).toBe('profit');
    expect(menuKeyToParent['4-2']).toBe('profit');
    expect(menuKeyToParent['4-3']).toBe('profit');
  });
});

describe('menuConfig - 菜单项结构', () => {
  it('menuItems 应为非空数组', () => {
    expect(Array.isArray(menuItems)).toBe(true);
    expect(menuItems.length).toBeGreaterThan(0);
  });

  it('每个顶级菜单项应有 key、icon 和 label 属性', () => {
    menuItems.forEach((item) => {
      expect(item).toHaveProperty('key');
      expect(item).toHaveProperty('icon');
      expect(item).toHaveProperty('label');
    });
  });

  it('含子菜单的菜单项 children 应为非空数组', () => {
    const parentMenus = menuItems.filter((item) => item.children);
    expect(parentMenus.length).toBeGreaterThan(0);

    parentMenus.forEach((parent) => {
      expect(Array.isArray(parent.children)).toBe(true);
      expect(parent.children.length).toBeGreaterThan(0);

      parent.children.forEach((child) => {
        expect(child).toHaveProperty('key');
        expect(child).toHaveProperty('label');
      });
    });
  });

  it('应包含仪表盘、交易管理、盈亏分析、账户管理和系统设置', () => {
    const topLevelKeys = menuItems.map((item) => item.key);
    expect(topLevelKeys).toContain('1');        // 仪表盘
    expect(topLevelKeys).toContain('trade');     // 交易管理
    expect(topLevelKeys).toContain('profit');    // 盈亏分析
    expect(topLevelKeys).toContain('account');   // 账户管理
    expect(topLevelKeys).toContain('5');         // 系统设置
  });
});
