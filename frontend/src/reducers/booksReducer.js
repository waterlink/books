import {t} from "../actions/booksActions";

export const initialState = {
  booksList: [],
  alreadyLoaded: false,
};

export const booksReducer = (state = initialState, action) => {

  switch (action.type) {

    case t.LOAD_BOOKS_SUCCESS:
      return {
        ...state,
        alreadyLoaded: true,
        booksList: action.payload,
      };

    case t.LOAD_BOOKS_FAILURE:
      console.log("Failed to load books");
      return state;

    default:
      return state;
  }

};
