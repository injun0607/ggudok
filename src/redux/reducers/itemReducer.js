const initialState = {
  items: [],
  filtereditems: [],
  pageditems: [],
  itemDetail: {},
  similarItems: [],
  bestitems: [],
  IsResult: null,
  IsLoading: true,

  // ******************** 로그인모달 *******************
  loginModal: false,
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
    case 'PAGING_ITEM':
      return {
        ...state,
        pageditems: action.payload,
      }
    case 'LIKE_ITEM_SUCCESS':
      return {
        ...state,
      }
    case 'LIKE_ITEM_FAILURE':
      return {
        ...state,
      };
    case 'DISLIKE_ITEM_SUCCESS':
      return {
        ...state,
      }
    case 'DISLIKE_ITEM_FAILURE':
      return {
        ...state,
      };
    case 'SET_ISRESULT':
      return {
        ...state,
        IsResult: action.payload,
      };
      
    // 로그인 모달 제어
    case 'SET_LOGINMODAL':
      return {
        ...state,
        loginModal: !state.loginModal,
      };

    default:
      return state;
  }
};

export default itemReducer;
