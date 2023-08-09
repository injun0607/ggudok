import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './reducers/rootReducer';
import logger from 'redux-logger'

const store = configureStore({
  reducer: rootReducer,
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(logger),
})


export default store;