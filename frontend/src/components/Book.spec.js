import React from 'react';
import {shallow} from 'enzyme';
import {Book} from './Book';

describe("<Book />", () => {

  let startedBorrowWithId;
  const startBorrow = (id) => {
    startedBorrowWithId = id;
  };

  beforeEach(() => {
    startedBorrowWithId = 'was not called';
  });

  it("renders book with title", () => {
    const bookTitle = "Robin Hood";

    const wrapper = shallow(<Book id="id" status="status" title={bookTitle} startBorrow={startBorrow}/>);

    const titleText = wrapper.find('.book-title').text();
    expect(titleText).toEqual(bookTitle);
  });

  it("renders book status", () => {

    const wrapper = shallow(<Book id="id" status={"available"} title="title" startBorrow={startBorrow}/>);

    const status = wrapper.find('.book-status').text();
    expect(status).toEqual("available");
  });

  it("starts borrow process when borrow button clicked", () => {
    const bookId = 'someBookId';

    const wrapper = shallow(<Book id={bookId} status="status" title="title" startBorrow={startBorrow}/>);

    wrapper.find('.book-borrow-button').simulate("click");

    expect(startedBorrowWithId).toEqual(bookId);
  });

  it("starts borrow process when borrow button clicked", () => {
    const bookId = 'someBookId';

    shallow(<Book id={bookId} status="status" title="title" startBorrow={startBorrow}/>);

    expect(startedBorrowWithId).toEqual('was not called');
  });

});
