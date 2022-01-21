package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;

import java.io.IOException;
import java.util.Collection;


public interface ComplaintService {

    Collection<ComplaintAnalysisDto>  create(ComplaintDto complaintDto);
    Collection<ComplaintAnalysisDto> list(int limit, int page);
    Complaint get(Long id);
    Complaint update(Long id, ComplaintDto complaint);
    Boolean delete(Long id);

    Analysis addAnalysis(AnalysisDto analysisDto);
    Boolean deleteAnalysis(Long id);
    Analysis update(Long complaintId, AnalysisDto analysis);
    Analysis getAnalysis(Long id);


    Boolean createAnalysisReport(Long analysisId) throws Exception;
    Boolean printLabel(ComplaintDto complaintDto) throws IOException;              // TODO: implement printLabel
    Boolean printSavedLabel(Long id) throws IOException;
}
