import React, {Component} from 'react';
import {ConnectedBookList} from "./BookList";
import {Provider} from "react-redux";

export class Root extends Component {

  render() {

    return <div>
      <h1>Books Application</h1>

      <Provider store={this.props.store}>
        <ConnectedBookList/>
      </Provider>

    </div>

  }

}
