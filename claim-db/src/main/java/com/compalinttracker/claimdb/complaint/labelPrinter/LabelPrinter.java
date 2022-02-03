package com.compalinttracker.claimdb.complaint.labelPrinter;

import com.compalinttracker.claimdb.complaint.ComplaintDto;
import com.compalinttracker.claimdb.complaint.boxLabel.BoxLabel;
import com.compalinttracker.claimdb.complaint.boxLabel.LabelFactory;
import com.compalinttracker.claimdb.complaint.boxLabel.Mib2Label;
import com.compalinttracker.claimdb.printerServer.PrinterServer;
import com.compalinttracker.claimdb.printerServer.PrinterServerImpl;
import com.compalinttracker.claimdb.printerServer.PrinterServerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LabelPrinter implements  PrintService{

    private final ZebraPrinter zebraPrinter;
    private final PrinterServerRepository printerServerRepository;
    private final LabelFactory labelFactory;


    @Override
    public Boolean print(ComplaintDto complaintDto) throws IOException {

        Optional<PrinterServer> printerServerOptional = printerServerRepository.findById(1l);
        PrinterServer server = printerServerOptional.get();
        BoxLabel label = labelFactory.getLabel(complaintDto.getProductInfo().toUpperCase());

        zebraPrinter.startConnection(server.getIp(), server.getPortNumber());
        zebraPrinter.sendMessage(label.prepareLabelContent(complaintDto));
        zebraPrinter.stopConnection();

        return Boolean.TRUE;
    }
}
