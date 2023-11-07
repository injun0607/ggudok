const initialState = {
  events: [],
  pagedEvents: [],
};


const adminEventReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'FETCH_EVENT_SUCCESS':
      return {
        ...state,
        events: action.payload,
      };
    case 'PAGING_EVENT':
      return {
        ...state,
        pagedEvents: action.payload,
      }
    case 'CREATE_EVENT':
      return {
        ...state,
      };
    case 'EDIT_EVENT_SUCCESS':
      return {
        ...state,
      };
    case 'EDIT_EVENT_FAILURE':
      return {
        ...state,
      };
    case 'DELETE_EVENT_SUCCESS':
      return {
        ...state,
      };
    case 'DELETE_EVENT_FAILURE':
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default adminEventReducer;
