import React from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../styles/Auth.module.css'

const Contactus = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleContact = (e) => {
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
    <div className={`${style.contactus} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h4>언제든지 열려 있어요</h4></div>
        <div className={style.form}>
          <form onSubmit={ handleContact }>
            <div className={style.inputwrap}>
              <input type='text' name='name' placeholder='성함을 입력해주세요.' className={style.input_half} />
              <input type='text' name='email' placeholder='이메일 주소를 입력해주세요.' className={style.input_half} />
              <input type='text' name='subject' placeholder='제목을 입력해주세요.' />
              <textarea type='text' name='content' />
            </div>
            <div className={style.doublebutton}>
              <button type='submit' className='btn btn_full'>메일 보내기</button>
              <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
}

export default Contactus;