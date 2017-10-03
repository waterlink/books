import React, {Component} from 'react';
import {BookList} from "./BookList";

export class Root extends Component {

  render() {

    return <div>
      <h1>Books Application</h1>

      <BookList/>

    </div>

  }

}
