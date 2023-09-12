import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter } from "react-router-dom";
import { Provider } from "react-redux";
import App from './App';
import Scrolltop from './components/Scrolltop.js'

// state store import
import store from './redux/store.js'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <Provider store={store}>
        <BrowserRouter>
          <Scrolltop />
          <App />
        </BrowserRouter>
      </Provider>
  </React.StrictMode>
); 