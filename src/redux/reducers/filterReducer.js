const initialState = {
  selectedPrice: null,
  selectedRating: null,
  selectedTag: [],

  hideMenu: { price: false, rating: false, tag: false, }
};

const filterReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_SELECTED_PRICE':
      return {
        ...state,
        selectedPrice: action.payload
      };
    case 'SET_SELECTED_RATING':
      return {
        ...state,
        selectedRating: action.payload
      };
    case 'SET_SELECTED_TAG':
      return {
        ...state,
        selectedTag: action.payload
      };
    case 'SET_HIDE_MENU':
      return {
        ...state,
        hideMenu: {
          ...state.hideMenu,
          [action.payload.section]: !state.hideMenu[action.payload.section],
        }
      };
    default:
      return state;
  }
}

export default filterReducer;