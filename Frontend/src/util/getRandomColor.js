// Colorize VSCode extension is recommended for looking at this file
// https://marketplace.visualstudio.com/items?itemName=kamikillerto.vscode-colorize
// Then add the following to settings.json
// "colorize.languages": ["typescript", "javascript", "css", "scss"]
const palettes =
{
  "palette1": ['#001219', '#005F73', '#0A9396', '#94d2bd', '#e9d8a6', '#ee9b00', '#ca6702', '#bb3e03', '#ae2012', '#9b2226'],
  "palette2": ['#f72585', '#b5179e', '#7209b7', '#560bad', '#480ca8', '#3a0ca3', '#3f37c9', '#4361ee', '#4895ef', '#4cc9f0'],
  "palette3": ['#03045e', '#023e8a', '#0077b6', '#0096c7', '#00b4d8', '#48cae4', '#90e0ef', '#ade8f4', '#caf0f8'],
  "palette4": ['#d9ed92', '#b5e48c', '#99d98c', '#76c893', '#52b69a', '#34a0a4', '#168aad', '#1a759f', '#1e6091', '#184e77'],
  "palette5": ['#03071e', '#370617', '#6a040f', '#9d0208', '#d00000', '#dc2f02', '#e85d04', '#f48c06', '#faa307', '#ffba08'],
  "palette6": ['#582f0e', '#7f4f24', '#936639', '#a68a64', '#b6ad90', '#c2c5aa', '#a4ac86', '#656d4a', '#414833', '#333d29'],
  "palette7": ["#f94144", "#f3722c", "#f8961e", "#f9844a", "#f9c74f", "#90be6d", "#43aa8b", "#4d908e", "#577590", "#277da1"],
  "palette8": ["#ff5400", "#ff6d00", "#ff8500", "#ff9100", "#ff9e00", "#00b4d8", "#0096c7", "#0077b6", "#023e8a", "#03045e"],
  "palette9": ["#053c5e", "#1d3958", "#353652", "#4c334d", "#643047", "#7c2e41", "#942b3b", "#ab2836", "#c32530", "#db222a"],
  "palette10": ["#006466", "#065a60", "#0b525b", "#144552", "#1b3a4b", "#212f45", "#272640", "#312244", "#3e1f47", "#4d194d"]
}


const getRandomColor = (palette, index) => palettes[palette][index];

export default getRandomColor;
