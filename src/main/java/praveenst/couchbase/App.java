package praveenst.couchbase;

import com.couchbase.client.java.Bucket;

import java.util.Scanner;

public class App {

    public static void main(String... args) throws Exception {

        Scanner myObj = new Scanner(System.in);
        CouchDataWriter writer = new CouchDataWriter();
        System.out.println("Enter # of Documents: ");

        // Numerical input
        int numOfRecords = myObj.nextInt();

        System.out.println("Enter couchbase username: ");
        String username = myObj.next();
        System.out.println("Enter couchbase password: ");
        String password = myObj.next();

        Bucket bucket = writer.initCouchConnection(username,password);

        // Output input by user
        System.out.println("Creating Documents in couchbase 'Default' bucket with " + numOfRecords + "of records....");

        if(numOfRecords < 10) {
            writer.createJsonObjects(numOfRecords, bucket);
        } else {
            writer.createJsonObjectsInBulk(numOfRecords, bucket);
        }

    }
}
