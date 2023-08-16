const initialState = {
  isEmailSent: false,
  isLoggedIn: false,
  emailid: null,
  password: null,
  username: null,
  // 기타 사용자 정보
};
  
const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'JOIN':
      return {
        ...state,
        emailid: action.payload.emailid,
        password: action.payload.password,
      };
    case 'SEND_EMAIL':
      return {
        ...state,
        isEmailSent: true,
      };
    case 'LOGIN':
      return {
        ...state,
        emailid: action.payload.emailid,
        password: action.payload.password,
        isLoggedIn: true,
      };
    case 'LOGOUT':
      return {
        ...state,
        emailid: null,
        password: null,
        isLoggedIn: false,
      };
    default:
      return state;
  }
};

export default userReducer;
  