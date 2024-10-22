import static org.junit.jupiter.api.Assertions.assertEquals;

import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.model.SocialPerformanceRecord;

public class TestSocialPerformanceRecord {
    private SocialPerformanceRecord SocPerfRec;

    @BeforeEach
    public void setup(){
        SocPerfRec = new SocialPerformanceRecord(12, 2024, 5, 6, 7, 2, 3, 4);
    }

    @AfterEach
    public void teardown(){
        SocPerfRec = null;
    }

    @Test
    public void toDocument(){
        //[GIVEN]
        org.bson.Document givenDocument = new Document();
        org.bson.Document actualDocument = new Document();

        givenDocument.append("pid", SocPerfRec.getId());
        givenDocument.append("year", SocPerfRec.getYear());
        givenDocument.append("leadershipCompetence", SocPerfRec.getLeadershipCompetence());
        givenDocument.append("opennessToEmployee", SocPerfRec.getOpennessToEmployee());
        givenDocument.append("socialBehaviourToEmployee", SocPerfRec.getSocialBehaviourToEmployee());
        givenDocument.append("attitudeTowardsClient", SocPerfRec.getAttitudeTowardsClient());
        givenDocument.append("communicationSkills", SocPerfRec.getCommunicationSkills());
        givenDocument.append("integrityToCompany", SocPerfRec.getIntegrityToCompany());

        //[WHEN]
        actualDocument = SocPerfRec.toDocument();

        //[THEN]
        assertEquals(givenDocument, actualDocument);
    }
}