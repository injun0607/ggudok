import React from 'react';
import { Link } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
/* css import */
import style from '../../styles/Admin/AdminHeader.module.css';


const Header = () => {
  const dispatch = useDispatch();

  return (
	<header>	
		<div className={style.header}>
			<h1 className={style.logo}>
				<Link to="/Admin/AdminHome">꾸<span>독</span></Link>
			</h1>
			<div className={style.nav}>
				<Link to="/Admin/Category">카테고리 관리</Link> 
				<Link to="/Admin/Items">구독서비스 관리</Link>
				<Link to="/Admin/Tags">태그 관리</Link>
				<Link to="/Admin/Events">이벤트 관리</Link>
			</div>
			<Link to="/Home" className={style.pointAdmin} target='_blank'><span className='material-icons'>home</span><p>홈페이지</p></Link>
		</div>
	</header>
  )
}

export default Header;