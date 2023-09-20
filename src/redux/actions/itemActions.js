export const setIsLoading = (isLoading) => {
  return {
    type: 'SET_IS_LOADING',
    payload: isLoading,
  };
};

export const fetchItemsSuccess = (data) => {
  return {
    type: 'FETCH_ITMES_SUCCESS',
    payload: data,
  };
};

export const filterItem = (filtereditem) => {
  return {
    type: 'FILTER_ITEM',
    payload: filtereditem,
  };
};

export const setLikedItem = (likeditem) => {
  return {
    type: 'SET_LIKED_ITEM',
    payload: likeditem,
  };
};
export const setIsResult = (isresult) => {
  return {
    type: 'SET_ISRESULT',
    payload: isresult,
  };
};