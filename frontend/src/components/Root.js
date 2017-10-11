import React, {Component} from 'react';
import {Provider} from "react-redux";
import {ConnectedBookList} from "../containers/BookList";
import logo from '../logo.svg';
import '../styles/app.scss';

export class Root extends Component {

  render() {
    return (
      <Provider store={this.props.store}>
        <div className="App">
          <div className="App-header">
            <img src={logo} className="App-logo" alt="logo"/>
            <h1>Books Application</h1>
          </div>

          <ConnectedBookList/>
        </div>
      </Provider>
    )
  }

}
