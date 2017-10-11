import React, {Component} from 'react';
import {actions} from "../actions/booksActions";
import {connect} from "react-redux";
import {Book} from "../components/Book";
import {push} from "react-router-redux";

export class BookList extends Component {

  render() {

    if (!this.props.alreadyLoaded) {
      this.props.loadBooks();
    }

    return(<div>
      {this.props.books.map(book =>
        <Book key={book.id} {...book} startBorrow={this.props.startBorrow}/>
      )}
    </div>);

  }

}

const mapStateToProps = (state) => {
  return {
    books: state.books.booksList,
    alreadyLoaded: state.books.alreadyLoaded,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    loadBooks: () => {
      dispatch(actions.loadBooks());
    },
    startBorrow: (id) => {
      dispatch(push(`/borrow/${id}`));
    },
  };
};

export const ConnectedBookList = connect(
  mapStateToProps,
  mapDispatchToProps,
)(BookList);
