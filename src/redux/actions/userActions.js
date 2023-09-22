import axios from 'axios';

export const setLoggedIn = (userData) => {
  return {
    type: 'SET_LOGGED_IN',
    payload: userData,
  };
}
// ***********************로그인*************************
export const login = (userData, navigate) => async (dispatch) => {
  const { loginId, password } = userData;
  try {
    const response = await axios.post('/login', new URLSearchParams({
      loginId: loginId,
      password: password,
    }));
    if (response.status === 200) {
      dispatch({ type: 'LOGIN_SUCCESS', payload: { loginId } });
      navigate('/Home');
    } else {
      dispatch({ type: 'LOGIN_FAILURE' });
    }
  } catch (error) {
    console.log('Error logging in:', error);
    dispatch({ type: 'LOGIN_FAILURE' });
  }
};
export const logout = () => async (dispatch) => {
  try {
      const response = await axios.get('/logout');
      if (response.status === 200) {
      dispatch({ type: 'LOGOUT' });
    }
  } catch (error) {
    console.log('Error logging out:', error);
  }
};

// *********************** 회원가입 **********************
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
    dispatch(setJoinPassword(''));
    dispatch(setJoinPasswordCheck(''));
  } else if(memberName === ''){ alert('이름을 입력하세요.')
  } else if(age === ''){ alert('나이를 입력하세요.')
  } else if (gender === '성별을 선택하세요.' || gender === ''){ alert('성별을 선택하세요.')
  } else if (!isPhoneval){
    alert('휴대폰 번호를 올바르게 입력하세요.')
    dispatch(setJoinPhoneNumber(''));
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
      // window.location.reload();
    }
  }
}
export const setJoinLoginId = (loginId) => {
  return {
    type: 'JOIN_LOGINID',
    payload: loginId,
  };
}
export const setJoinMemberName = (memberName) => {
  return {
    type: 'JOIN_MEMBERNAME',
    payload: memberName,
  };
}
export const setJoinAge = (age) => {
  return {
    type: 'JOIN_AGE',
    payload: age,
  };
}
export const setJoinGender = (gender) => {
  return {
    type: 'JOIN_GENDER',
    payload: gender,
  };
};
export const setJoinPassword = (password) => {
  return {
    type: 'JOIN_PASSWORD',
    payload: password,
  };
}
export const setJoinPasswordCheck = (passwordCheck) => {
  return {
    type: 'JOIN_PASSWORDCHECK',
    payload: passwordCheck,
  };
}
export const setValidPassword = (isPassval) => {
  return {
    type: 'IS_VALID_PASSWORD',
    payload: isPassval,
  };
}
export const setValidPhoneNymber = (isPhoneval) => {
  return {
    type: 'IS_VALID_PHONENUMBER',
    payload: isPhoneval,
  };
}
export const setEmailValNumber = (emailValNumber) => {
  return {
    type: 'JOIN_EMAILVALNUMBER',
    payload: emailValNumber,
  };
}
export const setJoinPhoneNumber = (phoneNumber) => {
  return {
    type: 'JOIN_PHONENUMBER',
    payload: phoneNumber,
  };
}
export const setEmailSent = (isEmailSent) => {
  return {
    type: 'IS_EMAIL_SENT',
    payload: isEmailSent,
  };
}