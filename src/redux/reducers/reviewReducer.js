const initialState = {
  reviews: [],
  reviewModal: false,
};

const reviewReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_REVIEW':
      return {
        ...state,
        reviews: action.payload,
      };
    case 'SET_REVIEWMODAL':
      return {
        ...state,
        reviewModal: !state.reviewModal,
      };
    default:
      return state;
  }
};


export default reviewReducer;
