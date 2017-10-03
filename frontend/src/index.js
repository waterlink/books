// Set up your application entry point here...

import React, {Component} from 'react';
import {render} from 'react-dom';
import {AppContainer} from 'react-hot-loader';
import {Root} from "./components/Root";

render(
  <AppContainer>
    <Root/>
  </AppContainer>,
  document.getElementById('app')
);

if (module.hot) {
  module.hot.accept('./components/Root', () => {
    const NewRoot = require('./components/Root').Root;
    render(
      <AppContainer>
        <NewRoot />
      </AppContainer>,
      document.getElementById('app')
    );
  });
}
