import React from 'react'
import { Helmet, HelmetProvider } from 'react-helmet-async';
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
          <HelmetProvider>
            <Helmet>
              <title>꾸독 : 관리자</title>
              <link rel="icon" href="/images/favicon_grey.ico" />
            </Helmet>
          </HelmetProvider>
          <AdminLayout />
        </>
      ) : (
        <>
          <HelmetProvider>
            <Helmet>
              <title>꾸독 : 나만의 구독 비서</title>
              <link rel="icon" href="/images/favicon.ico" />
            </Helmet>
          </HelmetProvider>
          <Layout />
        </>
      )}
    </div>
  );
}

export default App;