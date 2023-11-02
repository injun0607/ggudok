import React, { useState, useEffect, lazy, Suspense, } from 'react';
import axios from 'axios';
import { Routes, Route, useLocation, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
// css import
import style from '../../styles/Admin/AdminLayout.module.css';
// component import
import AdminHeader from './AdminHeader';
import AdminFooter from './AdminFooter';
import Error from './../Error.js'
import ErrorItem from './../ErrorItem.js'
import ErrorLogin from './../ErrorLogin.js'
import Loading from './../Loading';
// page import
import AdminHome from '../../pages/Admin/AdminHome';
import Category from '../../pages/Admin/Category';
import CategoryCreate from '../../pages/Admin/CategoryCreate';
import CategoryEdit from '../../pages/Admin/CategoryEdit';
import Items from '../../pages/Admin/Items';
import ItemsCreate from '../../pages/Admin/ItemsCreate';
import ItemsEdit from '../../pages/Admin/ItemsEdit';
import Tags from '../../pages/Admin/Tags';
import TagCreate from '../../pages/Admin/TagCreate';
import Events from '../../pages/Admin/Events';
//redux import
import { setAdminLayout } from '../../redux/actions/adminLayoutActions';
import { setLoggedIn } from '../../redux/actions/userActions';

const AdminLayout = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();
  useEffect(() => {
    dispatch(setAdminLayout(true));
    return () => { dispatch(setAdminLayout(false)); };
  }, []);

	const [isCheckingLogin, setIsCheckingLogin] = useState(true);
	const isadminLayout = useSelector(state => state.adminLayout.isadminLayout);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
	
	// 세션 상태 조회 요청
	const fetchSessionStatus = async () => {
		try {
			const response = await axios.get('/getSession');
			const userData = response.data;
			if (userData.memberName !== undefined && userData.loginId !== undefined) {
				dispatch(setLoggedIn(userData));
			}
		} catch (error) {
			console.error('Error fetch login session :', error);
		} finally {
			setIsCheckingLogin(false);
		}
	};
	useEffect(() => {
		fetchSessionStatus();
	}, [location.pathname])
  

  return (
		// isCheckingLogin ? (<Loading />) : (
    // isLoggedIn ?
    <div className='adminLayout'>
      <AdminHeader />
			<div className={style.body}>
        <Routes>
          <Route path='/Admin/AdminHome' element={<AdminHome />}></Route>

          <Route path='/Admin/Category' element={<Category />}></Route>
          <Route path='/Admin/CategoryCreate' element={<CategoryCreate />}></Route>
          <Route path='/Admin/CategoryEdit/:categoryId' element={<CategoryEdit />}></Route>

          <Route path='/Admin/Items' element={<Items />}></Route>
          <Route path='/Admin/ItemsCreate' element={<ItemsCreate />}></Route>
          <Route path='/Admin/ItemsEdit/:subsId' element={<ItemsEdit />}></Route>

          <Route path='/Admin/Tags' element={<Tags />}></Route>
          <Route path='/Admin/TagCreate' element={<TagCreate />}></Route>

          <Route path='/Admin/Events' element={<Events />}></Route>


          <Route path='*' element={ <Error message="이런! 존재하지 않는 페이지입니다." /> }></Route>
        </Routes>
      </div>
      <AdminFooter />
    </div>
    //  : (
    //   <>
    //     <ErrorLogin />
    //     <div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
    //   </>
    // ))
  );
};

export default AdminLayout;
