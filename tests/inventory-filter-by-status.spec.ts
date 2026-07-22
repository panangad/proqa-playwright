import { test, expect } from '@playwright/test';
import { login } from './login_helper';

test.beforeEach(async ({ page }) => {
  await login(page);
});

test("Inventory: filter orders by status dropdown", async ({ page }) => {
  await page.goto('/admin/inventory');

  const table = page.locator('table');
  await expect(table).toBeVisible();

  const statusDropdown = page.locator('select');
  await statusDropdown.selectOption({ label: 'Dispatched' });

  const rows = table.locator('tbody tr');
  await expect(rows.first()).toBeVisible();
  const count = await rows.count();
  expect(count).toBeGreaterThan(0);

  for (let i = 0; i < count; i++) {
    const statusCell = rows.nth(i).locator('td').last();
    await expect(statusCell).toHaveText(/^dispatched$/i);
  }
});
