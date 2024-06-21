package bookstore;

import io.grpc.stub.StreamObserver;
import java.util.HashMap;
import java.util.Map;

import bookstore.Bookstore.Book;
import bookstore.Bookstore.AddBookRequest;
import bookstore.Bookstore.AddBookResponse;
import bookstore.Bookstore.UpdateBookRequest;
import bookstore.Bookstore.UpdateBookResponse;
import bookstore.Bookstore.DeleteBookRequest;
import bookstore.Bookstore.DeleteBookResponse;
import bookstore.Bookstore.GetBooksRequest;
import bookstore.Bookstore.GetBooksResponse;
import bookstore.BookServiceGrpc.BookServiceImplBase;



public class BookServiceImpl extends BookServiceImplBase {
    private final Map<String, Book> books = new HashMap<>();

    @Override
    public void addBook(AddBookRequest request, StreamObserver<AddBookResponse> responseObserver) {
        Book book = request.getBook();
        books.put(book.getIsbn(), book);

        AddBookResponse response = AddBookResponse.newBuilder().setMessage("Book Added: " + book.getTitle()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateBook(UpdateBookRequest request, StreamObserver<UpdateBookResponse> responseObserver) {
        Book book = request.getBook();

        if(books.containsKey(book.getIsbn())) {
            books.put(book.getIsbn(), book);
            UpdateBookResponse response = UpdateBookResponse.newBuilder().setMessage("Book Updated: " + book.getTitle()).build();
            responseObserver.onNext(response);
        } else {
            UpdateBookResponse response = UpdateBookResponse.newBuilder().setMessage("Book Not Found: " + book.getIsbn()).build();
            responseObserver.onNext(response);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void deleteBook(DeleteBookRequest request, StreamObserver<DeleteBookResponse> responseObserver) {
        String isbn = request.getIsbn();
        if(books.containsKey(isbn)) {
            books.remove(isbn);
            DeleteBookResponse response = DeleteBookResponse.newBuilder().setMessage("Book Deleted: " + isbn).build();
            responseObserver.onNext(response);
        } else {
            DeleteBookResponse response = DeleteBookResponse.newBuilder().setMessage("Book Not Found: " + isbn).build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
    }

    @Override
    public void getBooks(GetBooksRequest request, StreamObserver<GetBooksResponse> responseObserver) {
//        Integer count = request.getCount();
        GetBooksResponse response = GetBooksResponse.newBuilder().addAllBooks(books.values()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
