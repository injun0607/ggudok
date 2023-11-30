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
import EventCreate from '../../pages/Admin/EventCreate';
import EventEdit from '../../pages/Admin/EventEdit';
//redux import
import { setLoggedIn, setAdminUser, refreshToken, logout } from '../../redux/actions/userActions';
import { setCookie, getCookie } from '../../redux/actions/cookieActions';

const AdminLayout = () => {
	let dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();

	const [isCheckingLogin, setIsCheckingLogin] = useState(true);
	
	const categories = useSelector(state => state.category.categories);
  const featuredcategories = useSelector(state => state.category.featuredcategories);
	const isadminLayout = useSelector(state => state.adminLayout.isadminLayout);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const isAdminUser = useSelector(state => state.user.isAdminUser);
  const memberName = useSelector(state => state.user.memberName);
  const loginId = useSelector(state => state.user.loginId);
	

	// JWT 만료 시간을 토큰에서 추출
	const parseJwt = (token) => {
		const base64Url = token.split('.')[1];
		const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
		const jsonPayload = decodeURIComponent(atob(base64));
		return JSON.parse(jsonPayload);
	};
	
	// refreschToken 설정
	const checkAccessTokenExpiration = async () => {
		const decodedToken = parseJwt(getCookie('access')); // JWT 디코딩
		const expirationTime = decodedToken.exp; // 만료 시간 (Unix timestamp 형식)
    const currentTime = Math.floor(Date.now() / 1000);

    if (expirationTime - currentTime < 30) {
      try {
        await dispatch(refreshToken()); // refreshToken을 백엔드에 전송하여 새로운 AccessToken을 받아옴
      } catch (error) {
        console.error('Error refreshing token:', error);
      }
    }
  };
	useEffect(() => {
    const checkInterval = setInterval(() => {
      if (isLoggedIn && !isCheckingLogin) {
        checkAccessTokenExpiration();
      }
    }, 30000); // 30초마다 AccessToken 만료 시간 확인

    return () => clearInterval(checkInterval);
  }, [isLoggedIn]);

	// 회원 상태조회 요청
	useEffect(() => {
		const interceptor = axios.interceptors.request.use(
			(config) => {
				const access = getCookie("access");
				if (access) {
					config.headers.access = `Bearer ${access}`;
				}
				return config;
			},
			(error) => {
				return Promise.reject(error);
			}
		);
	
		// 컴포넌트가 언마운트될 때 인터셉터를 정리
		return () => {
			axios.interceptors.request.eject(interceptor);
		};
	}, []); // 한 번만 실행
	const fetchAccessToken = async () => {
		const urlParams = new URLSearchParams(window.location.search);
		const access = urlParams.get('access');
		const refresh = urlParams.get('refresh');
		// Access Token을 쿠키에 저장
		setCookie('access', access, { path: '/' });
		setCookie('refresh', refresh, { path: '/' });

  };

	const fetchSessionStatus = async () => {
		try {
			const response = await axios.get('/getSession', {
				headers: {
					access: `Bearer ${getCookie('access')}`,
				},
			});
			const userData = response.data;
			if (userData.memberName !== undefined && userData.loginId !== undefined) {
				dispatch(setLoggedIn(userData));
			}
			if (userData.role === 'ADMIN'){
				dispatch(setAdminUser(true))
			}
		} catch (error) {
			console.error('Error fetch login session :', error);
		} finally {
			setIsCheckingLogin(false);
		}
	};
	useEffect(() => {
		const fetchAccessSession = async () => {
			if (window.location.search) {
				await fetchAccessToken();
			}
			const accessToken = getCookie('access');
			if(accessToken){
      	fetchSessionStatus();
			} else {
				dispatch(logout());
				setIsCheckingLogin(false);
			}
    };

		fetchAccessSession();
	}, [location.pathname, getCookie('access')])

	
 
  // ************************** 리뷰데이터 init ***************************
  const InitReviewData = async () => {
    try {
			const response = await axios.get('/getSession', {
				headers: {
					access: `Bearer ${getCookie('access')}`,
				},
			});
			console.log('InitReviewData Succress:', response)
    } catch (error) {
      console.error('Error InitReviewData:', error);
      alert(`리뷰 데이터를 받아오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  };

  return (
		isCheckingLogin ? <Loading /> : (
    (isLoggedIn && isAdminUser) ?
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
          <Route path='/Admin/EventCreate' element={<EventCreate />}></Route>
          <Route path='/Admin/EventEdit/:eventId' element={<EventEdit />}></Route>


          <Route path='*' element={ <Error message="이런! 존재하지 않는 페이지입니다." /> }></Route>
        </Routes>
      </div>
      <AdminFooter InitReviewData={() => InitReviewData()} />

    </div>
     : 
      <>
        <ErrorLogin />
        <div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
      </>
    )
  );
};

export default AdminLayout;
