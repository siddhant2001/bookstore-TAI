package bookstore;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.List;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import bookstore.Bookstore.Book;
import bookstore.Bookstore.AddBookRequest;
import bookstore.Bookstore.AddBookResponse;
import bookstore.Bookstore.GetBooksRequest;
import bookstore.Bookstore.GetBooksResponse;
import bookstore.Bookstore.UpdateBookRequest;
import bookstore.Bookstore.UpdateBookResponse;
import bookstore.Bookstore.DeleteBookRequest;
import bookstore.Bookstore.DeleteBookResponse;

public class BookStoreClient {

    private final ManagedChannel channel;
    private final BookServiceGrpc.BookServiceBlockingStub blockingStub;

    public BookStoreClient(String host, int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();

        this.blockingStub = BookServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void addBook(String isbn, String title, List<String> author, int pages) {
        Book.Builder bookBuilder = Book.newBuilder()
                .setIsbn(isbn)
                .setTitle(title)
                .setPageCount(pages);

        for (String authorName : author) {
            bookBuilder.addAuthors(authorName);
        }

        Book book = bookBuilder.build();

        AddBookRequest request = AddBookRequest.newBuilder().setBook(book).build();
        AddBookResponse response = blockingStub.addBook(request);
        System.out.println("AddBook Response: " + response);
    }

    public void getBooks(int count) {
        GetBooksRequest request = GetBooksRequest.newBuilder().setCount(count).build();
        GetBooksResponse response = blockingStub.getBooks(request);
        for (Book book : response.getBooksList()) {
            System.out.println(book.getIsbn() + " " + book.getTitle());
        }
    }

    public void updateBook(String isbn, String title, List<String> author, int pages) {
        UpdateBookRequest request = UpdateBookRequest.newBuilder()
                .setBook(Book.newBuilder()
                .setIsbn(isbn)
                .setTitle(title)
                .setPageCount(pages)
                .addAllAuthors(author)
                .build())
                .build();

        UpdateBookResponse response = blockingStub.updateBook(request);

        System.out.println("UpdateBook Response: " + response);
    }

    public void deleteBook(String isbn) {
        DeleteBookRequest request = DeleteBookRequest.newBuilder().setIsbn(isbn).build();

        DeleteBookResponse response = blockingStub.deleteBook(request);

        System.out.println("DeleteBook Response: " + response);
    }

    public static void main(String[] args) throws InterruptedException {
        BookStoreClient client = new BookStoreClient("localhost", 50051);

        client.addBook("111", "aaaa", Arrays.asList("John", "Jane"), 200);
        client.addBook("222", "bbbb", List.of("testing"), 400);

        client.getBooks(1);

        client.updateBook("111", "aaab", Arrays.asList("John", "Jane"), 200);

        client.deleteBook("222");

        client.getBooks(1);

        client.shutdown();
    }
}
