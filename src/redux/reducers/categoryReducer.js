const initialState = {
  categories: [
    { categoryEng: 'ott', category: '영상', icon: '/images/icons/ott.svg' },
    { categoryEng: 'music', category: '음악', icon: '/images/icons/music.svg' },
    { categoryEng: 'food', category: '식품', icon: '/images/icons/food.svg' },
    { categoryEng: 'health', category: '건강', icon: '/images/icons/health.svg' },
    { categoryEng: 'drink', category: '음료', icon: '/images/icons/drink.svg' },
    { categoryEng: 'alcohol', category: '주류', icon: '/images/icons/alcohol.svg' },
    { categoryEng: 'extra', category: '패션잡화', icon: '/images/icons/extra.svg' },
    { categoryEng: 'book', category: '책', icon: '/images/icons/book.svg' },
    { categoryEng: 'ride', category: '교통', icon: '/images/icons/ride.svg' },
    { categoryEng: 'plant', category: '식물', icon: '/images/icons/plant.svg' },
    { categoryEng: 'pet', category: '반려동물', icon: '/images/icons/love.svg' },
    // { category: '기타', icon: '/images/icons/setting.svg' },
  ],
  featuredcategories: [
    { categoryEng: 'best', category: '베스트',},
    // { categoryEng: 'new', category: '신상품',},
  ],
  othercategories:[
    { categoryEng: 'compare', category: '간편비교',},
    { categoryEng: 'event', category: '이벤트',},
    { categoryEng: 'contactus', category: 'Contact Us',},
  ],
  // usercategories: [
  //   { categoryEng: 'mypage', category: '회원 정보 수정',},
  //   { categoryEng: 'modify', category: '관심 서비스',},
  //   { categoryEng: 'myitem', category: '구독내역',},
  // ],
};


const categoryReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'ADD_MY_CATEGORY':
      const existingCategory = state.mycategories.find(
        (category) => category.category === action.payload.category
      );
      if (existingCategory) {
        return state;
      }
      return {
        ...state,
        mycategories: [...state.mycategories, action.payload],
      };
      
    case 'CANCEL_MY_CATEGORY':
      const updatedMycategories = state.mycategories.filter(
        (category) => category.category !== action.payload.category
      );
      return {
        ...state,
        mycategories: updatedMycategories,
      };

    default:
      return state;
  }
};

const categoryToEngReducer = (state = null, action) => {
  const { categories } = initialState;
  if (action.type === 'GET_CATEGORY_ENG') {
    const transformedCategory = categories.find((cat) => cat.category === action.payload.category)?.categoryEng;
    return transformedCategory;
  }
  return state;
};

export { categoryReducer, categoryToEngReducer };