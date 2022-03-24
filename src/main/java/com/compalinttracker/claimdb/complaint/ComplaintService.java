package com.compalinttracker.claimdb.complaint;


import java.io.IOException;
import java.util.Collection;
import java.util.List;


public interface ComplaintService {

    void create(ComplaintDto complaintDto);

    List<ComplaintAnalysisDto> list();

    ComplaintAnalysisDto get(Long id);

    ComplaintAnalysisDto search(String serialNumber);

    void update(ComplaintUpdateDto complaintDto);

    Boolean delete(Long id);

    byte[] createAnalysisReport(Long analysisId) throws Exception;

    Boolean printLabel(ComplaintDto complaintDto) throws IOException;

    Boolean printSavedLabel(Long id) throws IOException;


}
