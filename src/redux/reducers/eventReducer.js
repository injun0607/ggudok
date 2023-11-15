const initialState = {
  events: []
}

const eventReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_EVENT':
      return {
        ...state,
        events: action.payload,
      }
    default:
      return state;
  }
}

export default eventReducer;