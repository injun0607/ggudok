import React from 'react';
import { useDispatch } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { login } from '../../redux/actions/userActions';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogin = (e) => {
    e.preventDefault();
    const emailId = e.target.emailid.value;
    const password = e.target.password.value;
    if (emailId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      dispatch(login(emailId, password));
      navigate('/Home');
    }
  };

  return (
    <section className={`${style.login} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h4>로그인</h4></div>
        <div className={style.form}>
          <form onSubmit={ handleLogin }>
            <div className={style.inputwrap}>
              <input type='text' name='emailid' placeholder='이메일 아이디를 입력하세요.' />
              <input type='text' name='password' autoComplete="off" placeholder='비밀번호를 입력하세요.' />
            </div>
            <button type='submit' className='btn btn_full'>로그인</button>
          </form>
          <div className={style.doublebutton}>
            <Link to="/Home" className='btn btn_full02'>간편로그인</Link>
            <Link to="/Auth/Join" className='btn btn_normal'>회원가입</Link>
          </div>
        </div>
      </div>
    </section>
  )
}

export default Login;