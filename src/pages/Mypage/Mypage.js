import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, Outlet, useLocation, } from 'react-router-dom';
// component import
import Quit from '../../components/Quit';
// css import
import style from '../../styles/Mypage.module.css'
// redux import
import { fetchMemberImg, setQuitModal } from '../../redux/actions/userActions';

const NO_IMAGE_URL = '/images/common/noimg.png';

const Mypage = ({memberName}) => {
  const dispatch = useDispatch();
  const location = useLocation();
  const memberImg = useSelector(state => state.user.memberImg);
  const quitModal = useSelector(state => state.user.quitModal);
  const [IsResult, setIsResult] = useState(null);
  const [IsLoading, setIsLoading] = useState(true);
  
  // ************************** 기본 reviews fetch ***************************
  const fetchMyReviewData = async () => {
    try {
      const response = await axios.get(`/member/memberInfo`);
      const data = response.data.profileImage;
      console.log('data : ', data)

      if(data !== 0){
        dispatch(fetchMemberImg(data));
      } else {
        dispatch(fetchMemberImg(''));
      }

    } catch (error) {
      console.error('Error fetching data:', error);
    } finally {
      setIsLoading(false);
    }
  };
  useEffect(() => {
    fetchMyReviewData();
  }, [dispatch]);

  // 결과 유무
  useEffect(() => {
    setIsResult(memberImg.length > 0);
  }, [dispatch, memberImg]);

  // 탈퇴모달팝업
  const handleQuitModal = () => { dispatch(setQuitModal()) }

  // 현재 경로에 따라 클래스를 동적으로 설정
  const mySubscribeActiveClass = location.pathname.includes('MySubscribe') ? `${style.navOn}` : '';
  const myReviewActiveClass = location.pathname.includes('MyReview') ? `${style.navOn}` : '';
  const myLikeActiveClass = location.pathname.includes('MyLike') ? `${style.navOn}` : '';

  return (
    <>
    { quitModal ? <><Quit /> <div className='modalBg' onClick={ handleQuitModal }></div></> : null}
    <section className={style.pagewrapPd}>
      <div className='webwidth'>
        <div className='page_tit'><h2>마이페이지</h2></div>
        <section className={style.both}>
          <aside className={style.left}>
            <div className={style.box}>
              <div className={style.profile}>
                <div className={style.userImg}>
                  <div className={style.circle}>
                    <img src={memberImg} alt='유저 이미지' onError={(e) => {e.target.src = NO_IMAGE_URL;}}/>
                  </div>
                </div>
                <div className={style.userName}><span className={style.name}>{memberName}</span>님</div>
              </div>
              <nav className={style.category}>
                <Link to="/Auth/EditProfile">회원정보수정</Link>
                <button type='button' className={style.sm} onClick={ handleQuitModal }>회원탈퇴</button>
              </nav>
            </div>
            <nav className={style.nav}>
              <Link to="/Mypage/MySubscribe" className={mySubscribeActiveClass}>
                <img src='/images/icons/setting.svg' alt='나의 구독내역' />
                나의 구독내역
              </Link> 
              <Link to="/Mypage/MyReview" className={myReviewActiveClass}>
                <img src='/images/icons/receipt.svg' alt='내가 작성한 리뷰' />
                내가 작성한 리뷰
              </Link> 
              <Link to="/Mypage/MyLike" className={myLikeActiveClass}>
                <img src='/images/icons/hot.svg' alt='관심서비스' />
                관심서비스
              </Link> 
            </nav>
          </aside>
          <section className={style.right}>
            <Outlet />
          </section>
        </section>
      </div>
    </section>
    </>
  )
}

export default Mypage;