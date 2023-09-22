const initialState = {
  isLoggedIn: false,

  // ******************** 회원가입 *******************
  isEmailSent: false,
  isEmailChecked: false,
  isPassval: false,
  isPhoneval: false,
  // 회원가입시 작성 사용자정보
  emailValNumber: '',
  memberName: '',
  loginId: '',
  password: '',
  passwordCheck: '',
  gender: '',
  age: '',
  phoneNumber: '',
};
  
const userReducer = (state = initialState, action) => {
  switch (action.type) {
    // ****************************로그인****************************
    case 'LOGIN_SUCCESS':
      return {
        ...state,
        loginId: action.payload.loginId,
        password: action.payload.password,
        isLoggedIn: true,
      };
    case 'LOGIN_FAILURE':
      return {
        ...state,
        isLoggedIn: false,
      };
    case 'LOGOUT':
      return {
        ...state,
        loginId: null,
        password: null,
        isLoggedIn: false,
      };
    // **************************회원가입*****************************
    case 'JOIN':
      return {
        ...state,
        memberName: action.payload.memberName,
        loginId: action.payload.loginId,
        password: action.payload.password,
        passwordCheck: action.payload.passwordCheck,
        gender: action.payload.gender,
        age: action.payload.age,
        phoneNumber: action.payload.phoneNumber,
      };
    case 'JOIN_LOGINID':
      return {
        ...state,
        loginId: action.payload,
      };
    case 'JOIN_AGE':
      return {
        ...state,
        age: action.payload,
      };
    case 'JOIN_GENDER':
      return {
        ...state,
        gender: action.payload,
      };
    case 'JOIN_MEMBERNAME':
      return {
        ...state,
        memberName: action.payload,
      };
    case 'JOIN_PASSWORD':
      return {
        ...state,
        password: action.payload,
      };
    case 'JOIN_PASSWORDCHECK':
      return {
        ...state,
        passwordCheck: action.payload,
      };
    case 'JOIN_PHONENUMBER':
      return {
        ...state,
        phoneNumber: action.payload,
      };
    case 'JOIN_EMAILVALNUMBER':
      return {
        ...state,
        emailValNumber: action.payload,
      };

    case 'IS_VALID_PASSWORD':
      return {
        ...state,
        isPassval: action.payload,
      };
    case 'IS_VALID_PHONENUMBER':
      return {
        ...state,
        isPhoneval: action.payload,
      };
    case 'IS_EMAIL_SENT':
      return {
        ...state,
        isEmailSent: action.payload,
      };
    default:
      return state;
  }
};

export default userReducer;
  