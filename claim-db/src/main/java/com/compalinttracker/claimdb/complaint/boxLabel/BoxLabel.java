package com.compalinttracker.claimdb.complaint.boxLabel;

import com.compalinttracker.claimdb.complaint.ComplaintDto;

public interface BoxLabel {

    String prepareLabelContent(ComplaintDto complaintDto);
}
