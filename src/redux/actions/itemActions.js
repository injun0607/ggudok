import axios from 'axios';

export const setIsLoading = (isLoading) => {
  return {
    type: 'SET_IS_LOADING',
    payload: isLoading,
  };
};

export const fetchItemsSuccess = (data) => {
  return {
    type: 'FETCH_ITEMS_SUCCESS',
    payload: data,
  };
};

export const fetchitemDetailSuccess = (data) => {
  return {
    type: 'FETCH_ITEMDETAIL_SUCCESS',
    payload: data,
  };
};

export const filterItem = (filtereditem) => {
  return {
    type: 'FILTER_ITEM',
    payload: filtereditem,
  };
};

// export const setIsLikedOn = (itemId) => async (dispatch) => {
//   const { subsId } = itemId;
//   try {
//     const response = await axios.post(`/subs/like/${subsId}`, { subsId });
//     if (response.status === 200) {
//       dispatch({type: 'LIKE_ITEM_SUCCESS' });
//     } else {
//       dispatch({ type: 'LIKE_ITEM_FAILURE' });
//       alert(`관심서비스 등록 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
//     }
//   } catch (error) {
//     console.log('Error like item:', error);
//     dispatch({ type: 'LIKE_ITEM_FAILURE' });
//     alert(`관심서비스 등록 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
//   }
// };

// export const setIsLikedOff = (itemId) => async (dispatch) => {
//   const { subsId } = itemId;
//   try {
//     const response = await axios.post(`/subs/dislike/${subsId}`, new URLSearchParams({ subsId }));
//     if (response.status === 200) {
//       dispatch({type: 'DISLIKE_ITEM_SUCCESS', payload: false,});
//     } else {
//       dispatch({ type: 'DISLIKE_ITEM_FAILURE' });
//       alert(`관심서비스 해제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
//     }
//   } catch (error) {
//     console.log('Error dislike item:', error);
//     dispatch({ type: 'DISLIKE_ITEM_FAILURE' });
//     alert(`관심서비스 해제 중 오류가 발생했습니다. 잠시 후 다시 시도해주시기 바랍니다.`)
//   }
// };

export const likeItemSuccess = () => {
  return {
    type: 'LIKE_ITEM_SUCCESS'
  };
};

export const likeItemFailure = () => {
  return {
    type: 'LIKE_ITEM_FAILURE'
  };
};

export const setIsResult = (isresult) => {
  return {
    type: 'SET_ISRESULT',
    payload: isresult,
  };
};