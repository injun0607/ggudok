export const setReview = (review) => {
  return {
    type: 'SET_REVIEW',
    payload: review,
  };
};
export const setReviewModal = (modal) => {
  return {
    type: 'SET_REVIEWMODAL',
    payload: modal,
  };
};
