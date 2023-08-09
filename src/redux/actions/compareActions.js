export const setSelectedOneDepth = (selectedCategory) => {
  return {
    type: 'SET_SELECTED_ONE_DEPTH',
    payload: selectedCategory,
  };
};

export const setTwoDepthItems = (filteredItems) => {
  return {
    type: 'SET_TWO_DEPTH_ITEMS',
    payload: filteredItems,
  };
};

export const setCompareItem1 = (compareItem1) => {
  return {
    type: 'SET_COMPARE_ITEM1',
    payload: compareItem1,
  };
};

export const setCompareItem2 = (compareItem2) => {
  return {
    type: 'SET_COMPARE_ITEM2',
    payload: compareItem2,
  };
};