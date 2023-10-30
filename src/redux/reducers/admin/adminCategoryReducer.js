const initialState = {
  categories: [],
  pagedCategories: [],
  categoryName: '',
  isCategoryNameval: false,
  categoryEng: '',
  isCategoryEngval: false,
  categoryImage: '',
};


const adminCategoryReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_CATEGORY_SUCCESS':
      return {
        ...state,
        categories: action.payload,
      };
    case 'PAGING_CATEGORY':
      return {
        ...state,
        pagedCategories: action.payload,
      }
    case 'CREATE_CATEGORY':
      return {
        ...state,
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
    case 'SET_VALID_CATEGORYENG':
      return {
        ...state,
        isCategoryEngval: action.payload,
      };
    case 'SET_CATEGORYENG':
      return {
        ...state,
        categoryEng: action.payload,
      };
    case 'SET_CATEGORYIMAGE':
      return {
        ...state,
        categoryImage: action.payload,
      };
    case 'EDIT_CATEGORY_SUCCESS':
      return {
        ...state,
      };
    case 'EDIT_CATEGORY_FAILURE':
      return {
        ...state,
      };
    case 'DELETE_CATEGORY_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_CATEGORY_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default adminCategoryReducer;
