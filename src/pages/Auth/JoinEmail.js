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
  
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleChangePassword = () => {
    if (password === confirmPassword) {
      // 비밀번호가 일치할 때 처리
      // 이 부분에 비밀번호 변경 또는 저장 로직을 추가하세요.
    } else {
      // 비밀번호가 일치하지 않을 때 처리
      setErrorMessage('비밀번호가 일치하지 않습니다.');
    }
  };


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
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>이메일 회원가입</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleJoin }>
            <div className={style.inputwrap}>
              <input type='text' name='emailid' placeholder='이메일 아이디를 입력하세요.' />
              <button type='button' onClick={handleSendEmail} className='btn btn_normal_b'>인증 이메일 {isEmailSent && '다시'} 보내기</button>
              {isEmailSent && <input type='text' name='emailauth' placeholder='이메일로 전송된 인증번호를 입력하세요.' />}
              <input
                type="password"
                name="password"
                autoComplete="off"
                placeholder="비밀번호를 입력하세요."
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <input
                type="password"
                name="confirmPassword"
                autoComplete="off"
                placeholder="비밀번호를 한번 더 입력하세요."
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              <input type='text' name='name' placeholder='이름을 입력하세요.' />
              <input type='number' name='mobile' placeholder='휴대폰 번호를 숫자만 입력하세요.' />
            </div>
            <button type='submit' className='btn btn_full'>회원가입</button>
          </form>
          <div className={style.doublebutton}>
            <Link to="/Auth/Join" className='btn btn_full02'>간편회원가입 하러가기</Link>
            <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
          </div>
        </div>
      </div>
    </section>
  )
}

export default JoinEmail;