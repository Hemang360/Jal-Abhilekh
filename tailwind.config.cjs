/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'dam-bg': '#dbf4ff',
        'dam-panel': '#d8efff',
        'dam-highlight': '#9ce1ff',
        'dam-button': '#25a4ff',
        'dam-primary': '#0070c0',
      },
      fontFamily: {
        'sans': ['Inter', 'system-ui', 'sans-serif'],
      },
    },
  },
  plugins: [],
}


