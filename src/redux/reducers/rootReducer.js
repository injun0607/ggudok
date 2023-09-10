import { combineReducers } from 'redux';
import userReducer from './userReducer';
import subscriptionReducer from './subscriptionReducer';
import paymentReducer from './paymentReducer';
import searchReducer from './searchReducer';
import {categoryReducer, categoryToEngReducer} from './categoryReducer';
import itemReducer from './itemReducer';
import filterReducer from './filterReducer';
import compareReducer from './compareReducer';
import reviewReducer from './reviewReducer';
import eventReducer from './eventReducer';
import darkModeReducer from './darkModeReducer';
import adminLayoutReducer from './adminLayoutReducer';

const rootReducer = combineReducers({
  user: userReducer,
  subscription: subscriptionReducer,
  payment: paymentReducer,
  search: searchReducer,
  category: categoryReducer,
  categorytoeng: categoryToEngReducer,
  item: itemReducer,
  filter: filterReducer,
  compare: compareReducer,
  event: eventReducer,
  darkMode: darkModeReducer,
  adminLayout: adminLayoutReducer,
  review: reviewReducer,
});

export default rootReducer;
