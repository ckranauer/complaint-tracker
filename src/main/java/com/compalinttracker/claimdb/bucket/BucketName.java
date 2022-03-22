package com.compalinttracker.claimdb.bucket;

public enum BucketName {
    ANALYSIS_REPORT("analysis-report-upload");

    private final String bucketname;

    BucketName(String bucketname) {
        this.bucketname = bucketname;
    }

    public String getBucketName(){
        return bucketname;
    }
}
