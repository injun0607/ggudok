import { combineReducers } from 'redux';
import userReducer from './userReducer';
import subscribeReducer from './subscribeReducer';
import paymentReducer from './paymentReducer';
import searchReducer from './searchReducer';
import { categoryReducer } from './categoryReducer';
import itemReducer from './itemReducer';
import filterReducer from './filterReducer';
import compareReducer from './compareReducer';
import reviewReducer from './reviewReducer';
import eventReducer from './eventReducer';
import darkModeReducer from './darkModeReducer';
import adminLayoutReducer from './adminLayoutReducer';
import adminCategoryReducer from './admin/adminCategoryReducer';

const rootReducer = combineReducers({
  user: userReducer,
  subscribe: subscribeReducer,
  payment: paymentReducer,
  search: searchReducer,
  category: categoryReducer,
  item: itemReducer,
  filter: filterReducer,
  compare: compareReducer,
  event: eventReducer,
  darkMode: darkModeReducer,
  review: reviewReducer,
  adminLayout: adminLayoutReducer,
  adminCategory: adminCategoryReducer,
});

export default rootReducer;
