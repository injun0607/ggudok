const initialState = {
  categories: [],
  featuredcategories: [
    { categoryEng: 'best', category: '베스트',},
  ],
  othercategories:[
    { categoryEng: 'compare', category: '간편비교',},
    { categoryEng: 'event', category: '이벤트',},
    { categoryEng: 'contactus', category: 'Contact Us',},
  ],
  dropCategory: false,
};


const categoryReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_CATEGORY':
      return {
        ...state,
        categories: action.payload,
      };
    case 'SHOW_CATEGORY':
      return {
        ...state,
        dropCategory: !state.dropCategory,
      };

    default:
      return state;
  }
};

export { categoryReducer };