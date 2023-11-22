import { combineReducers } from 'redux';
import userReducer from './userReducer';
import subscribeReducer from './subscribeReducer';
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
import adminItemsReducer from './admin/adminItemsReducer';
import adminTagsReducer from './admin/adminTagsReducer';
import adminEventsReducer from './admin/adminEventsReducer';

const rootReducer = combineReducers({
  user: userReducer,
  subscribe: subscribeReducer,
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
  adminItems: adminItemsReducer,
  adminTags: adminTagsReducer,
  adminEvents: adminEventsReducer,
});

export default rootReducer;
