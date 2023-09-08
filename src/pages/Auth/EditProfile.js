import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { join, sendEmail } from '../../redux/actions/userActions';

const EditProfile = () => {
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

  const handleEdit = (e) => {
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
        <div className='page_tit'><h2>회원정보수정</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleEdit }>
            <div className={style.inputwrap}>
              <input type="text" name='emailid' value="injun0607" readOnly />
              <input
                type="password"
                name="password"
                autoComplete="off"
                placeholder="새 비밀번호를 입력하세요."
                value={password}
                onChange={(e) => setPassword(e.target.value)}
              />
              <input
                type="password"
                name="confirmPassword"
                autoComplete="off"
                placeholder="새 비밀번호를 한번 더 입력하세요."
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
              />
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              <input type='text' name='name' value="여인준" readOnly />
              <input type='text' name='mobile' placeholder='휴대폰 번호를 010-0000-0000 형태로 입력하세요.' />
            </div>
            <div className={style.doublebutton}>
              <button type='submit' className='btn btn_full'>수정 완료</button>
              <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
            </div>
          </form>
        </div>
      </div>
    </section>
  )
}

export default EditProfile;