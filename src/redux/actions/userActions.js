import axios from 'axios';
import { setCookie, getCookie, removeCookie } from './cookieActions';

// Action 타입
export const LOGIN_SUCCESS = 'LOGIN_SUCCESS';
export const LOGIN_FAILURE = 'LOGIN_FAILURE';
export const LOGOUT = 'LOGOUT';
export const REFRESH_TOKEN_SUCCESS = 'REFRESH_TOKEN_SUCCESS';

export const setLoggedIn = (userData) => {
  return {
    type: 'SET_LOGGED_IN',
    payload: userData,
  };
}
export const setAdminUser = (isAdminUser) => {
  return {
    type: 'SET_ADMIN_USER',
    payload: isAdminUser,
  };
}
export const loginSuccess = (userData) => ({
  type: LOGIN_SUCCESS,
  payload: userData,
});
export const loginFailure = () => ({
  type: LOGIN_FAILURE,
});
export const refreshTokenSuccess = (newAccessToken) => ({
  type: REFRESH_TOKEN_SUCCESS,
  payload: newAccessToken,
});
// *********************** 마이페이지 *************************
export const fetchLikedItemsSuccess = (likedItems) => {
  return {
    type: 'FETCH_LIKED_ITEMS_SUCCESS',
    payload: likedItems,
  };
}
export const fetchMemberImg = (memberImg) => {
  return {
    type: 'FETCH_MEMBERIMG',
    payload: memberImg,
  };
}
// ***********************로그인*************************
export const login = (userData, navigate) => async (dispatch) => {
  const { loginId, password } = userData;
  try {
    const response = await axios.post('/login', {
      loginId: loginId,
      password: password,
    });
    
    if (response.status === 200) {
      const access = response.headers.access;
      const refresh = response.headers.refresh;
      
      // Access Token을 쿠키에 저장
      setCookie('access', access, { path: '/' });
      setCookie('refresh', refresh, { path: '/' });

      dispatch(loginSuccess({ loginId }));

      navigate('/Home');
    } else {
      dispatch({ type: 'LOGIN_FAILURE' });
    }
  } catch (error) {
    console.log('Error logging in:', error);
    dispatch(loginFailure());
    alert(`로그인 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};

// Refresh Token을 통해 새로운 AccessToken을 가져오는 액션
export const refreshToken = (navigate) => async (dispatch) => {
  const refresh = getCookie('refresh');
  if (!refresh) {
    return;
  }
  try {
    const response = await axios.post('/getSession', { refresh });
    if (response.status === 200) {
      const newAccessToken = response.headers.access;
      const newRefreshToken = response.headers.refresh;

      setCookie('access', newAccessToken, { path: '/' });
      setCookie('refresh', newRefreshToken, { path: '/' });

      dispatch(refreshTokenSuccess(newAccessToken));
    } else {
      // RefreshToken을 통한 갱신에 실패할 경우
      dispatch(logout());
      alert('접속정보가 만료되었습니다. 다시 로그인해주세요.')
      navigate('/Home');
    }
  } catch (error) {
    console.error('Error refreshing token:', error);
  }
};

export const logout = () => async (dispatch) => {
  removeCookie('access');
  removeCookie('refresh');
  dispatch({ type: LOGOUT });
};

// *********************** 회원가입 및 회원정보수정 **********************
export const join = (userData, navigate) => async(dispatch) => {
  const {
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
  } = userData;
  
  if(loginId === ''){ alert('이메일 아이디 인증을 완료하세요.')
  } else if(emailValNumber === fetchedEmailValNumber) {
    alert('인증번호가 틀렸습니다.')
    dispatch(setEmailValNumber(''));
  } else if(!isPassval) {
    alert('비밀번호를 올바르게 입력하세요.')
    dispatch(setPassword(''));
    dispatch(setPasswordCheck(''));
  } else if(memberName === ''){ alert('이름을 입력하세요.')
  } else if(age === ''){ alert('나이를 입력하세요.')
  } else if (gender === '성별을 선택하세요.' || gender === ''){ alert('성별을 선택하세요.')
  } else if (!isPhoneval){
    alert('휴대폰 번호를 올바르게 입력하세요.')
    dispatch(setPhoneNumber(''));
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
        alert(`회원가입이 완료되었습니다. 로그인해주시기 바랍니다.`)
        navigate('/Auth/Login');
      } else {
        alert(`회원가입에 실패하였습니다. 다시 작성해주시기 바랍니다.`)
        // window.location.reload();
      }
    } catch (error) {
      console.log('Error Member Register :', error);
      alert(`회원가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  }
}
export const joinAfter = (userData, navigate) => async(dispatch) => {
  const {
    access,
    memberName,
    gender,
    age,
  } = userData;
  
  if(!access){
    alert('회원가입 중 오류가 발생했습니다. 다시 시도해 주세요.');
    navigate('/Auth/Join');
  } else {
    try{
      const response = await axios.post('/member/oauth/update', {
        access: access,
        memberName: memberName,
        gender: gender,
        age: age,
      });
      if (response.status === 200) {
        alert(`회원가입이 완료되었습니다. 로그인해주시기 바랍니다.`)
        dispatch(logout());
        navigate('/Auth/Login');
      } else {
        alert(`회원가입에 실패하였습니다. 다시 작성해주시기 바랍니다.`)
      }
    } catch (error) {
      console.log('Error Member Register :', error);
      alert(`회원가입 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  }
}
export const setMemberinfo = (userData) => {
  return {
    type: 'SET_MEMBERINFO',
    payload: userData,
  };
}
export const editMemberinfo = (userData, navigate) => async(dispatch) => {
  const {
    password,
    newPassword,
    newPasswordCheck,
    gender,
    age,
    phoneNumber,
    isPassval,
    isPhoneval,
    updateMemberImg,
    role,
    } = userData;
    debugger;
  if(password === ''){ alert('비밀번호를 입력하세요.')
  } else if ((newPassword !== '' || newPasswordCheck !== '') && !isPassval) {
    alert('변경할 비밀번호를 올바르게 입력하세요.')
    dispatch(setNewPassword(''));
    dispatch(setNewPasswordCheck(''));
  } else if(age === ''){ alert('나이를 입력하세요.')
  } else if (gender === '성별을 선택하세요.' || gender === ''){ alert('성별을 선택하세요.')
  } else if (!isPhoneval){
    alert('휴대폰 번호를 올바르게 입력하세요.')
    dispatch(setPhoneNumber(''));
  } else {
    try{
      const response = await axios.post('/member/update', {
        gender: gender,
        age: age,
        password: password,
        newPassword: newPassword,
        memberImg: updateMemberImg,
        newPasswordCheck: newPasswordCheck,
        phoneNumber: phoneNumber,
        role: role,
      });
      if (response.status === 200) {
        alert(`회원정보수정이 완료되었습니다.`)
        navigate('/Home');
      } else {
        alert(`회원정보수정에 실패하였습니다. 다시 작성해주시기 바랍니다.`)
        // window.location.reload();
      }
    } catch (error) {
      console.log('Error Member Register :', error);
      alert(`회원정보수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
      // window.location.reload();
    } finally {
      dispatch(setPassword(''));
      dispatch(setNewPassword(''));
      dispatch(setNewPasswordCheck(''));
    }
  }
}
export const editSocialMemberinfo = (userSocialData, navigate) => async(dispatch) => {
  const {
    gender,
    age,
    phoneNumber,
    isPhoneval,
    newMemberImg,
    role,
  } = userSocialData;
  
  if(age === ''){ alert('나이를 입력하세요.')
  } else if (gender === '성별을 선택하세요.' || gender === ''){ alert('성별을 선택하세요.')
  } else if (!isPhoneval){
    alert('휴대폰 번호를 올바르게 입력하세요.')
    dispatch(setPhoneNumber(''));
  } else {
    try{
      const response = await axios.post('/member/update', {
        gender: gender,
        age: age,
        memberImg: newMemberImg,
        phoneNumber: phoneNumber,
        role: role,
      });
      if (response.status === 200) {
        alert(`회원정보수정이 완료되었습니다.`)
        navigate('/Home');
      } else {
        alert(`회원정보수정에 실패하였습니다. 다시 작성해주시기 바랍니다.`)
        // window.location.reload();
      }
    } catch (error) {
      console.log('Error Member Register :', error);
      alert(`회원정보수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
      // window.location.reload();
    } finally {
      dispatch(setPassword(''));
      dispatch(setNewPassword(''));
      dispatch(setNewPasswordCheck(''));
    }
  }
}
export const setLoginId = (loginId) => {
  return {
    type: 'SET_LOGINID',
    payload: loginId,
  };
}
export const setRole = (role) => {
  return {
    type: 'SET_ROLE',
    payload: role,
  };
}
export const setNewMemberImg = (newMemberImg) => {
  return {
    type: 'SET_NEW_MEMBERIMG',
    payload: newMemberImg,
  };
}
export const setMemberImg = (memberImg) => {
  return {
    type: 'SET_MEMBERIMG',
    payload: memberImg,
  };
}
export const setMemberName = (memberName) => {
  return {
    type: 'SET_MEMBERNAME',
    payload: memberName,
  };
}
export const setAge = (age) => {
  return {
    type: 'SET_AGE',
    payload: age,
  };
}
export const setGender = (gender) => {
  return {
    type: 'SET_GENDER',
    payload: gender,
  };
};
export const setPassword = (password) => {
  return {
    type: 'SET_PASSWORD',
    payload: password,
  };
}
export const setPasswordCheck = (passwordCheck) => {
  return {
    type: 'SET_PASSWORDCHECK',
    payload: passwordCheck,
  };
}
export const setNewPassword = (newPassword) => {
  return {
    type: 'SET_NEW_PASSWORD',
    payload: newPassword,
  };
}
export const setNewPasswordCheck = (newPasswordCheck) => {
  return {
    type: 'SET_NEW_PASSWORDCHECK',
    payload: newPasswordCheck,
  };
}
export const setValidPassword = (isPassval) => {
  return {
    type: 'IS_VALID_PASSWORD',
    payload: isPassval,
  };
}
export const setValidPhoneNumber = (isPhoneval) => {
  return {
    type: 'IS_VALID_PHONENUMBER',
    payload: isPhoneval,
  };
}
export const setEmailValNumber = (emailValNumber) => {
  return {
    type: 'SET_EMAILVALNUMBER',
    payload: emailValNumber,
  };
}
export const setPhoneNumber = (phoneNumber) => {
  return {
    type: 'SET_PHONENUMBER',
    payload: phoneNumber,
  };
}
export const setEmailSent = (isEmailSent) => {
  return {
    type: 'IS_EMAIL_SENT',
    payload: isEmailSent,
  };
}
export const setIsLoading = (isLoading) => {
  return {
    type: 'SET_IS_LOADING',
    payload: isLoading,
  };
};

// 회원탈퇴 모달 제어
export const setQuitModal = (modal) => {
  return {
    type: 'SET_QUITMODAL',
    payload: modal,
  };
};