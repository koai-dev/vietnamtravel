import { test, expect } from '@playwright/test';

test('dashboard loads correctly', async ({ page }) => {
  await page.goto('/dashboard/provinces');
  await expect(page.getByRole('link', { name: 'Provinces' })).toBeVisible();
});
