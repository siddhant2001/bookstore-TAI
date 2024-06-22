## Versions
- JDK version: 21
- Gradle version: 8.5
- Protobuf version: 3
- Latest versions for all the dependencies (as of 21 June 2024)

## Source code file paths
- bookstore.proto file path: `src/main/proto/bookstore.proto`
- Bookstore Service Implementation Path: `src/main/java/bookstore/BookServiceImpl` 
- Server class: `src/main/java/bookstore/BookStoreServer`
- Client class: `src/main/java/bookstore/BookStoreClient`

## Build Instructions:
1. `gradlew generateProto` to generate prototypes for the bookstore service and the message classes defined in `bookstore.proto`
2. `gradlew build` to build the source files.
3. Running the server
   - Right-click on the BookStoreServer file and select 'Run BookStoreServer.main()'
   - Or, `gradlew run` from the root directory.
4. Running the client
   - Right-click on the BookStoreClient file and select 'Run BookStoreClient.main()'
   - Or, `gradlew runClient` from the root directory 
5. `./gradlew test` to run tests


## RPC Descriptions and Expected Behavior

### 1. AddBook

- **Request:** `AddBookRequest`
   - Contains a `Book` message with details of the book to be added.
- **Response:** `AddBookResponse`
   - Contains a message indicating the success of the operation.

**Expected Behavior:**
- Adds the provided book to the in-memory store.
- Returns a message confirming the book was added.

### 2. UpdateBook

- **Request:** `UpdateBookRequest`
   - Contains a `Book` message with updated details of the book.
- **Response:** `UpdateBookResponse`
   - Contains a message indicating the success or failure of the operation.

**Expected Behavior:**
- Updates the details of the specified book in the in-memory store if it exists.
- Returns a message confirming the book was updated or indicating that the book was not found.

### 3. DeleteBook

- **Request:** `DeleteBookRequest`
   - Contains the `isbn` of the book to be deleted.
- **Response:** `DeleteBookResponse`
   - Contains a message indicating the success or failure of the operation.

**Expected Behavior:**
- Deletes the specified book from the in-memory store if it exists.
- Returns a message confirming the book was deleted or indicating that the book was not found.

### 4. GetBooks

- **Request:** `GetBooksRequest`
   - No additional parameters required.
- **Response:** `GetBooksResponse`
   - Contains a list of `Book` messages representing all books in the store.

**Expected Behavior:**
- Retrieves all books from the in-memory store.
- Returns a list of all books currently stored.

## Unit tests

The tests are simple and don't use `GrpcCleanup`. Instead, `@BeforeEach` and `@AfterEach` annotations are used to make and teardown the server before and after each test.