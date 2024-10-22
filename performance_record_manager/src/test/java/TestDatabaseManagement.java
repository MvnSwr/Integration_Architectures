import static com.mongodb.client.model.Filters.eq;
import static org.junit.jupiter.api.Assertions.*;

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
        salesMan = null;
        socialPerformanceRecord = null;
    }

    @Test
    public void createSalesMan() {
        DbManagement.createSalesMan(salesMan);
        Document savedDoc = DbManagement.getSalesmenColl().find(eq("sid", 11)).first();

        assertNotNull(savedDoc, "Should not be null");
        assertEquals(11, savedDoc.getInteger("sid"));
        assertEquals("Peter", savedDoc.getString("firstname"));
        assertEquals("Dieter", savedDoc.getString("lastname"));

        //deleting files
        DbManagement.getSalesmenColl().deleteOne(eq("sid", 11));
    }

    @Test
    public void addSocialPerformanceRecord() {
        //[WHEN]
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);

        //[THEN]
        Document savedSocPerfRec = DbManagement.getPerformanceColl().find(eq("pid", 1)).first();
        socialPerformanceRecord.setSid(salesMan);
        Document expectedSocPerfRec = socialPerformanceRecord.toDocument();

        assertNotNull(expectedSocPerfRec);
        assertNotNull(savedSocPerfRec);
        assertEquals(socialPerformanceRecord.getId(), savedSocPerfRec.getInteger("pid"));

        //deleting files
        DbManagement.getSalesmenColl().deleteOne(eq("sid", 11));
        DbManagement.getPerformanceColl().deleteOne(eq("pid", 1));
    }

    @Test
    public void readSalesMan() {
        //[GIVEN]
        DbManagement.createSalesMan(salesMan);

        //[WHEN]
        SalesMan actualSalesMan = DbManagement.readSalesMan(11);

        //[THEN]
        assertEquals(actualSalesMan.toString(), salesMan.toString());

        //deleting files
        DbManagement.getSalesmenColl().deleteOne(eq("sid", 11));
    }

    @Test
    public void readAllSalesMen() {
        //[GIVEN]
        SalesMan newSalesMan = new SalesMan("Reise", "Kato", 13);
        List<SalesMan> salesManList = new ArrayList<SalesMan>();
        salesManList.add(salesMan);
        salesManList.add(newSalesMan);
        DbManagement.createSalesMan(salesMan);
        DbManagement.createSalesMan(newSalesMan);

        //[WHEN]
        List<SalesMan> actualSalesManList = DbManagement.readAllSalesMen();

        //[THEN]
        for(int i = 0; i < actualSalesManList.size(); i++){
            assertEquals(salesManList.get(i).toString(), actualSalesManList.get(i).toString());
        }

        //deleting files
        DbManagement.getSalesmenColl().deleteOne(eq("sid", 11));
        DbManagement.getSalesmenColl().deleteOne(eq("sid", 13));
    }

    @Test
    public void readSocialPerformanceRecord() {
        //[GIVEN]
        SocialPerformanceRecord newSocialPerformanceRecord = new SocialPerformanceRecord(2, 2024, 4, 6, 5, 3, 4, 6);
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        DbManagement.addSocialPerformanceRecord(newSocialPerformanceRecord, salesMan);

        List<SocialPerformanceRecord> expectedSocialPerformanceRecordList = new ArrayList<>();
        expectedSocialPerformanceRecordList.add(socialPerformanceRecord);
        expectedSocialPerformanceRecordList.add(newSocialPerformanceRecord);

        //[WHEN]
        List<SocialPerformanceRecord> actualSocialPerformanceRecordList =
                DbManagement.readSocialPerformanceRecord(salesMan);

        //[THEN]
        for(int i = 0; i < actualSocialPerformanceRecordList.size(); i++) {
            assertEquals(expectedSocialPerformanceRecordList.get(i).toString(), actualSocialPerformanceRecordList.get(i).toString());
        }

        //deleting files
        DbManagement.getSalesmenColl().deleteMany(eq("sid", 11));
        DbManagement.getPerformanceColl().deleteOne(eq("pid", 1));
        DbManagement.getPerformanceColl().deleteOne(eq("pid", 2));
    }

    @Test
    public void deleteSalesMan() {
        //[GIVEN]
        DbManagement.createSalesMan(salesMan);

        //[WHEN]
        DbManagement.deleteSalesMan(salesMan);

        //[THEN]
        List<SalesMan> actualSalesManList = DbManagement.readAllSalesMen();
        assertEquals(0, actualSalesManList.size());
    }

    @Test
    public void deleteSocialPerformanceRecord() {
        //[GIVEN]
        DbManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);

        //[WHEN]
        DbManagement.deleteSocialPerformanceRecord(socialPerformanceRecord);

        //[THEN]
        assertEquals(0, DbManagement.getPerformanceColl().countDocuments());

        //deleting files
        DbManagement.getSalesmenColl().deleteMany(eq("sid", 11));
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
        assertEquals(expectedRec.toString(), actualRec.toString());
    }
}