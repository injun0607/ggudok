const initialState = {
  isadminLayout: false,
};

const adminLayoutReducer = (state = initialState, action) => {
  switch (action.type) {
    case 'SET_ADMIN_LAYOUT':
      return { ...state, isadminLayout: action.payload };
    default:
      return state;
  }
};

export default adminLayoutReducer;