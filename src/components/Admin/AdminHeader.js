import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
/* css import */
import style from '../../styles/Admin/AdminHeader.module.css';


const Header = () => {

  return (
	<header>	
		<div className={style.header}>
			<h1 className={style.logo}>
				<Link to="/Admin/AdminHome">꾸<span>독</span></Link>
			</h1>
			<div className={style.nav}>
				<Link to="/Admin/Category">카테고리 <span>관리</span></Link> 
				<Link to="/Admin/Items">구독서비스 <span>관리</span></Link>
				<Link to="/Admin/Tags">태그 <span>관리</span></Link>
				<Link to="/Admin/Events">이벤트 <span>관리</span></Link>
			</div>
			<Link to="/Home" className={style.pointAdmin} target='_blank'><span className='material-icons'>home</span><p>홈페이지</p></Link>
		</div>
	</header>
  )
}

export default Header;