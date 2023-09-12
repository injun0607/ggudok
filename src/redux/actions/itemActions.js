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