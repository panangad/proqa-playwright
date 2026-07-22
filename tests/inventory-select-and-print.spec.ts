import { test, expect } from '@playwright/test';
import { login } from './login_helper';

test.beforeEach(async ({ page }) => {
  await login(page);
});

test("Inventory: select orders and bulk print invoices", async ({ page }) => {
  await page.goto('/admin/inventory');

  const table = page.locator('table');
  await expect(table).toBeVisible();

  const printButton = page.getByRole('button', { name: /Print Selected \(\d+\)/ });
  await expect(printButton).toHaveText('Print Selected (0)');
  await expect(printButton).toBeDisabled();

  const firstRowCheckbox = table.locator('tbody tr').first().locator('button').first();
  await firstRowCheckbox.click();

  await expect(printButton).toHaveText('Print Selected (1)');
  await expect(printButton).toBeEnabled();

  await printButton.click();

  // Invoice content is rendered into a print-only container (hidden outside @media print),
  // so assert it is attached to the DOM rather than checking visibility.
  await expect(page.getByText('PROQA BOUTIQUE').first()).toBeAttached();
});
