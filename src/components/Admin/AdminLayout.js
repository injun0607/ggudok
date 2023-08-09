import React from 'react';
import { Routes, Route } from 'react-router-dom';

// css import
import style from '../../styles/Admin.module.css';
// component import
import AdminHeader from './AdminHeader';
import AdminFooter from './AdminFooter';
import Error from './../Error.js'
// page import
// import DashboardPage from '../pages/admin/DashboardPage';
// import UserManagementPage from '../pages/admin/UserManagementPage';

const AdminLayout = () => {
  return (
		<div className={style.layout}>
      <AdminHeader />
			<div className={style.body}>
        <Routes>
          {/* <Route path="/" element={<DashboardPage />} />
          <Route path="/admin/dashboard" element={<DashboardPage />} />
          <Route path="/admin/users" element={<UserManagementPage />} /> */}
        </Routes>
      </div>
      <AdminFooter />
    </div>
  );
};

export default AdminLayout;
