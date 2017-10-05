import React, {Component} from 'react';
import {Provider} from "react-redux";
import {ConnectedBookList} from "../containers/BookList";

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
