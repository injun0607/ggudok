/* eslint-disable react-hooks/exhaustive-deps */
import React, { useState, useEffect, lazy, Suspense, } from 'react';
import axios from 'axios';
import { Routes, Route, useLocation, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';

// css import
import style from '../styles/Layout.module.css';
// component import
import Header from './Header';
import Footer from './Footer';
import Error from './Error.js'
import ErrorItem from './ErrorItem.js'
import ErrorLogin from './ErrorLogin.js'
import AdminHeader from './Admin/AdminHeader';
import AdminFooter from './Admin/AdminFooter';
import Loading from './Loading';
// page import
import Home from '../pages/Home';
import Login from '../pages/Auth/Login';
import Join from '../pages/Auth/Join';
import JoinEmail from '../pages/Auth/JoinEmail';
import EditProfile from '../pages/Auth/EditProfile';
import Itemlist from '../pages/Itemlist';
import FeaturedItemlist from '../pages/FeaturedItemlist';
import SearchItemlist from '../pages/SearchItemlist';
import Compare from '../pages/Compare';
import Event from '../pages/Event';
import Contactus from '../pages/Contactus';
import AddSubs from '../pages/Subscribe/AddSubs';
import DelSubs from '../pages/Subscribe/DelSubs';
import Mypage from '../pages/Mypage/Mypage';
import MySubscribe from '../pages/Mypage/MySubscribe';
import MyReview from '../pages/Mypage/MyReview';
import MyLike from '../pages/Mypage/MyLike';
import AdminHome from '../pages/Admin/AdminHome';
// redux import
import { toggleDarkMode } from '../redux/actions/darkModeActions';
import { setLoggedIn } from '../redux/actions/userActions';

const ItemDetail = lazy( () => import('../pages/ItemDetail') )

const Layout = () => {
	let dispatch = useDispatch();
  const navigate = useNavigate();
  const location = useLocation();

	const [isCheckingLogin, setIsCheckingLogin] = useState(true);
	
	const categories = useSelector(state => state.category.categories);
  const featuredcategories = useSelector(state => state.category.featuredcategories);
	const isadminLayout = useSelector(state => state.adminLayout.isadminLayout);
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const memberName = useSelector(state => state.user.memberName);
  const loginId = useSelector(state => state.user.loginId);
	
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

	// 다크모드 state 감지
  const darkMode = useSelector(state => state.darkMode.darkMode);

	return (
		<div className={`${darkMode ? 'dark' : ''}`}>
		<div className={`${isadminLayout ? `adminLayout` : `${style.layout}` }`}>
			{/* {isadminLayout ? <div className={style.adminLayout}> : <div className={style.layout}>} */}
			{isadminLayout ? <AdminHeader /> : <Header />} 
			<div className={style.body}>
				<Routes>
					<Route path='/' element={<Home />} />
					<Route path='/Home' element={<Home />}></Route>
					<Route path="/Admin/AdminHome" element={<AdminHome isadminLayout />} />

					<Route path='/Mypage' element={
						isCheckingLogin ? (<Loading />) : (
							isLoggedIn ? <Mypage memberName={memberName}/> : (
								<>
									<ErrorLogin />
									<div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
								</>
							)
						)
					}>
						<Route path="MySubscribe" element={
          	<Suspense fallback={ <Loading /> }><MySubscribe /></Suspense>
						} />
						<Route path="MyReview" element={
          	<Suspense fallback={ <Loading /> }><MyReview isCheckingLogin={isCheckingLogin} /></Suspense>
						} />
						<Route path="MyLike" element={
          	<Suspense fallback={ <Loading /> }><MyLike isCheckingLogin={isCheckingLogin} /></Suspense>
						} />
					</Route>
					
					<Route path='/Subscribe/AddSubs' element={
						isCheckingLogin ? (<Loading />) : (
							isLoggedIn ? <AddSubs /> : (
								<>
									<ErrorLogin />
									<div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
								</>
							)
						)
					} />
					<Route path='/Subscribe/DelSubs' element={
						isCheckingLogin ? (<Loading />) : (
							isLoggedIn ? <DelSubs /> : (
								<>
									<ErrorLogin />
									<div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
								</>
							)
						)
					} />

					<Route path='/Auth/Login' element={ <Login /> }></Route>
					<Route path='/Auth/Join' element={ <Join /> }></Route>
					<Route path='/Auth/JoinEmail' element={ <JoinEmail /> }></Route>
					<Route path='/Auth/EditProfile' element={
						isCheckingLogin ? (<Loading />) : (
							isLoggedIn ? <EditProfile /> : (
								<>
									<ErrorLogin />
									<div className='modalBg modalBg-Blur' onClick={() => { navigate(-1) }}></div>
								</>
							)
						)
					} />

					<Route path='/SearchItemlist' element={ <Suspense fallback={ <Loading /> }><SearchItemlist /></Suspense>} />

					{categories.map((category) => (
						<Route
							key={category.categoryEng}
							path={`/Category/${category.categoryEng}`}
							element={ <Suspense fallback={ <Loading /> }><Itemlist category={category.category} categoryEng={category.categoryEng} /></Suspense>}
						/>
					))}
					{featuredcategories.map((featuredcategories) => (
						<Route
							key={featuredcategories.categoryEng}
							path={`/Category/${featuredcategories.categoryEng}`}
							element={ <Suspense fallback={ <Loading /> }><FeaturedItemlist category={featuredcategories.category} /></Suspense>}
						/>
					))}

					<Route path='/subs/detail/:subsId' element={
          	<Suspense fallback={ <Loading /> }>
							<ItemDetail />
						</Suspense>
					} />


					<Route path='Compare' element={ <Compare /> }></Route>
					<Route path='Event' element={ <Event /> }></Route>
					<Route path='Contactus' element={ <Contactus /> }></Route>

					<Route path='*' element={ <Error /> }></Route>
					<Route path='*' element={ <ErrorItem /> }></Route>
					<Route path='*' element={ <ErrorLogin /> }></Route>
				</Routes>
			</div>
			{isadminLayout ? <AdminFooter /> : <Footer />} {}

			{isadminLayout ? null : <Darkmodebtn />}
			<Topbtn />
			{/* <div className={style.ani}>
				<span className={style.ani06}><img src='/images/common/animateBG-01.svg' alt='animate'/></span>
				<span className={style.ani08}><img src='/images/common/animateBG-02.svg' alt='animate'/></span>
				<span className={style.ani00}><img src='/images/common/animateBG-03.svg' alt='animate'/></span>
			</div> */}
		</div>
		</div>
	)
}

const Darkmodebtn = () => {
  const dispatch = useDispatch();
	// 다크모드 state 감지
  const darkMode = useSelector(state => state.darkMode.darkMode);
  const getCurrentMode = useSelector(state => state.darkMode.getCurrentMode);
	// 유저 브라우저 다크모드설정 감지
	const isDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;

	useEffect(() => {
		if(!getCurrentMode && !isDarkMode){
			localStorage.setItem('darkmode', JSON.stringify(false));
		} else if(!getCurrentMode && isDarkMode){
			localStorage.setItem('darkmode', JSON.stringify(true));
		}
	}, []); // eslint-disable-next-lined

	const handleDarkMode = () => {
		dispatch(toggleDarkMode());
	}
	return (
		<button className={style.darkmodebtn} onClick={ handleDarkMode }>
			{darkMode ? <span className="material-icons on">light_mode</span> : <span className="material-icons off">dark_mode</span>}
		</button>
	);
}

const Topbtn = () => {
	const [showToptn, setShowToptn] = useState(false);
	const handleScroll = () => {
		if (window.scrollY > 200) {
			setShowToptn(true);
		} else { setShowToptn(false) }
	}
	const scrollTop = () => {
		window.scrollTo({
			top: 0,
			behavior: 'smooth'
		});
	}

	useEffect(() => {
		window.addEventListener('scroll', handleScroll);
		return () => {
			window.removeEventListener('scroll', handleScroll)
		}
	}, []);

	return (
		<button className={`${style.topbtn} ${showToptn ? style.show : ''}`} onClick={ scrollTop }>
			<span className="material-icons">arrow_upward</span>
		</button>
	);
}

export default Layout;