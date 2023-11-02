const initialState = {
  events: [],
  pagedEvents: [],
  eventName: '',
  isEventNameval: false,
  eventEng: '',
  isEventEngval: false,
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
    case 'SET_VALID_EVENTNAME':
      return {
        ...state,
        isEventNameval: action.payload,
      };
    case 'SET_EVENTNAME':
      return {
        ...state,
        eventName: action.payload,
      };
    case 'SET_VALID_EVENTENG':
      return {
        ...state,
        isEventEngval: action.payload,
      };
    case 'SET_EVENTENG':
      return {
        ...state,
        eventEng: action.payload,
      };
    case 'SET_EVENTIMAGE':
      return {
        ...state,
        eventImage: action.payload,
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
