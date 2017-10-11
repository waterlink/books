import {config} from '../config';

export const booksApi = {
  booksApiService: null,
};

export class BooksApiService {

  loadBooks() {
    return fetch(config.apiGateway + "/v1/books")
      .then(responseBody => responseBody.json())
      .then(response => response["books"]);
  }

}

export class MockBooksApiService {

  constructor(bookList) {
    this.bookList = bookList;
  }

  loadBooks() {

    const fakePromise = {
      then: (fn) => {
        fn(this.bookList);
        return fakePromise;
      },

      catch: () => {
        return fakePromise;
      },
    };

    return fakePromise;
  }

}
