/* eslint-disable react-hooks/exhaustive-deps */
import React, { useState, useEffect, lazy, Suspense } from 'react';
import { Routes, Route, } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';

// css import
import style from '../styles/Layout.module.css';
// component import
import Header from './Header';
import Footer from './Footer';
import Error from './Error.js'
import AdminHeader from './Admin/AdminHeader';
import AdminFooter from './Admin/AdminFooter';
// page import
import Home from '../pages/Home';
import Login from '../pages/Auth/Login';
import Join from '../pages/Auth/Join';
import JoinEmail from '../pages/Auth/JoinEmail';
import Itemlist from '../pages/Itemlist';
import FeaturedItemlist from '../pages/FeaturedItemlist';
import Compare from '../pages/Compare';
import Event from '../pages/Event';
import Contactus from '../pages/Contactus';
import AdminHome from '../pages/Admin/AdminHome';
// redux import
import { toggleDarkMode } from '../redux/actions/darkModeActions';

const ItemDetail = lazy( () => import('../pages/ItemDetail') )


const Layout = () => {
	const categories = useSelector(state => state.category.categories);
  const featuredcategories = useSelector(state => state.category.featuredcategories);
	const isadminLayout = useSelector(state => state.adminLayout.isadminLayout);

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

					<Route path='/Auth/Login' element={ <Login /> }></Route>
					<Route path='/Auth/Join' element={ <Join /> }></Route>
					<Route path='/Auth/JoinEmail' element={ <JoinEmail /> }></Route>

					{categories.map((category) => (
						<Route
							key={category.categoryEng}
							path={`/Category/${category.categoryEng}`}
							element={<Itemlist category={category.category} />}
						/>
					))}
					{featuredcategories.map((featuredcategories) => (
						<Route
							key={featuredcategories.categoryEng}
							path={`/Category/${featuredcategories.categoryEng}`}
							element={<FeaturedItemlist category={featuredcategories.category} />}
						/>
					))}

					<Route path='/ItemDetail/:itemId' element={
          	<Suspense fallback={ <div>Loading...</div> }>
							<ItemDetail />
						</Suspense>
						} />


					<Route path='Compare' element={ <Compare /> }></Route>
					<Route path='Event' element={ <Event /> }></Route>
					<Route path='Contactus' element={ <Contactus /> }></Route>

					<Route path='*' element={ <Error /> }></Route>
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