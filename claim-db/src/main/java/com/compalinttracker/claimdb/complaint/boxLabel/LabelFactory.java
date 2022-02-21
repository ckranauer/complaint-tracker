package com.compalinttracker.claimdb.complaint.boxLabel;

import org.springframework.stereotype.Component;

@Component
public class LabelFactory {

    public BoxLabel getLabel(String type){
        if(type.equals("MIB2")){
            return new Mib2Label();
        }
        if(type.equals("VW")){
            return new Mib2Label();
        }
        if(type.equals("AUDI")){
            return new Mib2Label();
        }
        else if(type.equals("TOYOTA")){
            return new ToyotaLabel();
        }
        else if(type.equals("BMW")){
            return new BmwLabel();
        }

        return null;
    }


}
