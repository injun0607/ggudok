const initialState = {
  subscriptions: [],
};

const subscriptionReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'ADD_SUBSCRIPTION':
      return {
        ...state,
        subscriptions: [...state.subscriptions, action.payload.subscription],
      };
    case 'CANCEL_SUBSCRIPTION':
      return {
        ...state,
        subscriptions: state.subscriptions.filter(sub => sub.id !== action.payload.subscriptionId),
      };
    // 다른 액션들 처리
    default:
      return state;
  }
};

export default subscriptionReducer;
  