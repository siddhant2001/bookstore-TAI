package bookstore;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;


public class BookStoreServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .addService(new BookServiceImpl())
                .build()
                .start();

        System.out.println("Server started, listening on " + server.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down gRPC server");
            server.shutdown();
            System.out.println("Server shut down");
        }));

        server.awaitTermination();
    }
}
