/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [require("daisyui")],
  daisyui: {
    themes: [
      {
        light: {
          "primary": "#0093FB",
          "secondary": "#93b9df",
          "accent": "#002F81",
          "neutral": "#0f363d",
          "base-100": "#f6f6f6",
        },
      },
    ],
  },
}