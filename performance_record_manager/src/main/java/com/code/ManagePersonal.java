package com.code;

import com.model.SalesMan;
//import com.model.SocialPerformanceRecord;

import java.util.List;

/**
 * Code lines are commented for suppressing compile errors.
 * Are there any CRUD-operations missing?
 */
public interface ManagePersonal {
    public void createSalesMan( SalesMan record );

    // public void addSocialPerformanceRecord(SocialPerformanceRecord record , SalesMan salesMan );
    // Remark: an SocialPerformanceRecord corresponds to part B of a bonus sheet

    public SalesMan readSalesMan( int sid );

    public List<SalesMan> readAllSalesMen();

    // public List<SocialPerformanceRecord> readSocialPerformanceRecord( SalesMan salesMan );
    // Remark: How do you integrate the year?

}
