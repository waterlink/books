import React, {Component} from 'react';
import PropTypes from 'prop-types'

const books = [
  {id: '319f39e4-a820-11e7-abc4-cec278b6b50a', title: 'Ivanhoe'},
  {id: '319f3c50-a820-11e7-abc4-cec278b6b50a', title: 'Robin Hood'},
];

class Book extends Component {

  render() {
    return <div>
      {this.props.title}
    </div>
  }

}

Book.propTypes = {
  title: PropTypes.number,
};

export class BookList extends Component {

  render() {

    return <div>
      {books.map(book =>
        <Book key={book.id} title={book.title}/>
      )}
    </div>

  }

}
