import React, {Component} from 'react';
import {ConnectedBookList} from "./BookList";

export class Root extends Component {

  render() {

    return <div>
      <h1>Books Application</h1>

      <ConnectedBookList store={this.props.store}/>

    </div>

  }

}
