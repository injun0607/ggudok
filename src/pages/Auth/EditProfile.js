import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { Link, useNavigate } from 'react-router-dom';
// css import
import style from '../../styles/Auth.module.css'
// redux import
import { editMemberinfo, editSocialMemberinfo, setNewMemberImg, setRole, setValidPassword, setAge, setGender, setValidPhoneNumber, setPhoneNumber, setPassword, setNewPassword, setNewPasswordCheck, setIsLoading } from '../../redux/actions/userActions';

const EditProfile = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const isPassval = useSelector(state => state.user.isPassval);
  const isPhoneval = useSelector(state => state.user.isPhoneval);

  const [memberImg, setMemberImg] = useState(null);

  const memberName = useSelector(state => state.user.memberName);
  const newMemberImg = useSelector(state => state.user.newMemberImg);
  const loginId = useSelector(state => state.user.loginId);
  const password = useSelector(state => state.user.password);
  const newPassword = useSelector(state => state.user.newPassword);
  const newPasswordCheck = useSelector(state => state.user.newPasswordCheck);
  const gender = useSelector(state => state.user.gender);
  const age = useSelector(state => state.user.age);
  const phoneNumber = useSelector(state => state.user.phoneNumber);
  const role = useSelector(state => state.user.role);
  const IsLoading = useSelector(state => state.user.IsLoading);
  
  const [errorMessage, setErrorMessage] = useState('');
  const [errorMessageP, seterrorMessageP] = useState('');
  
	// 회원 정보 조회 요청
  const fetchMemberInfo = async () => {
    try {
      const response = await axios.get('/member/update');
      const userData = response.data;
      if (userData.gender && userData.age) {
        dispatch(setAge(userData.age));
        dispatch(setGender(userData.gender));
        if(userData.phoneNumber){
          dispatch(setPhoneNumber(userData.phoneNumber));
        }
        dispatch(setValidPhoneNumber(true));
        if(userData.memberImg){
          setMemberImg(userData.memberImg);
        }
        if(userData.role){
          dispatch(setRole(userData.role));
        }
      }
    } catch (error) {
      console.error('Error fetch set member information :', error);
    } finally {
      dispatch(setIsLoading(false));
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

  const convertBlobURLToBlob = async (blobURL) => {
    try {
      const response = await fetch(blobURL);
      const blobData = await response.blob();
      return blobData;
    } catch (error) {
      console.error('Blob URL을 Blob 객체로 변환하는 중에 오류가 발생했습니다:', error);
      throw error;
    }
  };
  
  const handleImageUpload = async () => {
    try {
      if (memberImg) {
        const blobData = await convertBlobURLToBlob(memberImg);
        const formData = new FormData();
        formData.append('profileImage', blobData);
        
        const response = await axios({
          method: "POST",
          url: `/member/update/image`,
          charset: 'utf-8',
          headers: {
            "Content-Type": "multipart/form-data",
          },
          data: formData
        })
        
        const imageUrl = response.data.imageUrl;
        dispatch(setNewMemberImg(imageUrl));
      }
    } catch (error) {
      console.error('이미지 업로드 오류:', error);
    }
  };
  
  const handleEdit = async(e) => {
    e.preventDefault();
    if(role !== 'SOCIAL'){if(!password){
      alert('현재 비밀번호를 입력해주세요.')
      return;
    }}
    await handleImageUpload();
    const userSocialData = {
      gender,
      age,
      phoneNumber,
      isPhoneval,
      newMemberImg,
      role,
    };
    const userData = {
      password,
      newPassword,
      newPasswordCheck,
      gender,
      age,
      phoneNumber,
      isPassval,
      isPhoneval,
      newMemberImg,
      role,
    };
    if(role === 'SOCIAL'){ dispatch(editSocialMemberinfo(userSocialData, navigate)); }
    else{ dispatch(editMemberinfo(userData, navigate)); }

    dispatch(setPassword(''));
    dispatch(setNewPassword(''));
    dispatch(setNewPasswordCheck(''));
    dispatch(setGender(''));
    dispatch(setAge(''));
    dispatch(setPhoneNumber(''));
  };
  
	useEffect(() => {
		fetchMemberInfo();
	}, [])

  const NO_IMAGE_URL = '/images/common/noimg.png';

  return (
    !IsLoading && <section className={`${style.join} ${style.auth}`}>
      <div className='webwidth webwidth_pd'>
        <div className='page_tit'><h2>회원정보수정</h2></div>
        <div className={style.form}>
          <form onSubmit={ handleEdit }>
            
            <div className={style.userImg}>
              <div className={style.circle}>
                <img src={memberImg || NO_IMAGE_URL} alt='유저 이미지' />
              </div>
              <input type="file" id="file" accept="image/*" className='inputFile' name="profileImage"
                onChange={(e) => {
                  const imageFile = e.target.files[0];
                  setMemberImg(URL.createObjectURL(imageFile));
                }}
              />
              <label htmlFor="file">프로필 사진 선택하기</label>
            </div>
            <div className={style.inputwrap}>
              {role !== 'SOCIAL' && <>
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
              </>}
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
              <Link className='btn btn_normal' onClick={ ()=>{ navigate(-1) } }>뒤로 가기</Link>
              <button type='submit' className='btn btn_full'>수정 완료</button>
            </div>
          </form>
        </div>
      </div>
    </section>
  )
}

export default EditProfile;