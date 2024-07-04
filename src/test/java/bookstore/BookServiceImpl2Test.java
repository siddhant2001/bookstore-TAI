package bookstore;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bookstore.Bookstore.Book;
import bookstore.Bookstore.AddBookRequest;
import bookstore.Bookstore.AddBookResponse;
import bookstore.Bookstore.UpdateBookRequest;
import bookstore.Bookstore.UpdateBookResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookServiceImpl2Test {

    private BookServiceImpl mock_service;
̦    StreamObserver<AddBookResponse> response_observer = mock(StreamObserver.class);


    @BeforeEach
    void setUp() {
        mock_service = new BookServiceImpl();
      }

    @AfterEach
    void tearDown() {
      }

    @Test
    void addBook() {
        Book book = Book.newBuilder()
                .setIsbn("123456a")
                .addAuthors("author")
                .setTitle("title")
                .setPageCount(200)
                .build();

        AddBookRequest addBookRequest = AddBookRequest.newBuilder().setBook(book).build();
      }

    @Test
    void updateBook() {
      }

    @Test
    void deleteBook() {
      }

    @Test
    void getBooks() {
      }
}