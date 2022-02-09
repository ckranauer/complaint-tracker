package com.compalinttracker.claimdb.complaint.boxLabel;

import com.compalinttracker.claimdb.complaint.ComplaintDto;

public class ToyotaLabel implements BoxLabel{

    @Override
    public String prepareLabelContent(ComplaintDto complaintDto) {
        String labelContent =
                "^XA" +
                        "^A0N,30,30^FO50,20^FDSNR: " + complaintDto.getSerialNumber()  + "^FS " +
                        "^A0N,30,30^FO50,60^FDType: "+ complaintDto.getProductInfo().toUpperCase() + "^FS " +
                        "^A0N,15,15^FO50,110^FD Incoming date: ^FS " +
                        "^A0N,30,30^FO50,140^FD: "+ complaintDto.getArrivedAt() /*.substring(0,10).replace("-",".")*/ + "^FS " +
                        "^A0N,15,15^FO200,110^FD Analysis ended: ^FS " +
                        "^FO200,130^GB150,50,3^FS" +
                        "^A0N,15,15^FO50,280^FD"+"Problem description: " + "^FS " +
                        "^A0N,20,20^FO50,310^FD"+ complaintDto.getClaimedFault() + "^FS " +
                        "^A0N,15,15^FO50,350^FD"+"Conclusion: " + "^FS " +
                        "^FO50,370^GB600,100,3^FS " +

                        "^A0N,30,30^FO450,20^FD"+"Photos: " + "^FS " +
                        "^A0N,30,30^FO450,60^FD"+"S1: " + "^FS " +
                        "^A0N,30,30^FO450,100^FD"+"S2: " + "^FS " +
                        "^A0N,30,30^FO450,140^FD"+"B1: " + "^FS " +
                        "^A0N,30,30^FO450,180^FD"+"B2: " + "^FS " +
                        "^A0N,30,30^FO450,220^FD"+"B3: " + "^FS " +
                        "^A0N,30,30^FO450,260^FD"+"Scrap: " + "^FS " +
                        "^A0N,30,30^FO450,300^FD"+"Sample: " + "^FS " +

                        "^FO630,20^GB30,30,3^FS " +
                        "^FO630,60^GB30,30,3^FS " +
                        "^FO630,100^GB30,30,3^FS " +
                        "^FO630,140^GB30,30,3^FS " +
                        "^FO630,180^GB30,30,3^FS " +
                        "^FO630,220^GB30,30,3^FS " +
                        "^FO630,260^GB30,30,3^FS " +
                        "^FO630,300^GB30,30,3^FS " +
                        "^BY2,2,50" + "^FO700,20^BCB^FD" + complaintDto.getSerialNumber()  + "^FS " +
                        "^XZ";

        return labelContent;
    }
}
