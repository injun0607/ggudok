import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { join, sendEmail } from '../../redux/actions/userActions';

const JoinEmail = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isEmailSent = useSelector(state => state.user.isEmailSent);

  const handleSendEmail = () => {
    dispatch(sendEmail());
  }

  const handleJoin = (e) => {
    e.preventDefault();
    const emailId = e.target.emailid.value;
    const password = e.target.password.value;
    if (emailId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      dispatch(join(emailId, password));
      navigate('/Home');
    }
  };

  return (
    <div className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h4>이메일 회원가입</h4></div>
        <div className={style.form}>
          <form onSubmit={ handleJoin }>
            <div className={style.inputwrap}>
              <input type='text' name='emailid' placeholder='이메일 아이디를 입력하세요.' />
              <button type='button' onClick={handleSendEmail} className='btn btn_normal_b'>인증 이메일 {isEmailSent && '다시'} 보내기</button>
              {isEmailSent && <input type='text' name='emailauth' placeholder='이메일로 전송된 인증번호를 입력하세요.' />}
              <input type='text' name='password' autoComplete="off" placeholder='비밀번호를 입력하세요.' />
            </div>
            <button type='submit' className='btn btn_full'>회원가입</button>
          </form>
          <div className={style.doublebutton}>
            <Link to="/Auth/Join" className='btn btn_full02'>간편회원가입 하러가기</Link>
            <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JoinEmail;