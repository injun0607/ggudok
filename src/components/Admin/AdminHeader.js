import React from 'react';
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
	</header>
  )
}

const Topheader = () => {
  const dispatch = useDispatch();
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
	)
}


export default Header;