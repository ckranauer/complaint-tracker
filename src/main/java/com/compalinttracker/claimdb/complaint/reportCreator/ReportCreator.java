package com.compalinttracker.claimdb.complaint.reportCreator;

import com.compalinttracker.claimdb.analysis.Analysis;

public interface ReportCreator {

    void create(Analysis analysis) throws Exception;
}
