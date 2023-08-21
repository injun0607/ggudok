const initialState = {
  price: [],
  rating: [],
  tag: [],
};

const filterReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_SELECTED_PRICE':
      return {
        ...state,
        price: [...state.price, action.payload]
      };
    case 'SET_SELECTED_RATING':
      return {
        ...state,
        price: [...state.rating, action.payload]
      };
    case 'SET_SELECTED_TAG':
      return {
        ...state,
        price: [...state.tag, action.payload]
      };
    default:
      return state;
  }
}

export default filterReducer;