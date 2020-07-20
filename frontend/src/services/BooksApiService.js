import {config} from '../config';

export const booksApi = {
  booksApiService: null,
};

export class BooksApiService {

  loadBooks() {
    return fetch(config.apiGateway + "/v1/books")
      .then(responseBody => responseBody.json())
      .then(response => response["books"])
  }

  borrow(bookId, memberId) {
    const formData = new FormData();
    formData.append("memberId", memberId);

    return fetch(config.apiGateway + "/v1/books/" + bookId + "/borrow",{
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8'
      },
      body: formData
    })
      .then(responseBody => responseBody.json())
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
