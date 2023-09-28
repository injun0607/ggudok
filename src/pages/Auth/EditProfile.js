import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { editMemberinfo, setMemberinfo, setValidPassword, setAge, setGender, setValidPhoneNumber, setPhoneNumber, setPassword, setNewPassword, setNewPasswordCheck, setIsLoading } from '../../redux/actions/userActions';

const EditProfile = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isPassval = useSelector(state => state.user.isPassval);
  const isPhoneval = useSelector(state => state.user.isPhoneval);
  const memberName = useSelector(state => state.user.memberName);
  const loginId = useSelector(state => state.user.loginId);
  const password = useSelector(state => state.user.password);
  const newPassword = useSelector(state => state.user.newPassword);
  const newPasswordCheck = useSelector(state => state.user.newPasswordCheck);
  const gender = useSelector(state => state.user.gender);
  const age = useSelector(state => state.user.age);
  const phoneNumber = useSelector(state => state.user.phoneNumber);
  const IsLoading = useSelector(state => state.user.IsLoading);
  
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageP, seterrorMessageP] = useState('');
  
	// 회원 정보 조회 요청
  const fetchMemberInfo = async () => {
    console.log('1. 세션 상태 조회 시작...')
    try {
      const response = await axios.get('/member/update');
      const userData = response.data;
      console.log('2. 세션 상태 조회 성공')
      console.log('2.5. 세션 데이터 있나?', userData, response.data)
      if (userData.gender && userData.age && userData.phoneNumber) {
        console.log('3. 세션 데이터 있음', userData)
        dispatch(setAge(userData.age));
        dispatch(setGender(userData.gender));
        dispatch(setPhoneNumber(userData.phoneNumber));
        // dispatch(setMemberinfo(userData));
        dispatch(setIsLoading(false));
        dispatch(setValidPhoneNumber(true))
        console.log('4. 세션 적용 완료')
        console.log(`phoneNumber : ${phoneNumber} \n gender : ${gender} \n age : ${age}`)
      }
    } catch (error) {
      console.error('Error fetch set member information :', error);
      dispatch(setIsLoading(true));
    }
  };
	useEffect(() => {
		fetchMemberInfo();
	}, [])

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

  const handleEdit = (e) => {
    e.preventDefault();
    const userData = {
      password,
      newPassword,
      newPasswordCheck,
      gender,
      age,
      phoneNumber,
      isPassval,
      isPhoneval,
    };
    dispatch(editMemberinfo(userData, navigate));
  };

  return (
    !IsLoading && <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>회원정보수정</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleEdit }>
            <div className={style.inputwrap}>
              <input type="text" name='emailid' value={loginId} readOnly />
              <input
                type="password" name="password" autoComplete="off"
                placeholder="현재 비밀번호를 입력하세요."
                value={password}
                onChange={(e) => {
                  dispatch(setPassword(e.target.value));
                }}
              />
              <input
                type="password" name="newPassword" autoComplete="off"
                placeholder="비밀번호를 변경할 경우에만 입력하세요."
                value={newPassword}
                onChange={(e) => {
                  dispatch(setNewPassword(e.target.value));
                  handleChangePassword(e.target.value, newPasswordCheck);
                }}
              />
              <input
                type="password" name="newPasswordCheck" autoComplete="off"
                placeholder="변경할 비밀번호를 한번 더 입력하세요."
                value={newPasswordCheck}
                onChange={(e) => {
                  dispatch(setNewPasswordCheck(e.target.value));
                  handleChangePassword(newPassword, e.target.value);
                }}
              />
              {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
              
              <input type='text' name='memberName'
                placeholder='이름을 입력하세요.' value={memberName} readOnly />
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