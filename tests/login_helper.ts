// login_snippet.ts — GENERATED from the verified login_recipe by snippet.py.
// Do not hand-edit: the recipe is the single source of truth; this file is
// regenerated (byte-identical) whenever the recipe re-verifies.
// Credentials are injected at run time via the MAESTRO_CREDS env var.
import type { Page } from '@playwright/test';

export async function login(page: Page): Promise<void> {
  const creds = JSON.parse(process.env.MAESTRO_CREDS || '{}');
  if (!creds.username) return; // no login configured for this app
  await page.goto("https://proqa.mywetechnologies.com/admin");
  await page.locator("input[placeholder=\"Enter username\"]").filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 15000 });
  await page.locator("input[placeholder=\"Enter username\"]").filter({ visible: true }).first().fill(creds.username, { timeout: 15000 });
  await page.locator("input[type=password]").filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 8000 });
  await page.locator("input[type=password]").filter({ visible: true }).first().fill(creds.password, { timeout: 8000 });
  await page.locator("button:has-text(\"Login\")").filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 8000 });
  await page.locator("button:has-text(\"Login\")").filter({ visible: true }).first().click({ timeout: 8000 });
  await page.waitForURL((u) => !u.href.toLowerCase().includes("/login"), { timeout: 15000 });
  await page.locator("button:has-text(\"Logout\")").filter({ visible: true }).first().waitFor({ state: 'visible', timeout: 15000 });
}
