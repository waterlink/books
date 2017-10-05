import React, {Component} from 'react';
import {actions} from "../actions/booksActions";
import {connect} from "react-redux";
import {Book} from "../components/Book";

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
