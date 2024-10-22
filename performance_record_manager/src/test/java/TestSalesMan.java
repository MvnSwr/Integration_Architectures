import com.model.SalesMan;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSalesMan {
    private SalesMan salesMan;

    @BeforeEach
    public void setup(){
        salesMan = new SalesMan("Peter", "Dieter", 11);
    }

    @AfterEach
    public void teardown(){
        salesMan = null;
    }

    @Test
    public void toDocument(){
        //[GIVEN]
        org.bson.Document givenDocument = new Document();
        org.bson.Document actualDocument = new Document();

        givenDocument.append("firstname" , salesMan.getFirstName());
        givenDocument.append("lastname" , salesMan.getLastName() );
        givenDocument.append("sid" , salesMan.getSid());

        //[WHEN]
        actualDocument = salesMan.toDocument();

        //[THEN]
        assertEquals(givenDocument, actualDocument);
    }
}
