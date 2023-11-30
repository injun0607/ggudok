const initialState = {
  isLoggedIn: false,
  isAdminUser: false,

  // ******************** 회원가입 *******************
  isEmailSent: false,
  isEmailChecked: false,
  isPassval: false,
  isPhoneval: false,
  // 회원가입시 작성 사용자정보
  emailValNumber: '',
  newMemberImg: '',
  memberName: '',
  loginId: '',
  password: '',
  passwordCheck: '',
  newPassword: '',
  newPasswordCheck: '',
  gender: '',
  age: '',
  phoneNumber: '',
  role: '',

  // ******************** 회원탈퇴 *******************
  quitModal: false,
  
  // ******************** 마이페이지 *******************
  memberImg: '',
  // 관심서비스
  likedItems: [],

  IsLoading: true,
};
  
const userReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_LOGGED_IN':
      return {
        ...state,
        isLoggedIn: true,
        loginId: action.payload.loginId,
        memberName: action.payload.memberName,
      };
    case 'SET_ADMIN_USER':
      return {
        ...state,
        isAdminUser: true,
      };

    // ************************** 마이페이지 ************************
    case 'FETCH_LIKED_ITEMS_SUCCESS':
      return {
        ...state,
        likedItems: action.payload,
      };
    case 'FETCH_MEMBERIMG':
      return {
        ...state,
        memberImg: action.payload,
      };

    // 회원탈퇴 모달 제어
    case 'SET_QUITMODAL':
      return {
        ...state,
        quitModal: !state.quitModal,
      };

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
      };
    case 'LOGOUT':
      return {
        ...state,
        loginId: '',
        password: '',
        isLoggedIn: false,
      };
    // *********************회원가입 / 회원정보수정***********************
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
    case 'SET_MEMBERINFO':
      return {
        ...state,
        gender: action.payload.gender,
        age: action.payload.age,
        phoneNumber: action.payload.phoneNumber,
      };
    case 'EDIT_MEMBERINFO':
      return {
        ...state,
        password: action.payload.password,
        newPassword: action.payload.newPassword,
        newPasswordCheck: action.payload.newPasswordCheck,
        gender: action.payload.gender,
        age: action.payload.age,
        phoneNumber: action.payload.phoneNumber,
      };
    case 'SET_LOGINID':
      return {
        ...state,
        loginId: action.payload,
      };
    case 'SET_AGE':
      return {
        ...state,
        age: action.payload,
      };
    case 'SET_GENDER':
      return {
        ...state,
        gender: action.payload,
      };
    case 'SET_NEW_MEMBERIMG':
      return {
        ...state,
        newMemberImg: action.payload,
      };
    case 'SET_MEMBERIMG':
      return {
        ...state,
        memberImg: action.payload,
      };
    case 'SET_MEMBERNAME':
      return {
        ...state,
        memberName: action.payload,
      };
    case 'SET_ROLE':
      return {
        ...state,
        role: action.payload,
      };
    case 'SET_PASSWORD':
      return {
        ...state,
        password: action.payload,
      };
    case 'SET_PASSWORDCHECK':
      return {
        ...state,
        passwordCheck: action.payload,
      };
    case 'SET_NEW_PASSWORD':
      return {
        ...state,
        newPassword: action.payload,
      };
    case 'SET_NEW_PASSWORDCHECK':
      return {
        ...state,
        newPasswordCheck: action.payload,
      };
    case 'SET_PHONENUMBER':
      return {
        ...state,
        phoneNumber: action.payload,
      };
    case 'SET_EMAILVALNUMBER':
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
    case 'SET_IS_LOADING':
      return {
        ...state,
        IsLoading: action.payload,
      }
    default:
      return state;
  }
};

export default userReducer;
  