package com.compalinttracker.claimdb.complaint;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ComplaintServiceImpl implements  ComplaintService{

    private final ComplaintRepository complaintRepository;

    @Override
    public Complaint create(Complaint complaint) {
        log.info("Saving new complaint: {}", complaint.getQmsNumber());
        complaint.setCreatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }

    @Override
    public Collection<Complaint> list(int limit) {
        log.info("Fetching all complaints");
        return complaintRepository.findAll(PageRequest.of(0,limit)).toList();
    }

    @Override
    public Complaint get(Long id) {
        log.info("Fetching complaint by id: {}", id);
        return complaintRepository.findComplaintById(id).get();
    }

    @Override
    public Complaint update(Complaint complaint) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
