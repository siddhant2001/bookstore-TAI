### Versions
- JDK version: 21
- Gradle version: 8.5
- Protobuf version: 3
- Latest versions for all the dependencies (as of 21 June 2024)

### Source code file paths
- bookstore.proto file path: `src/main/proto/bookstore.proto`
- Bookstore Service Implementation Path: `src/main/java/bookstore/BookServiceImpl` 
- Server class: `src/main/java/bookstore/BookStoreServer`
- Client class: `src/main/java/bookstore/BookStoreClient`

Build Instructions:
1. `gradlew generateProto` to generate prototypes for the bookstore service and the message classes defined in `bookstore.proto`
2. `gradlew build` to build the source files.
3. Running the server
   - Right-click on the BookStoreServer file and select 'Run BookStoreServer.main()'
   - Or, `gradlew run` from the root directory.
4. Running the client
   - Right-click on the BookStoreClient file and select 'Run BookStoreClient.main()'
   - Or, `gradlew runClient` from the root directory 