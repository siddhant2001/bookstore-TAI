package bookstore;

import io.grpc.ManagedChannel;
import io.grpc.Server;

import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import bookstore.Bookstore.Book;
import bookstore.Bookstore.AddBookRequest;
import bookstore.Bookstore.AddBookResponse;
import bookstore.Bookstore.UpdateBookRequest;
import bookstore.Bookstore.UpdateBookResponse;
import bookstore.Bookstore.DeleteBookRequest;
import bookstore.Bookstore.DeleteBookResponse;
import bookstore.Bookstore.GetBooksRequest;
import bookstore.Bookstore.GetBooksResponse;
import bookstore.BookServiceGrpc.BookServiceBlockingStub;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookServiceImplTest {

    private Server server;
    private ManagedChannel channel;
    private BookServiceBlockingStub blockingStub;

    @BeforeEach
    public void setUp() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        server = InProcessServerBuilder
                  .forName(serverName)
                  .directExecutor()
                  .addService(new BookServiceImpl())
                  .build()
                  .start();

        channel = InProcessChannelBuilder.forName(serverName).directExecutor().build();
        blockingStub = BookServiceGrpc.newBlockingStub(channel);
    }

    @AfterEach
    public void tearDown() throws Exception {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    public void testAddBook() throws Exception {
        Book book = Book.newBuilder()
                .setPageCount(123)
                .setTitle("First Title")
                .setIsbn("1")
                .addAuthors("John Doe")
                .build();

        AddBookRequest request = AddBookRequest.newBuilder().setBook(book).build();
        AddBookResponse response = blockingStub.addBook(request);

        assertEquals("Book Added: First Title", response.getMessage());
    }
}
