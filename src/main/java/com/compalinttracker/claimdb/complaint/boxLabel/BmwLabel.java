package com.compalinttracker.claimdb.complaint.boxLabel;

import com.compalinttracker.claimdb.complaint.ComplaintDto;

public class BmwLabel implements BoxLabel{

    @Override
    public String prepareLabelContent(ComplaintDto complaintDto) {
        String labelContent =
                "^XA" +
                "^A0N,30,30^FO50,20^FDQMS: " + complaintDto.getQmsNumber() + "^FS " +
                "^A0N,30,30^FO50,60^FDCRN: "+ complaintDto.getCustomerRefNumber() + "^FS " +
                "^A0N,30,30^FO50,100^FDSNR: "+ complaintDto.getSerialNumber() + "^FS " +
                "^A0N,30,30^FO50,140^FDType: "+ complaintDto.getProductInfo().toUpperCase() + "^FS " +
                "^A0N,15,15^FO50,190^FD"+"Incoming date: " + "^FS " +
                "^A0N,30,30^FO50,220^FD"+ complaintDto.getFormattedArrivedAt()+ "^FS " +
                "^A0N,15,15^FO200,190^FD"+"Analysis ended: " + "^FS " +
                "^FO200,210^GB150,50,3^FS " +
                "^A0N,15,15^FO50,280^FD"+"Problem description: " + "^FS " +
                "^A0N,20,20^FO50,310^FD"+ complaintDto.getClaimedFault()+ "^FS " +
                "^A0N,15,15^FO50,350^FD"+"Conclusion: " + "^FS " +
                "^FO50,370^GB600,100,3^FS " +

                "^A0N,30,30^FO420,20^FD"+"OABR: " + "^FS " +
                "^A0N,30,30^FO420,60^FD"+"Display: " + "^FS " +
                "^A0N,30,30^FO420,100^FD"+"Radio: " + "^FS " +
                "^A0N,30,30^FO420,140^FD"+"USB: " + "^FS " +
                "^A0N,30,30^FO420,180^FD"+"BT: " + "^FS " +
                "^A0N,30,30^FO420,220^FD"+"Waiting for SQA: " + "^FS " +
                "^A0N,30,30^FO420,260^FD"+"Scrap: " + "^FS " +

                "^FO630,20^GB30,30,3^FS " +
                "^FO630,60^GB30,30,3^FS " +
                "^FO630,100^GB30,30,3^FS " +
                "^FO630,140^GB30,30,3^FS " +
                "^FO630,180^GB30,30,3^FS " +
                "^FO630,220^GB30,30,3^FS " +
                "^FO630,260^GB30,30,3^FS " +

                "^BY2,2,50" + "^FO700,20^BCB^FD" + complaintDto.getSerialNumber()  + "^FS " +
                "^XZ";

        return labelContent;
    }
}
