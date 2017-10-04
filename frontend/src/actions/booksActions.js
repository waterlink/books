export const t = {

  LOAD_BOOKS: 'LOAD_BOOKS',
  LOAD_BOOKS_SUCCESS: 'LOAD_BOOKS_SUCCESS',
  LOAD_BOOKS_FAILURE: 'LOAD_BOOKS_FAILURE',

};

export const actions = {

  loadBooks: (payload) => {
    return (dispatch) => {
      fetch("http://localhost:9090/v1/books")
        .then(response => response.json())
        .then(response =>
          dispatch(actions.loadBooksSuccess(response)))
        .catch(() =>
          dispatch(actions.loadBooksFailure()))
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
