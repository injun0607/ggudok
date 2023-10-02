import axios from 'axios';

export const setReview = (review) => {
  return {
    type: 'SET_REVIEW',
    payload: review,
  };
};
// export const getMyItemReview = (myItemReview) => {
//   return {
//     type: 'SET_REVIEWMODAL',
//     payload: myItemReview,
//   };
// };
export const setMyItemReviewRating = (myItemReviewRating) => {
  return {
    type: 'SET_MYITEMREVIEW_RATING',
    payload: myItemReviewRating,
  };
};
export const setMyItemReviewContents = (myItemReviewContents) => {
  return {
    type: 'SET_MYITEMREVIEW_CONTENTS',
    payload: myItemReviewContents,
  };
};
export const setMyItemReview = (reviewData) => async (dispatch) => {
  const { subsId, rating, contents } = reviewData;

  try {
    const response = await axios.post(`/subs/write_review`, {
      subsId: subsId,
      rating: rating,
      contents: contents,
    });
    if (response.status === 200) {
      dispatch({type: 'SET_MYITEMREVIEW_SUCCESS', payload: reviewData,});
      alert(`리뷰작성이 완료되었습니다.`);
      dispatch(setReviewModal());
      window.location.reload();
    } else {
      dispatch({ type: 'SET_MYITEMREVIEW_FAILURE' });
      alert(`리뷰 작성 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error like item:', error);
    dispatch({ type: 'SET_MYITEMREVIEW_SUCCESS' });
    alert(`리뷰 작성 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};
export const setReviewModal = (modal) => {
  return {
    type: 'SET_REVIEWMODAL',
    payload: modal,
  };
};
