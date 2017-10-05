import React from 'react';
import {shallow} from 'enzyme';
import {Book} from './Book';

describe("<Book />", () => {

  it("renders book with title", () => {
    const bookTitle = "Robin Hood";

    const wrapper = shallow(<Book title={bookTitle}/>);

    const titleText = wrapper.find('div').text();
    expect(titleText).toEqual(bookTitle);
  });

});
