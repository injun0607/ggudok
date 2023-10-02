const initialState = {
  reviews: [],
  myItemReviewRating: 0,
  myItemReviewContents: '',
  myReviews: [],
  reviewModal: false,
};

const reviewReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_REVIEW':
      return {
        ...state,
        reviews: action.payload,
      };
    // case 'GET_MYITEMREVIEW_SUCCESS':
    //   return {
    //     ...state,
    //     myItemReviewRating: action.payload,
    //     myItemReviewContents: action.payload,
    //   };
    case 'SET_MYITEMREVIEW_RATING':
      return {
        ...state,
        myItemReviewRating: action.payload,
      };
    case 'SET_MYITEMREVIEW_CONTENTS':
      return {
        ...state,
        myItemReviewContents: action.payload,
      };
    case 'SET_MYITEMREVIEW_SUCCESS':
      return {
        ...state,
        myItemReviewRating: action.payload.rating,
        myItemReviewContents: action.payload.contents,
      };
    case 'SET_MYITEMREVIEW_FAILURE':
      return {
        ...state,
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
