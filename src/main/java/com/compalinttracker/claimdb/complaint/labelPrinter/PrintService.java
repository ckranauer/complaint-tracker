package com.compalinttracker.claimdb.complaint.labelPrinter;

import com.compalinttracker.claimdb.complaint.ComplaintDto;

import java.io.IOException;

public interface PrintService {

    Boolean print(ComplaintDto complaintDto) throws IOException;
}
