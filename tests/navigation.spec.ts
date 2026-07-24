import { test, expect } from '@playwright/test';
import fs from 'fs';
import path from 'path';
import { login } from './login_helper';

test('navigation: all pages are reachable', async ({ page }) => {
  const { pages } = JSON.parse(
    fs.readFileSync(path.resolve(__dirname, '../pages.json'), 'utf-8')
  );

  await login(page);

  const broken: string[] = [];

  for (const p of pages) {
    const response = await page.goto(p.url);
    const status = response?.status() ?? 0;
    if (status >= 400) {
      broken.push(`${p.url} (status ${status})`);
      continue;
    }
    try {
      await page
        .locator('button:has-text("Logout")')
        .filter({ visible: true })
        .first()
        .waitFor({ state: 'visible', timeout: 10000 });
    } catch {
      broken.push(`${p.url} (no logged-in marker / error state)`);
    }
  }

  expect(broken, `Broken pages: ${broken.join(', ')}`).toEqual([]);
});
