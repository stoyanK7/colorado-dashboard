import './css/index.css';
import './css/fonts.css';

import App from './components/site/App';
import { BrowserRouter } from 'react-router-dom';
import React from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';

axios.defaults.baseURL = 'http://localhost:8080/';
axios.defaults.headers.common['Cache-Control'] = 'max-age=31536000';

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById('root')
);
