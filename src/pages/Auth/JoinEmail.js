import React, { useState } from 'react';
import axios from 'axios';
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
  const [passwordCheck, setpasswordCheck] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const handleChangePassword = () => {
    if (password === passwordCheck) {
      // 비밀번호가 일치할 때 처리
      setErrorMessage('');
    } else {
      // 비밀번호가 일치하지 않을 때 처리
      setErrorMessage('비밀번호가 일치하지 않습니다.');
    }
  };


  const isEmailSent = useSelector(state => state.user.isEmailSent);

  const handleSendEmail = async(emailAddress) => {
    try {
      const response = await axios.post('/member/emailCheck', { emailAddress });
      if (response.status === 200) {
        console.log('Email sent successfully.');
      } else {
        console.error('Failed to send email.');
      }
    } catch (error) {
      console.error('Error sending email:', error);
    }
  }

  const handleJoin = async(e) => {
    e.preventDefault();
    const memberName = e.target.memberName.value;
    const loginId = e.target.loginId.value;
    const password = e.target.password.value;
    const gender = e.target.gender.value;
    const age = e.target.age.value;
    const phoneNumber = e.target.phoneNumber.value;
    if (loginId === '') {
      alert('이메일 아이디를 입력하세요.')
    } else if (password === '') {
      alert('비밀번호를 입력하세요.')
    } else {
      try{
        const response = await axios.post('/member/register', {
          memberName: memberName,
          loginId: loginId,
          password: password,
          passwordCheck: passwordCheck,
          gender: gender,
          age: age,
          phoneNumber: phoneNumber,
        });
        if (response.status === 200) {
          navigate('/Home');
        } else {
          alert(`회원가입에 실패하였습니다. 다시 작성해주시기 바랍니다.`)
        }
      } catch (error) {
        console.log('Error Member Register :', error);
        alert(`회원가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
      }
    }
  };

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>이메일 회원가입</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleJoin }>
            <div className={style.inputwrap}>
              <input type='text' name='loginId' placeholder='이메일 아이디를 입력하세요.' />
              <button type='button' onClick={handleSendEmail} className='btn btn_normal_b'>인증 이메일 {isEmailSent && '다시'} 보내기</button>
              {isEmailSent && <input type='text' name='emailauth' placeholder='이메일로 전송된 인증번호를 입력하세요.' />}
              <input
                type="password"
                name="password"
                autoComplete="off"
                placeholder="비밀번호를 입력하세요."
                value={password}
                onChange={(e) => {
                  setPassword(e.target.value);
                  handleChangePassword();
                }}
              />
              <input
                type="password"
                name="passwordCheck"
                autoComplete="off"
                placeholder="비밀번호를 한번 더 입력하세요."
                value={passwordCheck}
                onChange={(e) => {
                  setpasswordCheck(e.target.value);
                  handleChangePassword(); // 비밀번호 확인 변경 시 확인
                }}
              />
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              <input type='text' name='memberName' placeholder='이름을 입력하세요.' />
              <input type='number' name='age' placeholder='나이를 숫자만 입력하세요.' />
              <select className={`${style.select}`} name='gender'>
                <option value=''>성별을 선택하세요.</option>
                <option value='man'>남성</option>
                <option value='woman'>여성</option>
              </select>
              <input type='text' name='phoneNumber' placeholder='휴대폰 번호를 숫자만 입력하세요.' />
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