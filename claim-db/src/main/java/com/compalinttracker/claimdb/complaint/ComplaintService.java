package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;

import java.util.Collection;


public interface ComplaintService {

    Complaint create(ComplaintDto complaintDto);
    Collection<Complaint> list(int limit);
    Complaint get(Long id);
    Complaint update(ComplaintDto complaint);       //TODO: implement complaint update
    Boolean delete(Long id);

    Analysis addAnalysis(AnalysisDto analysisDto);
    Boolean deleteAnalysis(Long id);
    Analysis update(AnalysisDto analysis);          //TODO: implement analysis update

    void createAnalysisReport(Long analysisId);
    void printLabel(Long ComplaintId);

}
