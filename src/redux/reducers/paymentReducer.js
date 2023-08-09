const initialState = {
    paymentStatus: null,
    // 기타 결제 정보
  };
  
  const paymentReducer = (state = initialState, action) => {
    switch (action.type) {
      case 'PAYMENT_SUCCESS':
        return {
          ...state,
          paymentStatus: 'success',
          // 기타 결제 정보 업데이트
        };
      case 'PAYMENT_CANCEL':
        return {
          ...state,
          paymentStatus: 'cancelled',
          // 기타 결제 정보 초기화
        };
      // 다른 액션들 처리
      default:
        return state;
    }
  };
  
  export default paymentReducer;
  