import axios from 'axios';

// ******************* 아이템페이지 리뷰 *********************
export const setReview = (review) => {
  return {
    type: 'SET_REVIEW',
    payload: review,
  };
};
// 아이템페이지 리뷰 수정
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
    console.log('Error setting my itemreview:', error);
    dispatch({ type: 'SET_MYITEMREVIEW_FAILURE' });
    alert(`리뷰 작성 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};
// 리뷰 모달 제어
export const setReviewModal = (modal) => {
  return {
    type: 'SET_REVIEWMODAL',
    payload: modal,
  };
};

// ******************* 마이페이지 리뷰 *********************
export const setMyReview = (myReviews) => {
  return {
    type: 'SET_MYREVIEW',
    payload: myReviews,
  };
};
// 마이페이지 리뷰 수정
export const editMyReviewRating = (editReviewRating) => {
  return {
    type: 'EDIT_MYREVIEW_RATING',
    payload: editReviewRating,
  };
};
export const editMyReviewContents = (editReviewContents) => {
  return {
    type: 'EDIT_MYREVIEW_CONTENTS',
    payload: editReviewContents,
  };
};
export const editMyReview = (reviewData) => async (dispatch) => {
  const { subsId, rating, contents } = reviewData;

  try {
    const response = await axios.post(`/subs/write_review`, {
      subsId: subsId,
      rating: rating,
      contents: contents,
    });
    if (response.status === 200) {
      dispatch({type: 'EDIT_MYREVIEW_SUCCESS', payload: reviewData,});
      alert(`리뷰수정이 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'EDIT_MYREVIEW_FAILURE' });
      alert(`리뷰 수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error edit myReview :', error);
    dispatch({ type: 'EDIT_MYREVIEW_FAILURE' });
    alert(`리뷰 수정 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};
export const deleteMyReview = (itemId) => async (dispatch) => {
  const { subsId } = itemId;
  try {
    const response = await axios.post(`/member/reviews/delete/${subsId}`, { subsId });
    if (response.status === 200) {
      dispatch({type: 'DELETE_MYREVIEW_SUCCESS'});
      alert(`리뷰 삭제가 완료되었습니다.`);
      window.location.reload();
    } else {
      dispatch({ type: 'DELETE_MYREVIEW_FAILURE' });
      alert(`리뷰 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
    }
  } catch (error) {
    console.log('Error deleting review :', error);
    dispatch({ type: 'DELETE_MYREVIEW_FAILURE' });
    alert(`리뷰 삭제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
  }
};