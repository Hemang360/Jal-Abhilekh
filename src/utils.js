/**
 * Creates a URL for a given page name
 * @param {string} pageName - The name of the page
 * @returns {string} The URL for the page
 */
export function createPageUrl(pageName) {
  // Convert page name to kebab-case and prepend a slash
  return '/' + pageName
    .replace(/([a-z])([A-Z])/g, '$1-$2')
    .replace(/\s+/g, '-')
    .toLowerCase();
}
