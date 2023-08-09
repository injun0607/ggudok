import { combineReducers } from 'redux';
import userReducer from './userReducer';
import subscriptionReducer from './subscriptionReducer';
import paymentReducer from './paymentReducer';
import searchReducer from './searchReducer';
import {categoryReducer, categoryToEngReducer} from './categoryReducer';
import itemReducer from './itemReducer';
import compareReducer from './compareReducer';
import eventReducer from './eventReducer';
import darkModeReducer from './darkModeReducer';

const rootReducer = combineReducers({
  user: userReducer,
  subscription: subscriptionReducer,
  payment: paymentReducer,
  search: searchReducer,
  category: categoryReducer,
  categorytoeng: categoryToEngReducer,
  item: itemReducer,
  compare: compareReducer,
  event: eventReducer,
  darkMode: darkModeReducer,
  
});

export default rootReducer;
