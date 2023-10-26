import React from 'react'
import { Helmet } from 'react-helmet';
import { useLocation } from 'react-router-dom';
// component import
import Layout from './components/Layout'
import AdminLayout from './components/Admin/AdminLayout';
// css import
import './styles/reset.css';
import './styles/global.css';
import './styles/globalResponsive.css';

function App() {
  const location = useLocation();
  return (
    <div className="App">
      {location.pathname.startsWith('/Admin') ? (
        <>
          <Helmet>
            <title>꾸독 : 관리자</title>
          </Helmet>
          <AdminLayout />
        </>
      ) : (
        <>
          <Helmet>
            <title>꾸독 : 나만의 구독 비서</title>
          </Helmet>
          <Layout />
        </>
      )}
    </div>
  );
}

export default App;