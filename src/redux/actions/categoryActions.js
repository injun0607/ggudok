export const setDropCategory = (dropCategory) => {
  return {
    type: 'SHOW_CATEGORY',
    payload: {
      dropCategory,
    }
  };
};
