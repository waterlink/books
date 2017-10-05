import {booksApi} from "../services/BooksApiService";

export const t = {

  LOAD_BOOKS: 'LOAD_BOOKS',
  LOAD_BOOKS_SUCCESS: 'LOAD_BOOKS_SUCCESS',
  LOAD_BOOKS_FAILURE: 'LOAD_BOOKS_FAILURE',

};

export const actions = {

  loadBooks: (payload) => {
    return (dispatch) => {
      booksApi.booksApiService.loadBooks()
        .then(response =>
          dispatch(actions.loadBooksSuccess(response)))
        .catch((e) =>
          dispatch(actions.loadBooksFailure(e)))
    }
  },

  loadBooksSuccess: (payload) => {
    return {
      type: t.LOAD_BOOKS_SUCCESS,
      payload,
    }
  },

  loadBooksFailure: (payload) => {
    return {
      type: t.LOAD_BOOKS_FAILURE,
      payload,
    }
  },

};
