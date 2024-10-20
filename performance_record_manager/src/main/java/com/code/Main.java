package com.code;

import com.model.SalesMan;
import com.model.SocialPerformanceRecord;

public class Main {
    public static void main(String[] args) {
        DatabaseManagement databaseManagement = new DatabaseManagement();

        SalesMan salesMan = new SalesMan("Reise", "Kato", 13);
        SocialPerformanceRecord socialPerformanceRecord = new SocialPerformanceRecord(10, 2024, 5, 5, 5, 5, 5, 5);

        databaseManagement.addSocialPerformanceRecord(socialPerformanceRecord, salesMan);
        System.out.println(databaseManagement.readSalesMan(13));
    }
}
