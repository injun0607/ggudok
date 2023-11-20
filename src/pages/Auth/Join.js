import React from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { join } from '../../redux/actions/userActions';

const Join = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleJoin = (e) => {
    e.preventDefault();
    const emailId = e.target.emailid.value;
    const password = e.target.password.value;
    if (emailId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      dispatch(Join(emailId, password));
      navigate('/Home');
    }
  };

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>3초만에 끝나는 간편회원가입</h2></div>
        <div className={style.form}>
          <div className={style.easySignup}>
            <Link to="http://ec2-43-202-123-248.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/naver" className={`${style.sns} ${style.naver}`}>
              <img src='../images/icons/icon_naver.png' alt='naver' />
              <p>네이버 간편가입하기</p>
            </Link>
            <Link to="http://ec2-43-202-123-248.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/kakao" className={`${style.sns} ${style.kakao}`}>
              <img src='../images/icons/icon_kakaotalk.png' alt='kakao' />
              <p>카카오톡 간편가입하기</p>
            </Link>
          </div>
          <div className={style.doublebutton}>
            <Link className="btn btn_normal" onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
            <Link to="/Auth/JoinEmail" className="btn btn_full">이메일로 회원가입하기</Link>
          </div>
        </div>
      </div>
    </section>
  )
}

export default Join;