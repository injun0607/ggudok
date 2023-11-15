const initialState = {
  categories: [
    // { categoryEng: 'ott', category: '영상', icon: '/images/icons/ott.svg' },
    // { categoryEng: 'music', category: '음악', icon: '/images/icons/music.svg' },
    // { categoryEng: 'food', category: '식품', icon: '/images/icons/food.svg' },
    // { categoryEng: 'health', category: '건강', icon: '/images/icons/health.svg' },
    // { categoryEng: 'drink', category: '음료', icon: '/images/icons/drink.svg' },
    // { categoryEng: 'alcohol', category: '주류', icon: '/images/icons/alcohol.svg' },
    // { categoryEng: 'extra', category: '패션잡화', icon: '/images/icons/extra.svg' },
    // { categoryEng: 'book', category: '책', icon: '/images/icons/book.svg' },
    // { categoryEng: 'ride', category: '교통', icon: '/images/icons/ride.svg' },
    // { categoryEng: 'plant', category: '식물', icon: '/images/icons/plant.svg' },
    // { categoryEng: 'pet', category: '반려동물', icon: '/images/icons/love.svg' },
  ],
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