import React, {Component} from 'react';
import {Book} from "../components/Book";
import {connect} from "react-redux";

export class BookToBorrow extends Component {

  render() {

    return (
      <div>
          <Book {...this.props.book}/>
      </div>
    )
  }

}

const mapStateToProps = (state, ownProps) => {

  console.log(ownProps);
  return {
    book: state.books.booksList.find(book => book.id === ownProps.match.params.bookId)
  }
};

const mapDispatchToProps = (dispatch) => {
  return {}
};

export const ConnectedBorrowBook = connect(
  mapStateToProps,
  mapDispatchToProps,
)(BookToBorrow);
