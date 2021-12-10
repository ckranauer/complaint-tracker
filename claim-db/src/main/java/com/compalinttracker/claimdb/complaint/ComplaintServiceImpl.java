package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.analysis.AnalysisRepository;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.compalinttracker.claimdb.userProfile.UserProfileService;
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
    private final AnalysisRepository analysisRepository;
    private final UserProfileService userProfileService;

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
        log.info("Deleting complaint by id: {}", id);
        complaintRepository.deleteById(id);
        return Boolean.TRUE;
    }

    @Override
    public Analysis addAnalysis(AnalysisDto analysisDto) {
        log.info("Add analysis to the complaint.");
        Analysis analysis = new Analysis();
        Complaint complaint = complaintRepository.getById(analysisDto.getComplaintId());
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
}
