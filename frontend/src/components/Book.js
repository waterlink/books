import React, {Component} from 'react';
import PropTypes from 'prop-types';

export class Book extends Component {

  render() {

    const onBorrowClick = () => this.props.startBorrow(this.props.id);

    return (
      <div className="book-container">
        <div className="book-title">{this.props.title}</div>
        <div className="book-status">{this.props.status}</div>
        <a href="#" className="book-borrow-button" onClick={onBorrowClick}>Borrow</a>
      </div>
    )
  }

}

Book.propTypes = {
  id: PropTypes.string.isRequired,
  title: PropTypes.string.isRequired,
  status: PropTypes.string.isRequired,
  startBorrow: PropTypes.func.isRequired,
};
