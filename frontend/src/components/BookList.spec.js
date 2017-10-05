import React from 'react';
import {shallow} from 'enzyme';
import {BookList} from './BookList';

describe("<BookList />", () => {

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
      {id: 'id1', title: 'title1'},
      {id: 'id2', title: 'title2'},
    ];

    const wrapper = shallow(<BookList alreadyLoaded={true}
                      loadBooks={loadBooks}
                      books={books}/>);

    const bookComponents = wrapper.find('Book');

    expect(bookComponents.length).toEqual(2);

    expect(bookComponents.at(0).key()).toEqual('id1');
    expect(bookComponents.at(0).props().title).toEqual('title1');
    expect(bookComponents.at(1).key()).toEqual('id2');
    expect(bookComponents.at(1).props().title).toEqual('title2');

  });

});
