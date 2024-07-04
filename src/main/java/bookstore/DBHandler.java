package bookstore;


import com.google.protobuf.util.JsonFormat;
import org.hypertrace.core.documentstore.*;
import org.hypertrace.core.documentstore.model.config.*;
import org.hypertrace.core.documentstore.query.Query;
import org.hypertrace.core.documentstore.query.SelectionSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHandler {

    Datastore datastore;
    Collection collection;
    JsonFormat.Printer printer = JsonFormat.printer().preservingProtoFieldNames();
    JsonFormat.Parser parser = JsonFormat.parser().ignoringUnknownFields();

    public DBHandler() {
        ConnectionConfig connectionConfig = ConnectionConfig.builder()
                .type(DatabaseType.MONGO)
                .addEndpoint(Endpoint.builder().port(28018).build())
                .aggregationPipelineMode(AggregatePipelineMode.DEFAULT_ALWAYS)
                .build();

        DatastoreConfig datastoreConfig = DatastoreConfig.builder()
                .type(DatabaseType.MONGO)
                .connectionConfig(connectionConfig)
                .build();

        datastore = DatastoreProvider.getDatastore(datastoreConfig);

        collection = datastore.getCollection("bookstore");
    }

    public boolean addBook(Bookstore.Book book) {

        HashMap<Key, Document> newDocument = new HashMap<>();

        JSONDocument doc;
        try{
            doc = new JSONDocument(printer.print(book));
//            SingleValueKey key = new SingleValueKey("0", book.getIsbn());
            newDocument.put(Key.from(book.getIsbn()), doc);
            collection.bulkUpsert(newDocument);
        }
        catch (IOException e){
            System.out.println("Error" + e);
        }


        return true;
    }

    public List<Bookstore.Book> getBooks() throws IOException{

        final List<Bookstore.Book> books = new ArrayList<>();

        try(final CloseableIterator<Document> it = collection.aggregate(Query.builder().build())) {
            while(it.hasNext()){
                final Document document = it.next();
                final Bookstore.Book.Builder builder = Bookstore.Book.newBuilder();
                parser.merge(document.toJson(), builder);
                books.add(builder.build());
            }
        }

        return books;
    }
}
