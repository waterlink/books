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

  describe('.borrow(book)', () => {

    it('borrows the selected book', () => {

      const book = {id: '101', title: 'Ivanhoe', status: 'available'};
      const member = {id: '12122', name: 'Jeroen'};

      fetchMock.post((url, options) => {
        expect(url).toEqual(config.apiGateway + '/v1/books/' + book.id + '/borrow');
        expect(options.body.get("memberId")).toEqual(member.id);
        return true;
      }, {
        "body": {
          id: book.id,
          title: book.title,
          status: 'unavailable',
          borrowedById: member.id,
        }
      });

      const booksApiService = new BooksApiService();

      return booksApiService.borrow(book.id, member.id).then((actualBook) => {
        expect(actualBook.id).toEqual(book.id);
        expect(actualBook.status).toEqual('unavailable');
      });

    });

  });
});
