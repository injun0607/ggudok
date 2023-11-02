const initialState = {
  tags: [],
  pagedTags: [],
  isTagNameval: false,
  tagName: '',
};


const adminTagsReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_TAG_SUCCESS':
      return {
        ...state,
        tags: action.payload,
      };
    case 'PAGING_TAG':
      return {
        ...state,
        pagedTags: action.payload,
      }
    case 'CREATE_TAG':
      return {
        ...state,
      };
    case 'SET_VALID_TAGNAME':
      return {
        ...state,
        isTagNameval: action.payload,
      };
    case 'SET_TAGNAME':
      return {
        ...state,
        tagName: action.payload,
      };
    case 'DELETE_TAG_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_TAG_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default adminTagsReducer;
