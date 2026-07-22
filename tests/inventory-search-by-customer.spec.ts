import { test, expect } from '@playwright/test';
import { login } from './login_helper';

test.beforeEach(async ({ page }) => {
  await login(page);
});

test("Inventory: search orders by customer name", async ({ page }) => {
  await page.goto('/admin/inventory');

  const table = page.locator('table');
  await expect(table).toBeVisible();

  const searchBox = page.getByPlaceholder('Search by Order Code, Customer Name, Customer Phone...');
  await expect(searchBox).toBeVisible();
  await searchBox.fill('Rahul');

  const rows = table.locator('tbody tr');
  await expect(rows.first()).toBeVisible();
  const count = await rows.count();
  expect(count).toBeGreaterThan(0);

  for (let i = 0; i < count; i++) {
    const customerCell = rows.nth(i).locator('td').nth(3);
    await expect(customerCell).toHaveText(/Rahul/i);
  }
});
