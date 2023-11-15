const initialState = {
  selectedOneDepth: '',
  twoDepthItems: [],
  compareItem1: {},
  compareItem2: {},
};

const compareReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_SELECTED_ONE_DEPTH':
      return {
        ...state,
        selectedOneDepth: action.payload,
      };
    case 'SET_TWO_DEPTH_ITEMS':
      return {
        ...state,
        twoDepthItems: action.payload,
      };
    case 'SET_COMPARE_ITEM1':
      return {
        ...state,
        compareItem1: action.payload,
        
      };
    case 'SET_COMPARE_ITEM2':
      return {
        ...state,
        compareItem2: action.payload,
      };
    default:
      return state;
  }
};


export default compareReducer;
