import React from 'react';
import {shallow} from 'enzyme';
import {BookList, ConnectedBookList} from './BookList';
import thunk from 'redux-thunk';
import {createMockStore} from "../testutils";
import {actions} from "../actions/booksActions";
import {booksApi, MockBooksApiService} from "../services/BooksApiService";
import {push} from "react-router-redux";

describe("<BookList />", () => {

  const startBorrow = () => {};

  let loadedBooks;

  const loadBooks = () => {
    loadedBooks = true;
  };

  beforeEach(() => {
    loadedBooks = false;
  });

  it("loads books when they're not loaded yet", () => {
    shallow(<BookList alreadyLoaded={false}
                      loadBooks={loadBooks}
                      books={[]}/>);
    expect(loadedBooks).toEqual(true);
  });

  it("doesn't load books when they're already loaded", () => {
    shallow(<BookList alreadyLoaded={true}
                      loadBooks={loadBooks}
                      books={[]}/>);
    expect(loadedBooks).toEqual(false);
  });

  it("renders books", () => {
    const books = [
      {id: 'id1', title: 'title1', status: 'available'},
      {id: 'id2', title: 'title2', status: 'unavailable'},
    ];

    const wrapper = shallow(<BookList alreadyLoaded={true}
                      loadBooks={loadBooks}
                      books={books}
                      startBorrow={startBorrow}/>);

    const bookComponents = wrapper.find('Book');

    expect(bookComponents.length).toEqual(2);

    expect(bookComponents.at(0).key()).toEqual('id1');
    expect(bookComponents.at(0).props().title).toEqual('title1');
    expect(bookComponents.at(0).props().status).toEqual('available');
    expect(bookComponents.at(1).key()).toEqual('id2');
    expect(bookComponents.at(1).props().title).toEqual('title2');
    expect(bookComponents.at(1).props().status).toEqual('unavailable');
  });

});

const createStore = createMockStore([thunk]);

describe('<ConnectedBookList />', () => {

  it('loads books from store', () => {
    const bookList = [
      {id: 'an id', title: 'a title'}
    ];

    booksApi.booksApiService = new MockBooksApiService(bookList);

    const store = createStore({
      books: {
        alreadyLoaded: false,
        booksList: [],
      }
    });

    shallow(<ConnectedBookList store={store} />).dive();

    const dispatchedActions = store.getActions();

    expect(dispatchedActions).toEqual([
      actions.loadBooksSuccess(bookList),
    ]);

  });

  it('navigates to borrow screen when clicking on borrow button for a book', () => {
    const bookId = 'some-book-id';

    const store = createStore({
      books: {
        alreadyLoaded: true,
        booksList: [{id: bookId, title: 'a title', status: 'available'}],
      }
    });

    const wrapper = shallow(<ConnectedBookList store={store} />).dive();

    const bookWrapper = wrapper.find("Book").dive();

    bookWrapper.find(".book-borrow-button").simulate("click");

    const dispatchedActions = store.getActions();

    expect(dispatchedActions).toEqual([
      push(`/borrow/${bookId}`),
    ])
  });

});
