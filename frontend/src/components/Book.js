import React, {Component} from 'react';
import PropTypes from 'prop-types'

export class Book extends Component {

  render() {
    return <div>
      {this.props.title}
    </div>
  }

}

Book.propTypes = {
  title: PropTypes.string,
};
