import React, { useEffect } from 'react';
import { Routes, Route, } from 'react-router-dom';

// css import
import style from '../../styles/Admin.module.css';
// component import
import AdminHeader from './AdminHeader';
import AdminFooter from './AdminFooter';
import Error from './../Error.js'
// page import
import AdminHome from '../../pages/Admin/AdminHome';
//redux import
import { useDispatch } from 'react-redux';
import { setAdminLayout } from '../../redux/actions/adminLayoutActions';


const AdminLayout = () => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(setAdminLayout(true)); // AdminLayout 내에서는 isadminLayout을 true로 설정
    return () => {
      dispatch(setAdminLayout(false)); // 컴포넌트 언마운트 시에는 다시 false로 설정
    };
  }, [dispatch]);

  return (
		<div className={style.layout}>
      <AdminHeader />
			<div className={style.body}>
        <Routes>
          <Route path='/Admin/' element={<AdminHome />}></Route>
          <Route path='/Admin/AdminHome' element={<AdminHome />}></Route>
        </Routes>
      </div>
      <AdminFooter />
    </div>
  );
};

export default AdminLayout;
