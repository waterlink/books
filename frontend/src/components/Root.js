import React, {Component} from 'react';
import {Provider} from "react-redux";
import {ConnectedBookList} from "../containers/BookList";
import logo from '../logo.svg';
import '../styles/app.scss';
import {ConnectedRouter} from "react-router-redux";
import {Route, Switch} from "react-router-dom";
import {ConnectedBorrowBook} from "../containers/ConnectedBorrowBook";

export class Root extends Component {

  render() {
    return (
      <Provider store={this.props.store}>
        <div className="App">

          <div className="App-header">
              <img src={logo} className="App-logo" alt="logo"/>
              <h1>Books Application</h1>
          </div>
          <ConnectedRouter history={this.props.history}>
            <Switch>
              <Route exact path="/" component={ConnectedBookList} />
              <Route path="/borrow/:bookId" component={ConnectedBorrowBook}/>
            </Switch>
          </ConnectedRouter>
        </div>

      </Provider>
    )
  }

}
