import { test, expect } from '@playwright/test';

test.beforeEach(async ({ page }) => {
  await page.goto('/');
});

// ---------------------------------------------------------------------------
// #getEngineers — "Get All Engineers"
// ---------------------------------------------------------------------------
test.describe('Get All Engineers button (#getEngineers)', () => {
  test('is visible on page load', async ({ page }) => {
    await expect(page.locator('#getEngineers')).toBeVisible();
  });

  test('has correct label', async ({ page }) => {
    await expect(page.locator('#getEngineers')).toHaveText('Get All Engineers');
  });

  test('is enabled and clickable', async ({ page }) => {
    await expect(page.locator('#getEngineers')).toBeEnabled();
    await page.locator('#getEngineers').click();
    // Click should not throw or navigate away
    await expect(page).toHaveURL('/');
  });

  test('populates #responseOutputArea or #statusMessageArea after click', async ({
    page,
  }) => {
    // NOTE: this assertion requires the backend to be running.
    // It verifies the DOM is updated with some content after the fetch resolves.
    await page.locator('#getEngineers').click();
    await expect(page.locator('#responseOutputArea')).not.toBeEmpty({
      timeout: 5000,
    });
  });
});

// ---------------------------------------------------------------------------
// #getEngineerById — "Get Engineers By Id"
// ---------------------------------------------------------------------------
test.describe('Get Engineers By Id button (#getEngineerById)', () => {
  test('is visible on page load', async ({ page }) => {
    await expect(page.locator('#getEngineerById')).toBeVisible();
  });

  test('has correct label', async ({ page }) => {
    await expect(page.locator('#getEngineerById')).toHaveText(
      'Get Engineers By Id'
    );
  });

  test('is enabled and clickable', async ({ page }) => {
    await expect(page.locator('#getEngineerById')).toBeEnabled();
    await page.locator('#getEngineerById').click();
    await expect(page).toHaveURL('/');
  });

  test('reveals the #getId form section after click', async ({ page }) => {
    // The #getId div is hidden by default (display: none)
    await expect(page.locator('#getId')).toBeHidden();
    await page.locator('#getEngineerById').click();
    await expect(page.locator('#getId')).toBeVisible();
  });
});

// ---------------------------------------------------------------------------
// #reset — "Reset"
// ---------------------------------------------------------------------------
test.describe('Reset button (#reset)', () => {
  test('is visible on page load', async ({ page }) => {
    await expect(page.locator('#reset')).toBeVisible();
  });

  test('has correct label', async ({ page }) => {
    await expect(page.locator('#reset')).toHaveText('Reset');
  });

  test('is enabled and clickable', async ({ page }) => {
    await expect(page.locator('#reset')).toBeEnabled();
    await page.locator('#reset').click();
    await expect(page).toHaveURL('/');
  });

  test('hides the #getId form section if it was shown', async ({ page }) => {
    // Show the form first
    await page.locator('#getEngineerById').click();
    await expect(page.locator('#getId')).toBeVisible();

    // Reset should hide it again
    await page.locator('#reset').click();
    await expect(page.locator('#getId')).toBeHidden();
  });

  test('clears #responseOutputArea after click', async ({ page }) => {
    await page.locator('#reset').click();
    await expect(page.locator('#responseOutputArea')).toBeEmpty();
  });
});

// ---------------------------------------------------------------------------
// #submitId — "Enter Id" (form submit inside #enterId)
// ---------------------------------------------------------------------------
test.describe('Enter Id submit button (#submitId)', () => {
  test.beforeEach(async ({ page }) => {
    // The submit button is only accessible once the #getId section is visible
    await page.locator('#getEngineerById').click();
    await expect(page.locator('#getId')).toBeVisible();
  });

  test('is visible after revealing the form', async ({ page }) => {
    await expect(page.locator('#submitId')).toBeVisible();
  });

  test('has correct value label', async ({ page }) => {
    await expect(page.locator('#submitId')).toHaveValue('Enter Id');
  });

  test('is enabled', async ({ page }) => {
    await expect(page.locator('#submitId')).toBeEnabled();
  });

  test('submitting with a number populates #responseOutputArea or #statusMessageArea', async ({
    page,
  }) => {
    // NOTE: this assertion requires the backend to be running.
    await page.locator('#inputId').fill('1');
    await page.locator('#submitId').click();
    await expect(
      page.locator('#responseOutputArea').or(page.locator('#statusMessageArea')).first()
    ).not.toBeEmpty({ timeout: 5000 });
  });
});
