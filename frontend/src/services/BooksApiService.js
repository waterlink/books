export const booksApi = {
  booksApiService: null,
};

export class BooksApiService {

  loadBooks() {
    return fetch("http://localhost:9090/v1/books")
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
        fn({books: this.bookList});
        return fakePromise;
      },

      catch: (fn) => {
        return fakePromise;
      },
    };

    return fakePromise;
  }

}
