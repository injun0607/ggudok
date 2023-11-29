import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { join, setEmailValNumber, setLoginId, setValidPassword, setMemberName, setAge, setGender, setValidPhoneNumber, setPhoneNumber, setEmailSent, setPassword, setPasswordCheck } from '../../redux/actions/userActions';

const JoinEmail = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const isEmailSent = useSelector(state => state.user.isEmailSent);
  const isPassval = useSelector(state => state.user.isPassval);
  const isPhoneval = useSelector(state => state.user.isPhoneval);
  const emailValNumber = useSelector(state => state.user.emailValNumber);
  const memberName = useSelector(state => state.user.memberName);
  const loginId = useSelector(state => state.user.loginId);
  const password = useSelector(state => state.user.password);
  const passwordCheck = useSelector(state => state.user.passwordCheck);
  const gender = useSelector(state => state.user.gender);
  const age = useSelector(state => state.user.age);
  const phoneNumber = useSelector(state => state.user.phoneNumber);
  
  const [fetchedEmailValNumber, setFetchedEmailValNumber] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageP, seterrorMessageP] = useState('');


  // 비밀번호 유효성 검사 및 비밀번호 일치 검사
  const handleChangePassword = (newPassword, newPasswordCheck) => {
    // 비밀번호 유효성 검사
    const regPassword = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
    const isPasswordReg = regPassword.test(newPassword);
    // 비밀번호 일치 검사
    const doPasswordsMatch = isPasswordReg && newPassword === newPasswordCheck;
  
    // 오류 메시지 설정
    if (!isPasswordReg) {
      setErrorMessage('비밀번호는 문자, 숫자, 특수문자의 조합으로 8자 이상으로 입력해주세요. (@ 제외)');
    } else if (!doPasswordsMatch) {
      setErrorMessage('비밀번호가 일치하지 않습니다.');
    } else {
      setErrorMessage('');
    }
  
    // Redux 상태 업데이트
    dispatch(setValidPassword(isPasswordReg && doPasswordsMatch));
  };

  // 휴대폰번호 검사
  const handleChangePhoneNumber = (newRhoneNumber) => {
    // 휴대폰번호 유효성 검사
    const regPhoneNumber = /^[0-9]+$/;
    const isPhoneNumberReg = regPhoneNumber.test(newRhoneNumber);

    // 오류 메시지 설정
    if (!isPhoneNumberReg){
      seterrorMessageP('휴대폰번호는 숫자만 입력해주세요.');
    } else {
      seterrorMessageP('');
    }
  
    // Redux 상태 업데이트
    dispatch(setValidPhoneNumber(isPhoneNumberReg));
  }

  // 이메일 인증번호 fetch 해오기
  const fetchEmailValNumber = async() => {
    try {
      const response = await axios.get('/member/emailCheck');
      setFetchedEmailValNumber(response.data.certCode);
    } catch (error) {
      console.error('Error fetching email verification number:', error);
    }
  }
  // 입력한 이메일주소로 이메일 전송
  const handleSendEmail = async(loginId) => {
    try {
      const data = { loginId: loginId };
      const response = await axios.post('/member/emailCheck', data);
      if (response.status === 200) {
        dispatch(setEmailSent(true));
        alert('이메일을 전송했습니다. 인증코드는 1234로 임시 고정되어 있습니다.');
        
        // 이메일을 성공적으로 보낸 후에 인증번호를 가져오도록 이동
        fetchEmailValNumber();
      } else {
        dispatch(setEmailSent(false));
        console.error('이메일 전송 실패');
      }
    } catch (error) {
      console.error('Error sending email:', error);
    }
  }

  // 회원가입 검사
  const handleJoin = async(e) => {
    e.preventDefault();
    const userData = {
      memberName,
      loginId,
      password,
      passwordCheck,
      gender,
      age,
      phoneNumber,
      emailValNumber,
      isPassval,
      isPhoneval,
      fetchedEmailValNumber,
    };
    dispatch(join(userData, navigate));

    dispatch(setMemberName(''));
    dispatch(setLoginId(''));
    dispatch(setPassword(''));
    dispatch(setPasswordCheck(''));
    dispatch(setGender(''));
    dispatch(setAge(''));
    dispatch(setPhoneNumber(''));
    dispatch(setEmailValNumber(''));
    dispatch(setEmailSent(false));
    dispatch(setValidPassword(false));
    dispatch(setValidPhoneNumber(false));
  };

  return (
    <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>이메일 회원가입</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleJoin }>
            <div className={style.inputwrap}>
              <input
                type='text' name='loginId' placeholder='이메일 아이디를 입력하세요.' autoComplete="off"
                value={loginId}
                onChange={(e) => dispatch(setLoginId(e.target.value))} />
              <button type='button' onClick={() => handleSendEmail(loginId)} className='btn btn_normal_b'>인증 이메일 {isEmailSent && '다시'} 보내기</button>
              {isEmailSent &&
                <input
                type='text' name='emailValNumber' value={emailValNumber} autoComplete="off" placeholder='이메일로 전송된 인증번호를 입력하세요.'
                onChange={(e) => dispatch(setEmailValNumber(e.target.value))} />              
              }
              <input
                type="password" name="password" autoComplete="off"
                placeholder="비밀번호를 입력하세요."
                value={password}
                onChange={(e) => {
                  dispatch(setPassword(e.target.value));
                  handleChangePassword(e.target.value, passwordCheck);
                }}
              />
              <input
                type="password" name="passwordCheck" autoComplete="off"
                placeholder="비밀번호를 한번 더 입력하세요."
                value={passwordCheck}
                onChange={(e) => {
                  dispatch(setPasswordCheck(e.target.value));
                  handleChangePassword(password, e.target.value);
                }}
              />
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              
              <input type='text' name='memberName' autoComplete="off"
                placeholder='이름을 입력하세요.'
                value={memberName}
                onChange={(e) => { dispatch(setMemberName(e.target.value)) }} />
              <input type='text' name='age' autoComplete="off"
                  placeholder='나이를 숫자만 입력하세요.'
                  value={age}
                  onChange={(e) => { dispatch(setAge(e.target.value)) }} />
              <select className={`${style.select}`} name='gender'
                value={gender}
                onChange={(e) => dispatch(setGender(e.target.value))}
              >
                <option value=''>성별을 선택하세요.</option>
                <option value='MAN'>남성</option>
                <option value='WOMAN'>여성</option>
              </select>
              <input
                type='text' name='phoneNumber' autoComplete="off"
                placeholder='휴대폰 번호를 숫자만 입력하세요.' 
                value={phoneNumber}
                onChange={(e) => {
                  dispatch(setPhoneNumber(e.target.value))
                  handleChangePhoneNumber(e.target.value);
                }} />
              {errorMessageP && <p style={{ color: 'red' }}>{errorMessageP}</p>}
            </div>
            <button type='submit' className='btn btn_full'>회원가입</button>
          </form>
          <div className={style.doublebutton}>
            <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
            <Link to="/Auth/Join" className='btn btn_full02'>간편회원가입 하러가기</Link>
          </div>
        </div>
      </div>
    </section>
  )
}

export default JoinEmail;