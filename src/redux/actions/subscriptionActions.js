export const addSubscription = (subscription) => {
    // 새로운 구독 정보를 받아와서 구독 추가 액션 객체를 반환
    return {
      type: 'ADD_SUBSCRIPTION',
      payload: {
        subscription,
      }
    };
  };
  
  export const cancelSubscription = (subscriptionId) => {
    // 구독 ID를 받아와서 구독 취소 액션 객체를 반환
    return {
      type: 'CANCEL_SUBSCRIPTION',
      payload: {
        subscriptionId,
      }
    };
  };
  
  // 다른 구독 관련 액션 생성자 함수들
  