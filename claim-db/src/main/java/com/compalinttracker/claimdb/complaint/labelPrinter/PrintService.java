package com.compalinttracker.claimdb.complaint.labelPrinter;

import com.compalinttracker.claimdb.complaint.ComplaintDto;

public interface PrintService {

    Boolean print(ComplaintDto complaintDto);
}
