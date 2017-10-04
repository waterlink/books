// Set up your root reducer here...
import {combineReducers} from 'redux';
import {booksReducer} from "./booksReducer";

export default combineReducers({
  books: booksReducer,
});
