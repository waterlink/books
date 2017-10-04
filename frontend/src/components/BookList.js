import React, {Component} from 'react';
import PropTypes from 'prop-types'
import {actions} from "../actions/booksActions";
import {connect} from "react-redux";

class Book extends Component {

  render() {
    return <div>
      {this.props.title}
    </div>
  }

}

Book.propTypes = {
  title: PropTypes.string,
};

export class BookList extends Component {

  render() {

    if (!this.props.alreadyLoaded) {
      this.props.loadBooks();
    }

    return <div>
      {this.props.books.map(book =>
        <Book key={book.id} title={book.title}/>
      )}
    </div>

  }

}

const mapStateToProps = (state) => {
  return {
    books: state.books.booksList,
    alreadyLoaded: state.books.alreadyLoaded,
  }
};

const mapDispatchToProps = (dispatch) => {
  return {
    loadBooks: () => {
      dispatch(actions.loadBooks());
    }
  }
};

export const ConnectedBookList = connect(
  mapStateToProps,
  mapDispatchToProps,
)(BookList);
