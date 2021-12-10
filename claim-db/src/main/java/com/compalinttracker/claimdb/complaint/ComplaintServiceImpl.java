package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.analysis.AnalysisRepository;
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
        log.info("Complaint: " + analysis.getComplaint().getId());
        analysis.setBarcodes(analysisDto.getBarcodes());
        log.info("Barcodes: " + analysis.getBarcodes());
        analysis.setLifecycleInfo(analysisDto.getLifecycleInfo());
        log.info("Lifecycle: " + analysis.getLifecycleInfo());
        analysis.setVisualAnalysis(analysisDto.getVisualAnalysis());
        log.info("Visual: " + analysis.getVisualAnalysis());
        analysis.setElectricalAnalysis(analysisDto.getElectricalAnalysis());
        log.info("Electrical: " + analysis.getElectricalAnalysis());
        analysis.setConclusion(analysisDto.getConclusion());
        log.info("Conclusion: " + analysis.getConclusion());
        analysis.setAnalysisEndedAt(analysisDto.getAnalysisEndedAt());
        log.info("Ended at: " + analysis.getAnalysisEndedAt());
        analysis.setAnalysisStartedAt(analysisDto.getAnalysisStartedAt());
        log.info("Started at: " + analysis.getAnalysisStartedAt());
        log.info("Analyzed by: " + analysis.getAnalyzedBy());
        complaint.setAnalysis(analysis);
        //analysisRepository.save(analysis);
        //log.info("does it  work ?");
        //analysisRepository.get
        //analysis.getComplaint();  // TODO: i need to write a query which fetch first the complaint then add the complain to the analysis
        return analysisRepository.save(analysis);//analysisRepository.getById(analysis.getId());
    }
}
