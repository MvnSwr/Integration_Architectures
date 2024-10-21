package com.code;

import com.model.*;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManagement implements ManagePersonal {
    private String uri = "mongodb://localhost:27017";
    private MongoClient mongoClient;
    private MongoDatabase mongoDatabase;
    private MongoCollection<Document> salesmenColl;
    private MongoCollection<Document> performanceColl;

    public DatabaseManagement() {
        this.mongoClient = MongoClients.create(uri);
        this.mongoDatabase = mongoClient.getDatabase("performanceRecordDatabase");
        this.salesmenColl = mongoDatabase.getCollection("salesmen");
        this.performanceColl = mongoDatabase.getCollection("performance_records");
    }


    @Override
    public void createSalesMan(SalesMan record) {
        this.salesmenColl.insertOne(record.toDocument());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record , SalesMan salesMan) {
        record.setSid(salesMan);
        this.salesmenColl.insertOne(salesMan.toDocument());
        this.performanceColl.insertOne(record.toDocument());
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        return docToSalesman(this.salesmenColl.find(Filters.eq("sid", sid)).first());
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        List<SalesMan> salesmenList = new ArrayList<>();

        try (MongoCursor<Document> mCursor = salesmenColl.find().iterator()) {
            while (mCursor.hasNext()) {
                Document doc = mCursor.next();
                SalesMan sm = docToSalesman(doc);
                salesmenList.add(sm);
            }
        }

        return salesmenList;
    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        List<SocialPerformanceRecord> socialPerformanceRecordList = new ArrayList<>();

        try (MongoCursor<Document> mCursor = salesmenColl.find().iterator()) {
            while (mCursor.hasNext()) {
                Document doc = mCursor.next();
                if (doc.getInteger("pid") == salesMan.getSid()) {
                    SocialPerformanceRecord socialPerformanceRecord = docToSocialPerformanceRecord(doc);
                    socialPerformanceRecordList.add(socialPerformanceRecord);
                }
            }
        }

        return socialPerformanceRecordList;
    }

    @Override
    public void deleteSalesMan(SalesMan salesMan) {
        this.salesmenColl.deleteOne(Filters.eq("sid", salesMan.getSid()));
    }

    @Override
    public void deleteSocialPerformanceRecord(SocialPerformanceRecord socialPerformanceRecord) {
        this.performanceColl.deleteOne(Filters.eq("pid", socialPerformanceRecord.getId()));
    }

    // user methods
    /*
    * convert from document to salesman object type.
    * */
    private SalesMan docToSalesman(Document doc) {
        SalesMan sm = new SalesMan(doc.getString("firstname"), doc.getString("lastname"),
                doc.getInteger("sid"));

        return sm;
    }

    public SocialPerformanceRecord docToSocialPerformanceRecord(Document doc) {
        Integer id = doc.getInteger("pid");
        Integer sid = doc.getInteger("sid");
        Integer year = doc.getInteger("year");
        Integer leadership = doc.getInteger("leadershipCompetence");
        Integer openness = doc.getInteger("opennessToEmployee");
        Integer socialBehaviourToEmployee = doc.getInteger("socialBehaviourToEmployee");
        Integer attitudeTowardsClient = doc.getInteger("attitudeTowardsClient");
        Integer communicationSkills = doc.getInteger("communicationSkills");
        Integer integrityToCompany = doc.getInteger("integrityToCompany");

        SocialPerformanceRecord socialPerformanceRecord = new SocialPerformanceRecord(id, year, leadership, openness,
                socialBehaviourToEmployee, attitudeTowardsClient, communicationSkills, integrityToCompany);
        socialPerformanceRecord.setSid(sid);

        return socialPerformanceRecord;
    }



    public MongoClient getMongoClient() {
        return mongoClient;
    }

    public MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

    public MongoCollection getSalesmenColl() {
        return salesmenColl;
    }

    public MongoCollection getPerformanceColl() {
        return performanceColl;
    }
}
