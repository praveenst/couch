package praveenst.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.json.JsonObject;
import com.github.javafaker.Faker;
import rx.Observable;
import rx.functions.Func1;

import java.time.LocalDateTime;
import java.util.*;

public class CouchDataWriter {

    public CouchDataWriter() {
        System.out.println("Creating Data Writer instance...");
    }

    /**
     * Creates a json documents iteratively to be used for smaller data sets
     * @param n number of documents
     */

    protected void createJsonObjects(int n, Bucket bucket) {
        Random rb = new Random();
        Faker faker = new Faker();

        for (int i = 0; i < n ; i++) {
            boolean isVegan = rb.nextBoolean();
            boolean isVeteran = rb.nextBoolean();
            JsonObject content = JsonObject.empty()
                    .put("firstName", faker.name().firstName())
                    .put("lastName", faker.name().lastName())
                    .put("phone", generatePhoneNumber())
                    .put("isVegan", isVegan)
                    .put("isArmyVeteran", isVeteran)
                    .put("age", generateAge())
                    .put("lastUpdated", LocalDateTime.now().toString());

            System.out.println("THE CONTENT IS: " + content);
            storeDocument(bucket, content);
        }
    }

    /**
     * Creates a json documents in batch mode to be used for larger data sets
     * @param numOfDocuments
     */

    protected void createJsonObjectsInBulk(int numOfDocuments, Bucket bucket) {

        Random rb = new Random();
        Faker faker = new Faker();

        // Generate a number of dummy JSON documents
        List<JsonDocument> documents = new ArrayList<>();
        String id = generateId();
        for (int i = 0; i < numOfDocuments; i++) {
            boolean isVegan = rb.nextBoolean();
            boolean isVeteran = rb.nextBoolean();
            JsonObject content = JsonObject.create()
                    .put("firstName", faker.name().firstName())
                    .put("lastName", faker.name().lastName())
                    .put("phone", generatePhoneNumber())
                    .put("isVegan", isVegan)
                    .put("isArmyVeteran", isVeteran)
                    .put("age", generateAge())
                    .put("lastUpdated", LocalDateTime.now().toString());
            documents.add(JsonDocument.create(id+i, content));
        }

        // Insert them in one batch, waiting until the last one is done.
        rx.Observable
                .from(documents)
                .flatMap(new Func1<JsonDocument, Observable<JsonDocument>>() {
                    @Override
                    public Observable<JsonDocument> call(final JsonDocument docToInsert) {
                        return bucket.async().insert(docToInsert);
                    }
                })
                .last()
                .toBlocking()
                .single();

    }

    protected Bucket initCouchConnection(String username, String password) {
        // Initialize the Connection
        Cluster cluster = CouchbaseCluster.create("localhost");
        cluster.authenticate(username, password);
        return cluster.openBucket("default");

    }

    private void storeDocument(Bucket bucket, JsonObject input) {
        // Store the Document
        String id = generateId();
        bucket.upsert(JsonDocument.create(id, input));

        // Load the Document and print it
        // Prints Content and Metadata of the stored Document
        System.out.println(bucket.get(""+id));
    }

    private String generateId() {
        return UUID.randomUUID().toString();

    }

    private int generateAge() {
        Random generator = new Random();
        return generator.nextInt(99);
    }

    private String generatePhoneNumber() {
        Random generator = new Random();
        //Area code number; avoid 0s and mid sequence <= 999 and last 4 is <=9999
        int set1 = generator.nextInt(888) + 111;
        return  "(" + set1  +  ")" +
                "-" + generator.nextInt(899)  +
                "-" + generator.nextInt(8999);
    }


}
