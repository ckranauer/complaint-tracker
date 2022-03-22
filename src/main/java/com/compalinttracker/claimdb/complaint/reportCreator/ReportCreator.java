package com.compalinttracker.claimdb.complaint.reportCreator;

import com.compalinttracker.claimdb.analysis.Analysis;

import java.io.File;

public interface ReportCreator {

    File create(Analysis analysis) throws Exception;
}
