import fetchMock from 'fetch-mock';
import {BooksApiService} from "./BooksApiService";
import {config} from "../config";

describe('BooksApiService', () => {

  describe('.loadBooks()', () => {

    it('loads books', () => {

      const bookList = [
        {id: 'id1', title: 'title1'},
        {id: 'id2', title: 'title2'},
      ];

      fetchMock.get(config.apiGateway + '/v1/books', {
        books: bookList,
      });

      const booksApiService = new BooksApiService();

      return booksApiService.loadBooks().then((actualBookList) => {
        expect(actualBookList).toEqual(bookList);
      });

    });

  });

});
