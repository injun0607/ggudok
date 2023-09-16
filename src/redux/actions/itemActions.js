export const fetchItemsSuccess = (data) => {
  return {
    type: 'FETCH_ITMES_SUCCESS',
    payload: data,
  };
};

export const setLikedItem = (likeditem) => {
  return {
    type: 'SET_LIKED_ITEM',
    payload: likeditem,
  };
};
export const setNoResult = (isresult) => {
  return {
    type: 'SET_NORESULT',
    payload: isresult,
  };
};