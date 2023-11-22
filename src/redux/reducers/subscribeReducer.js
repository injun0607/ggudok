const initialState = {
  mySubscribe: [],
  mySubsRank: [],
  mySubsOtherRank: [],
  myTotalAvg: '',
};
  
const subscribeReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_MY_SUBS':
      return {
        ...state,
        mySubscribe: action.payload,
      }
    case 'SET_MY_SUBS_RANK':
      return {
        ...state,
        mySubsRank: action.payload,
      }
    case 'SET_MY_SUBS_OTHER_RANK':
      return {
        ...state,
        mySubsOtherRank: action.payload,
      }
    case 'SET_MY_TOTAL':
      return {
        ...state,
        myTotalAvg: action.payload,
      }
    // **************************** 구독하기 ****************************
    case 'ADD_SUBS_SUCCESS':
      return {
        ...state,
      };
    case 'ADD_SUBS_FAILURE':
      return {
        ...state,
      };
    // **************************** 구독 변경 ****************************
    case 'EDIT_SUBS_SUCCESS':
      return {
        ...state,
      };
    case 'EDIT_SUBS_FAILURE':
      return {
        ...state,
      };
    // **************************** 구독 해지 ****************************
    case 'DELETE_SUBS_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_SUBS_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default subscribeReducer;
  