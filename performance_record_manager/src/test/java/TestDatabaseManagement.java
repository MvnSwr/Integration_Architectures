import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.code.DatabaseManagement;
import com.model.SalesMan;
import com.model.SocialPerformanceRecord;

import com.mongodb.client.*;

public class TestDatabaseManagement {
    private DatabaseManagement DbManagement;
    MongoCollection<Document> salesmenColl;
    MongoCollection<Document> performanceColl;
    private SalesMan salesMan;
    private SocialPerformanceRecord socialPerformanceRecord;
    
    @BeforeEach
    public void setup(){
        DbManagement = new DatabaseManagement();
        salesMan = new SalesMan("Peter", "Dieter", 11);
        socialPerformanceRecord = new SocialPerformanceRecord(1, 2024, 5, 5, 5, 5, 5, 5);
    }

    @AfterEach
    public void teardown(){
        DbManagement = null;
    }

    @BeforeAll
    public void constructor(){
        //[GIVEN]
        MongoClient mongoClient;
        MongoDatabase mongoDatabase;

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
    public void createSalesMan() {
        DbManagement.createSalesMan(salesMan);
        Document savedDoc = salesmenColl.find(eq("sid", 11)).first();

        assertNotNull(savedDoc, "Should not be null");
        assertEquals(11, savedDoc.getInteger("sid"));
        assertEquals("Peter", savedDoc.getString("firstname"));
        assertEquals("Dieter", savedDoc.getString("lastname"));
    }

    @Test
    public void addSocialPerformanceRecord() {
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        Document savedSoc = performanceColl.find(Filters.eq("pid", 1)).first();
        Document sacedSaM = salesmenColl.find(eq("sid", 11)).first();

        assertNotNull(savedSoc);
        assertNotNull(sacedSaM);
        assertEquals(savedSoc.getInteger("sid"), sacedSaM.getInteger("sid"));
        assertEquals(savedSoc, socialPerformanceRecord.toDocument());
        assertEquals(sacedSaM, salesMan.toDocument());
    }

    @Test
    public void readSalesMan() {
        DbManagement.createSalesMan(salesMan);
        SalesMan salesManClone = DbManagement.readSalesMan(11);

        assertEquals(salesManClone,salesMan);
    }

    @Test
    public void readAllSalesMen() {
        SalesMan salesMan1 = new SalesMan("Reise", "Kato", 13);
        List<SalesMan> salesManList = new ArrayList<SalesMan>();
        salesManList.add(salesMan);
        salesManList.add(salesMan1);
        DbManagement.createSalesMan(salesMan);
        List<SalesMan> salesManListClone = DbManagement.readAllSalesMen();

        for(int i = 0; i < salesManList.size(); i++) {
            assertEquals(salesManList.get(i), salesManListClone.get(i));
        }
    }

    @Test
    public void readSocialPerformanceRecord() {
        SocialPerformanceRecord socialPerformanceRecord1 = new SocialPerformanceRecord(2, 2024, 4, 6, 5, 3, 4, 6);
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord1, salesMan);
        List<SocialPerformanceRecord> socialPerformanceRecordList = new ArrayList<>();
        socialPerformanceRecordList.add(socialPerformanceRecord1);
        socialPerformanceRecordList.add(socialPerformanceRecord);
        List<SocialPerformanceRecord> socialPerformanceRecordListClone =
                DbManagement.readSocialPerformanceRecord(salesMan);

        for(int i = 0; i < socialPerformanceRecordList.size(); i++) {
            assertEquals(socialPerformanceRecordList.get(i), socialPerformanceRecordListClone.get(i));
        }
    }

    @Test
    public void deleteSalesMan() {
        DbManagement.createSalesMan(salesMan);
        DbManagement.deleteSalesMan(salesMan);

        assertNull(salesmenColl.find(eq("sid", salesMan.getSid())).first());
    }

    @Test
    public void deleteSocialPerformanceRecord() {
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        DbManagement.deleteSocialPerformanceRecord(socialPerformanceRecord);

        assertNull(performanceColl.find(eq("pid", 1)).first());
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