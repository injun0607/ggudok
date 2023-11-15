import React from 'react'
import { Link } from 'react-router-dom';
// css import
import style from '../styles/Item.module.css'

const ErrorLogin = () => {
  return (
    <form className='modal_conts'>
      <div className='modal_tit'>
			<span className='material-icons'>warning</span>
        <h2>로그인이 필요한 페이지입니다.</h2>
      </div>
      <div className={style.modalCont}>
        <div className={style.doublebutton}>
          <Link className='btn_s btn_normal' to={'/Auth/Join'}>회원가입</Link>
          <Link className='btn_s btn_full' to={'/Auth/Login'}>로그인</Link>
        </div>
      </div>
    </form>
  )
}

export default ErrorLogin;