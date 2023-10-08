import React from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../styles/Auth.module.css'

const Quit = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();


  const handleQuit = (e) => {
    e.preventDefault();
    const emailId = e.target.emailid.value;
    const password = e.target.password.value;
    if (emailId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      navigate('/Home');
    }
  };

  return (
    <section className={`${style.contactus} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>회원탈퇴</h2></div>
        
      </div>
    </section>
  )
}

export default Quit;