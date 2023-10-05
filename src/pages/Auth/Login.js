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

  const handleLogin = async(e) => {
    e.preventDefault();
    const loginId = e.target.loginId.value;
    const password = e.target.password.value;
    const userData = {
      loginId,
      password,
    };
    if (loginId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      dispatch(login(userData, navigate));
    }
  };

  return (
    <section className={`${style.login} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>로그인</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleLogin }>
            <div className={style.inputwrap}>
              <input type='text' name='loginId' placeholder='이메일 아이디를 입력하세요.' />
              <input type='password' name='password' autoComplete="off" placeholder='비밀번호를 입력하세요.' />
            </div>
            <button type='submit' className='btn btn_full'>로그인</button>
          </form>
          <div className={style.doublebutton}>
            <Link to="/Auth/Join" className='btn btn_normal'>회원가입</Link>
            <Link to="/Home" className='btn btn_full02'>간편로그인</Link>
          </div>
        </div>
      </div>
    </section>
  )
}

export default Login;