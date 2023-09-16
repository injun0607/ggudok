import React, { useEffect, useRef } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
/* css import */
import style from '../styles/Header.module.css';
// redux import
import { logout } from '../redux/actions/userActions';
import { setSearchQuery } from '../redux/actions/searchActions';
import { setDropCategory } from '../redux/actions/categoryActions';


const Header = () => {
  return (
	<header>	
		<Topheader />
		<div className={style.category}>
			<div className='webwidth'>
				<Allcategory />
				<Featuredcategory />

				<Link to="/Contactus" className={style.pointdepth}><span className='material-icons'>mail</span><p>Contact Us</p></Link>
			</div>
		</div>
	</header>
  )
}
// 상단 메뉴
const Topheader = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const searchQuery = useSelector(state => state.search.searchQuery);
	const handleLogout = () => {
    dispatch(logout());
  };
	const handleSearchChange = (e) => {
		dispatch(setSearchQuery(e.target.value));
	}
	const handleSearchSubmit = (e) => {
		e.preventDefault();
		navigate(`/SearchItemlist?q=${searchQuery}`);
	}

	return (
		<div className={style.header}>
			<div className='webwidth'>
				<div className={style.link}>
					<h1 className={style.logo}>
						<Link to="/Home">꾸<span>독</span></Link>
					</h1>
					<div className={style.search}>
						<form onSubmit={ handleSearchSubmit } className={style.searchbar}>
							<input
								type='text'
								placeholder='구독서비스 또는 태그를 입력하세요.'
								value={ searchQuery }
								onChange={ handleSearchChange }
								id='searchInput'
							/>
							<button type='submit'><span className='material-icons'>search</span></button>
						</form>
					</div>
					<div className={style.nav}>
						{isLoggedIn ? (
							<>
								<button onClick={ handleLogout }>로그아웃</button>
								<Link to="/Mypage/MySubscribe" className={style.point}>마이페이지</Link>
							</>
						) : (
							<>
								<Link to="/Auth/Login">로그인</Link> 
								<Link to="/Mypage/MySubscribe">마이페이지</Link>
								<Link to="/Auth/Join" className={style.point}>회원가입</Link>
							</>
						)}
						<Link to="/Admin/AdminHome" className={style.pointAdmin}><span className='material-icons'>settings</span><p>Admin</p></Link>
					</div>
				</div>
			</div>
		</div>
	)
}
// 하단메뉴 전체카테고리/드롭다운 메뉴
const Allcategory = () => {
  const dispatch = useDispatch();
	const dropdownRef = useRef(null);

  const categories = useSelector(state => state.category.categories);
  const dropCategory = useSelector(state => state.category.dropCategory);
	
	const handleTwodepth = () => {
		dispatch(setDropCategory());
	}
	
	useOutsideClick(dropdownRef);
	// 드롭메뉴 다른 곳 눌렀을 때 꺼지기
	function useOutsideClick(ref) {
		const dispatch = useDispatch();
		
		useEffect(() => {
			function handleClickOutside(event) {
				if (ref.current && !ref.current.contains(event.target)) {
					dispatch(setDropCategory());
				}
			}
			document.addEventListener("mousedown", handleClickOutside);
			return () => {
				document.removeEventListener("mousedown", handleClickOutside);
			};
		}, [dispatch, ref]);
	}
	return (
		<nav className={style.allcategory}>
			<div className={style.onedepth} onClick={ handleTwodepth }>
				<span className='material-icons'>menu</span>
				<p>전체카테고리</p>
			</div>
			{dropCategory ? 
				<div className={style.twodepth} ref={dropdownRef}>
					{
						categories.map((category, index) => 
						<Link
							to={`/Category/${category.categoryEng}`}
							className={style.depths}
							key={index}
						>
								<img src={`${category.icon}`} alt={category.category} />
								<p>{category.category}</p>
						</Link>
						)
					}
				</div> : null}
		</nav>
	)
}
// 중간 메뉴
const Featuredcategory = () => {
  const featuredcategories = useSelector(state => state.category.featuredcategories);
	return (
		<div className={style.featuredcategory}>
			{
				featuredcategories.map((category, index) =>
					<Link
						to={`/Category/${category.categoryEng}`}
						className={style.depths}
						key={index}
					>
						<p>{category.category}</p>
					</Link>
				)
			}
			<Link to="/Compare" className={style.depths}>
				<p>간편비교</p>
			</Link>
			<Link to="/Event" className={style.depths}>
				<p>이벤트</p>
			</Link>
		</div>
	)
}

export default Header;