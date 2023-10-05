const initialState = {
  
};
  
const subscribeReducer = (state = initialState, action) => {
  switch (action.type) {
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
  