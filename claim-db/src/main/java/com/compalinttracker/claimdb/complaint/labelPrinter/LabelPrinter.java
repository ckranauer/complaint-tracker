package com.compalinttracker.claimdb.complaint.labelPrinter;

import com.compalinttracker.claimdb.complaint.ComplaintDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LabelPrinter implements  PrintService{

    @Override
    public Boolean print(ComplaintDto complaintDto) {

        // TODO: 1. Create label String   --> this part can vary, different product with different labels
        // label factory -> depending on the complaintDto.getProdGroup gives back a label -> pl MIB2 group -> MIB2 label
        // MIB2 label implements the Box label interface, and it need a
            // TODO: Create a BoxLabel interface
            // TODO: pl MIB2 implement BoxLabel then it should have a build label method and should give back a string

        // 2. TODO: Print the String with the label printer
        //
        // string string builder

        return Boolean.TRUE;
    }
}
