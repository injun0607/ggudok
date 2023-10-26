const initialState = {
  categories: [],
  isCategoryNameval: false,
  categoryName: '',
};


const adminCategoryReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'CREATE_CATEGORY':
      return {
        ...state,
        categories: action.payload,
      };
    case 'EDIT_CATEGORY':
      return {
        ...state,
        categories: action.payload,
      };
    case 'SET_VALID_CATEGORYNAME':
      return {
        ...state,
        isCategoryNameval: action.payload,
      };
    case 'SET_CATEGORYNAME':
      return {
        ...state,
        categoryName: action.payload,
      };

    default:
      return state;
  }
};

export { adminCategoryReducer };