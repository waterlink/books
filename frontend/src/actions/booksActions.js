import {booksApi} from "../services/BooksApiService";

export const t = {

  LOAD_BOOKS: 'LOAD_BOOKS',
  LOAD_BOOKS_SUCCESS: 'LOAD_BOOKS_SUCCESS',
  LOAD_BOOKS_FAILURE: 'LOAD_BOOKS_FAILURE',
  BORROW_BOOK_SUCCESS: 'BORROW_BOOK_SUCCESS',
  BORROW_BOOK_FAILURE: 'BORROW_BOOK_FAILURE',

};

export const actions = {

  loadBooks: () => {
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

  borrow: (bookId) => {
    return (dispatch) => {
      booksApi.booksApiService.borrow(bookId)
        .then(response =>
          dispatch(actions.borrowBookSuccess(response)))
        .catch((e) =>
          dispatch(actions.borrowBookFailure(e)))
    }
  },

  borrowBookSuccess: (payload) => {
    return {
      type: t.BORROW_BOOK_SUCCESS,
      payload,
    }
  },

  borrowBookFailure: (payload) => {
    return {
      type: t.BORROW_BOOK_FAILURE,
      payload,
    }
  },

};
