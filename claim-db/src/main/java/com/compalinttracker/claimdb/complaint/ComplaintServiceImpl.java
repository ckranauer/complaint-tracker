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
import java.util.Optional;

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

        // Check if the complaint is already exists, if the serial number is already used then the complaint is already exist
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintBySerNo(complaintDto.getSerialNumber());

        if(complaintOptional.isPresent()){
            throw new IllegalStateException(String.format("Complaint with "+ complaintDto.getSerialNumber() + " serial number is already exists"));
        }

        if(complaintDto.getQmsNumber() != null ){
            if(complaintOptional.isPresent()  && complaintDto.getQmsNumber().equals(complaintOptional.get().getQmsNumber())){
                throw new IllegalStateException(String.format("Complaint with "+ complaintDto.getQmsNumber() + " qms number is already exists"));
            }
        }

        Complaint complaint = new Complaint();
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setSerialNumber(complaintDto.getSerialNumber());
        complaint.setQmsNumber(complaintDto.getQmsNumber());
        complaint.setCustomerRefNumber(complaintDto.getCustomerRefNumber());
        complaint.setClaimedFault(complaintDto.getClaimedFault());
        if(complaintDto.getArrivedAt().length() > 0){
            int year = Integer.parseInt(complaintDto.getArrivedAt().substring(0,4)); // TODO: create method in the class
            int month = Integer.parseInt(complaintDto.getArrivedAt().substring(5,7));
            int day = Integer.parseInt(complaintDto.getArrivedAt().substring(8,10));
            LocalDate date = LocalDate.of(year, Month.of(month), day);
            complaint.setArrivedAt(date );
        }
        if(complaintDto.getIsPrio() != null){
            complaint.setPrio(complaintDto.getIsPrio());
        }
        if(complaintDto.getResponsible() != null) {
            UserProfile responsible = userProfileService.get(complaintDto.getResponsible());
            responsible.addComplaint(complaint);
            complaint.setResponsible(responsible);
        }
        return complaintRepository.save(complaint);
    }

    @Override
    public Collection<Complaint> list(int limit, int page) {
        log.info("Fetching all complaints");
        return complaintRepository.findAll(PageRequest.of(page,limit)).toList();
    }

    @Override
    public Complaint get(Long id) {
        log.info("Fetching complaint by id: {}", id);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with id: "+ id + " is not exist."));
        }
        return complaintOptional.get();
    }

    @Override
    public Complaint update(ComplaintDto complaintDto) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting complaint by id: {}", id);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);

        if(complaintOptional.isEmpty()){
            throw new IllegalStateException("Complaint with id: "+ id + " does not exists.");
        }

        if(complaintRepository.deleteComplaintById(id) == 0){
            throw new IllegalStateException("Complaint with id: "+ id + " cannot be deleted.");
        }
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
        if(analysisDto.getAnalyzedBy() != null){
            UserProfile user = userProfileService.get(analysisDto.getAnalyzedBy());
            user.addAnalysis(analysis);
            analysis.setAnalyzedBy(user);
        }
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
    public void createAnalysisReport(Long complaintId) {
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(complaintId);
        Complaint complaint;
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with " + complaintId + " does not exists."));
        }

        complaint = complaintOptional.get();

        Optional<Analysis> analysisOptional = analysisRepository.findById(complaintId);

        Analysis analysis;
        // If the complaint does not contain analysis then the report will only contain the complaint data
        if(analysisOptional.isPresent()){
            analysis = analysisOptional.get();
        }

        // TODO : create reportCreator service and implementation
        // TODO: report creator -> factory ? different reports or one standard



    }

    @Override
    public void printLabel(Long ComplaintId) {
        // TODO: create label printer service
    }
}
