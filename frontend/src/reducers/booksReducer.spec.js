import {booksReducer, initialState} from "./booksReducer";
import {actions} from "../actions/booksActions";

describe('booksReducer(state, action)', () => {

  it('initially has no books and is not loaded yet', () => {
    const initialState = booksReducer(undefined, {});

    expect(initialState.booksList).toEqual([]);
    expect(initialState.alreadyLoaded).toEqual(false);
  });

  it('stores books list when load books success action is dispatched', () => {
    const state = initialState;
    const booksList = [
      {id: 'id1', title: 'title1'},
      {id: 'id2', title: 'title2'},
    ];
    const action = actions.loadBooksSuccess(booksList);

    const nextState = booksReducer(state, action);

    expect(nextState.booksList).toEqual(booksList);
  });

  it('marks it as already loaded when load books success action is dispatched', () => {
    const state = initialState;
    const action = actions.loadBooksSuccess([]);

    const nextState = booksReducer(state, action);

    expect(nextState.alreadyLoaded).toEqual(true);
  });

});
