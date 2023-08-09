export const paymentSuccess = (paymentInfo) => {
  // 결제 정보를 받아와서 결제 성공 액션 객체를 반환
  return {
    type: 'PAYMENT_SUCCESS',
    payload: {
      paymentInfo,
    }
  };
};

export const paymentCancel = (paymentId) => {
  // 결제 ID를 받아와서 결제 취소 액션 객체를 반환
  return {
    type: 'PAYMENT_CANCEL',
    payload: {
      paymentId,
    }
  };
};

// 다른 결제 관련 액션 생성자 함수들
