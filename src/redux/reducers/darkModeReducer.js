const savedDarkMode = JSON.parse(localStorage.getItem('darkmode'))

const initialState = {
  darkMode: savedDarkMode === null ? false : savedDarkMode,
  getCurrentMode: savedDarkMode === null ? false : savedDarkMode,
}


const darkModeReducer = (state = initialState, action) => { 
  switch (action.type) {
    case 'TOGGLE_DARK_MODE':
      const newDarkMode = !state.darkMode;
      localStorage.setItem('darkmode', JSON.stringify(newDarkMode));
      return {
        ...state,
        darkMode: newDarkMode,
        getCurrentMode: newDarkMode,
      };
    default:
      return state;
  }
}

export default darkModeReducer;