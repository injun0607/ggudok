import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
const Join = () => {
  const navigate = useNavigate();

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>3초만에 끝나는 간편회원가입</h2></div>
        <div className={style.form}>
          <div className={style.easySignup}>
            <div
              className={`${style.sns} ${style.naver}`}
              onClick={() => (window.location.href = 'http://ec2-43-202-123-248.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/naver')}
            >
              <img src='../images/icons/icon_naver.png' alt='naver' />
              <p>네이버 간편가입하기</p>
            </div>
            <div
              className={`${style.sns} ${style.kakao}`}
              onClick={() => (window.location.href = 'http://ec2-43-202-123-248.ap-northeast-2.compute.amazonaws.com:8080/oauth2/authorization/kakao')}
            >
              <img src='../images/icons/icon_kakaotalk.png' alt='kakao' />
              <p>카카오톡 간편가입하기</p>
            </div>
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