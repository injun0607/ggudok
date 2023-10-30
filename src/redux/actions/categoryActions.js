export const fetchCategory = (categories) => {
  return {
    type: 'FETCH_CATEGORY',
    payload: categories,
  };
};

export const setDropCategory = (dropCategory) => {
  return {
    type: 'SHOW_CATEGORY',
    payload: {
      dropCategory,
    }
  };
};
