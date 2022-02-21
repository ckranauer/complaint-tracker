package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;

import java.io.IOException;
import java.util.Collection;


public interface ComplaintService {

    Collection<ComplaintAnalysisDto>  create(ComplaintDto complaintDto);
    Collection<ComplaintAnalysisDto> list(int limit, int page);
    ComplaintAnalysisDto get(Long id);
    ComplaintAnalysisDto search(String serialNumber);
    Collection<ComplaintAnalysisDto> update(ComplaintUpdateDto complaintDto);
    Boolean delete(Long id);



    Boolean createAnalysisReport(Long analysisId) throws Exception;
    Boolean printLabel(ComplaintDto complaintDto) throws IOException;
    Boolean printSavedLabel(Long id) throws IOException;
}
