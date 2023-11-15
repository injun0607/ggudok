const initialState = {
  items: [],
  selectedCategory: '',
  filteredItems: [],
  pagedItems: [],
};


const adminItemsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_ITEM_SUCCESS':
      return {
        ...state,
        items: action.payload,
      };
    case 'SELECTED_CATEGORY':
      return {
        ...state,
        selectedCategory: action.payload,
      }
    case 'FILTER_ITEM':
      return {
        ...state,
        filteredItems: action.payload,
      }
    case 'PAGING_ITEM':
      return {
        ...state,
        pagedItems: action.payload,
      }
    case 'CREATE_ITEM':
      return {
        ...state,
      };
    case 'EDIT_ITEM_SUCCESS':
      return {
        ...state,
      };
    case 'EDIT_ITEM_FAILURE':
      return {
        ...state,
      };
    case 'DELETE_ITEM_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_ITEM_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default adminItemsReducer;
