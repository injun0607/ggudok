const initialState = {
  items: [],
  filtereditems: [],
  itemDetail: {},
  similarItems: [],
  bestitems: [],
  likeditems: [],
  IsResult: null,
  IsLoading: true,
};

const itemReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_IS_LOADING':
      return {
        ...state,
        IsLoading: action.payload,
      }
    case 'FETCH_ITEMS_SUCCESS':
      return {
        ...state,
        items: action.payload,
      }
    case 'FETCH_ITEMDETAIL_SUCCESS':
      return {
        ...state,
        itemDetail: action.payload.itemDetail,
        similarItems: action.payload.similarItems,
      }
    case 'SLICE_ITEM':
      return {
        ...state,
        sliceditems: action.payload,
      }
    case 'FILTER_ITEM':
      return {
        ...state,
        filtereditems: action.payload,
      }
    case 'LIKE_ITEM_SUCCESS':
      return {
        ...state,
        likeditems: [...state.likeditems, action.payload],
      }
    case 'LIKE_ITEM_FAILURE':
      return {
        ...state,
      };
    case 'SET_ISRESULT':
      return {
        ...state,
        IsResult: action.payload,
      };
    default:
      return state;
  }
};

export default itemReducer;
