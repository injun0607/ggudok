import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
/* css import */
import style from '../../styles/Admin/AdminHeader.module.css';
// redux import
import { logout } from '../../redux/actions/userActions';
import { setSearchQuery } from '../../redux/actions/searchActions';


const Header = () => {

  return (
	<header>	
		<Topheader />
		<div className={style.category}>
			<div className='webwidth'>
				<Allcategory />
				<Featuredcategory />

				<Link to="/Contactus" className={style.pointdepth}><p>Contact Us</p></Link>
			</div>
		</div>
	</header>
  )
}

const Topheader = () => {
  const dispatch = useDispatch();
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const searchQuery = useSelector(state => state.search.searchQuery);
	const handleLogout = () => {
    // 로그아웃 처리
    dispatch(logout());
  };
	const handleSearchChange = (e) => {
		// 검색어 입력 시 검색어 상태 변경
		dispatch(setSearchQuery(e.target.value));
	}
	return (
		<div className={style.header}>
			<div className='webwidth'>
				<div className={style.link}>
					<h1 className={style.logo}>
						<Link to="/Admin/AdminHome">꾸<span>독</span></Link>
					</h1>
					<div className={style.search}>
						<div className={style.searchbar}>
							<input
								type='text'
								placeholder='관심있는 구독서비스 또는 태그를 입력하세요.'
								value={searchQuery}
								onChange={handleSearchChange}
							/>
							<span className='material-icons'>search</span>
						</div>
					</div>
					<div className={style.nav}>
						<button onClick={ handleLogout }>로그아웃</button>
						<Link to="/Auth/Join" className={style.point}>홈페이지</Link>
					</div>
				</div>
			</div>
		</div>
	)
}

const Allcategory = () => {
  const categories = useSelector(state => state.category.categories);

	const [showTwodepth, setShowTwodepth] = useState(false);
	const handleTwodepth = () => {
		setShowTwodepth(!showTwodepth);
	}

	return (
		<nav className={style.allcategory}>
			<div className={style.onedepth} onClick={handleTwodepth}>
				<span className='material-icons'>menu</span>
				전체카테고리
			</div>
			{showTwodepth && (
				<div className={style.twodepth}>
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
				</div>)}
		</nav>
	)
}

const Featuredcategory = () => {
  const featuredcategories = useSelector(state => state.category.featuredcategories);
	return (
		<div className={style.featuredcategory}>
			{
				featuredcategories.map((category, index) =>
					<Link
						to={`/Category/${category.categoryEng}`} // Use 'to' instead of 'href'
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