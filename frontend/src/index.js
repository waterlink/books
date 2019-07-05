// Set up your application entry point here...

import React from 'react';
import {render} from 'react-dom';
import {AppContainer} from 'react-hot-loader';
import {Root} from "./components/Root";
import configureStore, { history } from "./store/configureStore";
import {booksApi, BooksApiService} from "./services/BooksApiService";
import './styles/app.scss';
import './styles/book.scss';

const store = configureStore();

booksApi.booksApiService = new BooksApiService();

render(
  <AppContainer>
    <Root store={store} history={history}/>
  </AppContainer>,
  document.getElementById('app')
);

if (module.hot) {
  module.hot.accept('./components/Root', () => {
    const NewRoot = require('./components/Root').Root;
    render(
      <AppContainer>
        <NewRoot store={store} history={history}/>
      </AppContainer>,
      document.getElementById('app')
    );
  });
}
