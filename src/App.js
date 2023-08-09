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
      <Routes>
        <Route path="/" element={<Layout />} />
        <Route path="/admin/*" element={<AdminLayout />} />
      </Routes>
    </div>
  );
}

export default App;