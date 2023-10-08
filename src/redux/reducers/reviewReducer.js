const initialState = {
  reviews: [],
  pagedReviews: [],
  myItemReviewRating: 0,
  myItemReviewContents: '',
  myReviews: [],
  pagedMyReviews: [],
  editReviewRating: 0,
  editReviewContents: '',
  reviewModal: false,
};

const reviewReducer = (state = initialState, action) => {
  switch (action.type) {
    // ******************* 아이템페이지 리뷰 *********************
    case 'SET_REVIEW':
      return {
        ...state,
        reviews: action.payload,
      };
    case 'PAGING_REVIEW':
      return {
        ...state,
        pagedReviews: action.payload,
      }
    // 아이템페이지 리뷰 수정
    case 'SET_MYITEMREVIEW_CONTENTS':
      return {
        ...state,
        myItemReviewContents: action.payload,
      };
    case 'SET_MYITEMREVIEW_RATING':
      return {
        ...state,
        myItemReviewRating: action.payload,
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
    // 리뷰 모달 제어
    case 'SET_REVIEWMODAL':
      return {
        ...state,
        reviewModal: !state.reviewModal,
      };
    // ******************* 마이페이지 리뷰 *********************
    case 'SET_MYREVIEW':
      return {
        ...state,
        myReviews: action.payload,
      };
    case 'PAGING_MYREVIEW':
      return {
        ...state,
        pagedMyReviews: action.payload,
      }
    case 'EDIT_MYREVIEW_RATING':
      return {
        ...state,
        editReviewRating: action.payload,
      };
    case 'EDIT_MYREVIEW_CONTENTS':
      return {
        ...state,
        editReviewContents: action.payload,
      };
    case 'EDIT_MYREVIEW_SUCCESS':
      return {
        ...state,
      };
    case 'EDIT_MYREVIEW_FAILURE':
      return {
        ...state,
      };
    case 'DELETE_MYREVIEW_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_MYREVIEW_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};


export default reviewReducer;
