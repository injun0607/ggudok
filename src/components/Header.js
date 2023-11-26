import React, { useEffect, useState, useRef } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
/* css import */
import style from '../styles/Header.module.css';
// redux import
import { logout } from '../redux/actions/userActions';
import { setSearchQuery } from '../redux/actions/searchActions';
import { fetchCategory, setDropCategory } from '../redux/actions/categoryActions';

const NO_ICON_URL = '/images/common/logo_grey.png';

const Header = ({ isAdminUser }) => {
  return (
	<header>	
		<Topheader isAdminUser={isAdminUser} />
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
const Topheader = ({ isAdminUser }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const isLoggedIn = useSelector(state => state.user.isLoggedIn);
  const searchQuery = useSelector(state => state.search.searchQuery);
	const handleLogout = () => {
    dispatch(logout());
		alert('로그아웃되었습니다. ')
    navigate('/Home');
  };
	const handleSearchChange = (e) => {
		dispatch(setSearchQuery(e.target.value));
	}
	const handleSearchSubmit = (e) => {
		e.preventDefault();
		
		if(searchQuery.length === 0){alert('검색어를 한 글자 이상 입력하세요.')}
		else {navigate(`/SearchItemlist?q=${searchQuery}`);}
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
						{isAdminUser && <Link to="/Admin/AdminHome" className={style.pointAdmin} target='_blank'><span className='material-icons'>settings</span><p>Admin</p></Link>}
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
	
  const [IsLoading, setIsLoading] = useState(true);

  // ************************** 추천 아이템 / 헤더 fetch ***************************
  const fetchCategoryData = async () => {
    try {
      const response = await axios.get(`/home`);
      const data = response.data;
      if(data){
        dispatch(fetchCategory(data.categoryList))
      } else {
				dispatch(fetchCategory([]))
      }
    } catch (error) {
      console.error('Error fetching item:', error);
      alert(`서비스를 가져오던 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchCategoryData();
  }, [dispatch]);

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
		categories && <nav className={style.allcategory}>
			<div className={style.onedepth} onClick={ handleTwodepth }>
				<span className='material-icons'>menu</span>
				<p>전체카테고리</p>
			</div>
			{ dropCategory ? 
				<div className={style.twodepth} ref={dropdownRef}>
					{
						categories.map((category, index) => 
						<Link
							to={`/Category/${category.categoryEng}`}
							className={style.depths}
							key={index}
						>
							<img src={category.categoryImage || NO_ICON_URL} alt={category.categoryName} />
                <p>{category.categoryName}</p>
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