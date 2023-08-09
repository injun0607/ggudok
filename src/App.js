import { Routes, Route, } from 'react-router-dom';
// component import
import Layout from './components/Layout'
import AdminLayout from './components/Admin/AdminLayout'
// css import
import './styles/reset.css';
import './styles/global.css';
import './styles/globalResponsive.css';

function App() {
  return (
    <div className="App">
      <Layout />
    </div>
  );
}

export default App;