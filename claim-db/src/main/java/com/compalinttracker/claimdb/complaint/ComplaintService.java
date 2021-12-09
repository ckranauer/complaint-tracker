package com.compalinttracker.claimdb.complaint;


import java.util.Collection;


public interface ComplaintService {

    Complaint create(Complaint complaint);
    Collection<Complaint> list(int limit);
    Complaint get(Long id);
    Complaint update(Complaint complaint);
    Boolean delete(Long id);
}
