import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.code.DatabaseManagement;
import com.model.SalesMan;
import com.model.SocialPerformanceRecord;

import com.mongodb.client.*;

public class TestDatabaseManagement {
    private DatabaseManagement DbManagement;
    
    @BeforeEach
    public void setup(){
        DbManagement = new DatabaseManagement();
    }

    @AfterEach
    public void teardown(){
        DbManagement = null;
    }

    @Test
    public void constructor(){
        //[GIVEN]
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;
        MongoCollection<Document> salesmenColl;
        MongoCollection<Document> performanceColl;

        mongoClient = MongoClients.create("mongodb://localhost:27017");
        
        mongoDatabase = mongoClient.getDatabase("performanceRecordDatabase");
        salesmenColl = mongoDatabase.getCollection("salesmen");
        performanceColl = mongoDatabase.getCollection("performance_records");
        
        //[THEN]
        assertEquals(mongoDatabase, DbManagement.getMongoDatabase());
        assertEquals(mongoClient, DbManagement.getMongoClient());
        assertEquals(salesmenColl, DbManagement.getSalesmenColl());
        assertEquals(performanceColl, DbManagement.getPerformanceColl());
    }

    @Test
    public void createSalesMan(SalesMan record) {
        // this.salesmenColl.insertOne(record.toDocument());
    }

    @Test
    public void addSocialPerformanceRecord(SocialPerformanceRecord record , SalesMan salesMan) {
        // record.setSid(salesMan);
        // this.salesmenColl.insertOne(salesMan.toDocument());
        // this.performanceColl.insertOne(record.toDocument());
    }

    @Test
    public void readSalesMan(int sid) {
        // return docToSalesman(this.salesmenColl.find(Filters.eq("sid", sid)).first());
    }

    @Test
    public void readAllSalesMen() {
        // List<SalesMan> salesmenList = new ArrayList<>();

        // try (MongoCursor<Document> mCursor = salesmenColl.find().iterator()) {
        //     while (mCursor.hasNext()) {
        //         Document doc = mCursor.next();
        //         SalesMan sm = docToSalesman(doc);
        //         salesmenList.add(sm);
        //     }
        // }

        // return salesmenList;
    }

    @Test
    public void readSocialPerformanceRecord(SalesMan salesMan) {
        // List<SocialPerformanceRecord> socialPerformanceRecordList = new ArrayList<>();

        // try (MongoCursor<Document> mCursor = salesmenColl.find().iterator()) {
        //     while (mCursor.hasNext()) {
        //         Document doc = mCursor.next();
        //         if (doc.getInteger("pid") == salesMan.getSid()) {
        //             SocialPerformanceRecord socialPerformanceRecord = docToSocialPerformanceRecord(doc);
        //             socialPerformanceRecordList.add(socialPerformanceRecord);
        //         }
        //     }
        // }

        // return socialPerformanceRecordList;
    }

    @Test
    public void deleteSalesMan(SalesMan salesMan) {
        // this.salesmenColl.deleteOne(Filters.eq("sid", salesMan.getSid()));
    }

    @Test
    public void deleteSocialPerformanceRecord(SocialPerformanceRecord socialPerformanceRecord) {
        // this.performanceColl.deleteOne(Filters.eq("pid", socialPerformanceRecord.getId()));
    }

    @Test
    public void docToSocPerfRec(){
        //[GIVEN]
        SocialPerformanceRecord actualRec;
        SocialPerformanceRecord expectedRec = new SocialPerformanceRecord(12, 2024, 5, 6, 7, 2, 3, 4);
        Document expectedDoc = expectedRec.toDocument();

        //[WHEN]
        actualRec = DbManagement.docToSocialPerformanceRecord(expectedDoc);

        //[THEN]
        assertEquals(expectedRec, actualRec);
    }
}