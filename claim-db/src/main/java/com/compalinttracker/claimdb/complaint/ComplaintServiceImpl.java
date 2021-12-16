package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.analysis.AnalysisRepository;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.compalinttracker.claimdb.userProfile.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ComplaintServiceImpl implements  ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final AnalysisRepository analysisRepository;
    private final UserProfileService userProfileService;

    @Override
    public Complaint create(ComplaintDto complaintDto) {
        log.info("Saving new complaint: {}", complaintDto.getQmsNumber());
        Complaint complaint = new Complaint();
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setSerialNumber(complaintDto.getSerialNumber());
        complaint.setQmsNumber(complaintDto.getQmsNumber());
        complaint.setCustomerRefNumber(complaintDto.getCustomerRefNumber());
        complaint.setClaimedFault(complaintDto.getClaimedFault());
        int year = Integer.parseInt(complaintDto.getArrivedAt().substring(0,4)); // TODO: create method in the class
        int month = Integer.parseInt(complaintDto.getArrivedAt().substring(5,7));
        int day = Integer.parseInt(complaintDto.getArrivedAt().substring(8,10));
        LocalDate date = LocalDate.of(year, Month.of(month), day);
        complaint.setArrivedAt(date );
        complaint.setPrio(complaintDto.getIsPrio());
        if(complaintDto.getResponsible() != 0) {
            UserProfile responsible = userProfileService.get(complaintDto.getResponsible());
            responsible.addComplaint(complaint);
            complaint.setResponsible(responsible);
        }
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
    public Complaint update(ComplaintDto complaintDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting complaint by id: {}", id);
        complaintRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Analysis addAnalysis(AnalysisDto analysisDto) {
        log.info("Add analysis to the complaint.");
        Analysis analysis = new Analysis();
        Complaint complaint = complaintRepository.getById(analysisDto.getComplaintId());
        // TODO: put these to the class (pass the Dto as argument and set values inside the class) ??
        analysis.setComplaint(complaint);
        analysis.setBarcodes(analysisDto.getBarcodes());
        analysis.setLifecycleInfo(analysisDto.getLifecycleInfo());
        analysis.setVisualAnalysis(analysisDto.getVisualAnalysis());
        analysis.setElectricalAnalysis(analysisDto.getElectricalAnalysis());
        analysis.setConclusion(analysisDto.getConclusion());
        analysis.setAnalysisEndedAt(analysisDto.getAnalysisEndedAt());
        analysis.setAnalysisStartedAt(analysisDto.getAnalysisStartedAt());
        UserProfile user = userProfileService.get(analysisDto.getAnalyzedBy());
        user.addAnalysis(analysis);
        analysis.setAnalyzedBy(user);
        complaint.setAnalysis(analysis);
        return analysisRepository.save(analysis);
    }

    @Override
    public Boolean deleteAnalysis(Long complaintId) {
        log.info("Deleting analysis by id: {}", complaintId);
        Complaint complaint = complaintRepository.getById(complaintId);
        analysisRepository.deleteAnalysisByComplaintId(complaintId);
        return Boolean.TRUE;
    }

    @Override
    public Analysis update(AnalysisDto analysis) {
        return null;
    }

    @Override
    public void createAnalysisReport(Long analysisId) {

    }
}
