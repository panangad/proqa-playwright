import type { Page } from '@playwright/test';

const USERNAME = 'admin';
const PASSWORD = 'password123';

export async function login(page: Page): Promise<void> {
  await page.goto('https://proqa.mywetechnologies.com/admin');
  await page.locator('input[placeholder="Enter username"]').filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 15000 });
  await page.locator('input[placeholder="Enter username"]').filter({ visible: true }).first().fill(USERNAME, { timeout: 15000 });
  await page.locator('input[type=password]').filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 8000 });
  await page.locator('input[type=password]').filter({ visible: true }).first().fill(PASSWORD, { timeout: 8000 });
  await page.locator('button:has-text("Login")').filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 8000 });
  await page.locator('button:has-text("Login")').filter({ visible: true }).first().click({ timeout: 8000 });
  await page.waitForURL((u) => !u.href.toLowerCase().includes('/login'), { timeout: 15000 });
  await page.locator('button:has-text("Logout")').filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 15000 });
}
