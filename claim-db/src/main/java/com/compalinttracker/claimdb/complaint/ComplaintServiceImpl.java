package com.compalinttracker.claimdb.complaint;


import com.compalinttracker.claimdb.analysis.Analysis;
import com.compalinttracker.claimdb.analysis.AnalysisDto;
import com.compalinttracker.claimdb.analysis.AnalysisRepository;
import com.compalinttracker.claimdb.complaint.labelPrinter.LabelPrinter;
import com.compalinttracker.claimdb.complaint.reportCreator.ReportCreatorImpl;
import com.compalinttracker.claimdb.userProfile.UserProfile;
import com.compalinttracker.claimdb.userProfile.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ComplaintServiceImpl implements  ComplaintService{

    private final ComplaintRepository complaintRepository;
    private final AnalysisRepository analysisRepository;
    private final UserProfileRepository userProfileRepository;
    private final ReportCreatorImpl reportCreator;
    private final LabelPrinter labelPrinter;

    @Override
    public Collection<ComplaintAnalysisDto> create(ComplaintDto complaintDto) {
        log.info("Saving new complaint: {}", complaintDto.getSerialNumber());

        // Check serial number
        isSerialNumberValid(complaintDto.getSerialNumber());

        // Check QMS number and set QMS number null if the value is blank
        //complaintDto = isQMSNumberValid(complaintDto);
        isQMSNumberValid(complaintDto);

        // Check if the complaint is already exist with this serial number
        complaintWithThisSerialNumberAlreadyExists(complaintDto);

        Complaint complaint = createComplaint(complaintDto);

        // There is no need the return value because the @Transactional
        addResponsibleToComplaint(complaintDto.getResponsible(), complaint);

        complaintRepository.save(complaint);
        Pageable pageable = PageRequest.of(0,20);
        return complaintRepository.findAllComplaintAnalysis(pageable);
    }



    private Complaint createComplaint(ComplaintDto complaintDto) {
        Complaint complaint = new Complaint();
        complaint.setCreatedAt(LocalDateTime.now());
        complaint.setSerialNumber(complaintDto.getSerialNumber());
        complaint.setQmsNumber(complaintDto.getQmsNumber());
        complaint.setCustomerRefNumber(complaintDto.getCustomerRefNumber());
        complaint.setClaimedFault(complaintDto.getClaimedFault());
        complaint.setArrivedAt(complaintDto.getArrivedAt());
        // it will be false by default
        if(complaintDto.getIsPrio() != null){
            complaint.setPrio(complaintDto.getIsPrio());
        }
        complaint.setProductInfo(complaintDto.getProductInfo());
        Analysis analysis = new Analysis();
        analysis.setComplaint(complaint);
        complaint.setAnalysis(analysis);
        return complaint;
    }

    private void complaintWithThisSerialNumberAlreadyExists(ComplaintDto complaintDto) {
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintBySerNo(complaintDto.getSerialNumber());

        if(complaintOptional.isPresent()){
            throw new IllegalStateException(String.format("Complaint with "+ complaintDto.getSerialNumber() + " serial number is already exist"));
        }
    }

    private ComplaintDto isQMSNumberValid(ComplaintDto complaintDto) {
        // QMS number is enabled to be null
        // but it is not enabled to be empty/blank
        // it must be unique
        if(complaintDto.getQmsNumber() != null){
            // Check if it is alredy in use
            Optional<Complaint> complaintOptional = complaintRepository.findComplaintByQmsNo(complaintDto.getQmsNumber());
            if(complaintOptional.isPresent()){
                throw new IllegalStateException(String.format("Complaint with "+ complaintDto.getQmsNumber() + " qms number is already exist"));
            }
            // if the value from the front-end is blank then set the value to null
            if(complaintDto.getQmsNumber().isBlank()) {
                complaintDto.setQmsNumber(null);
            }
        }
        return complaintDto;
    }

    private void isSerialNumberValid(String serialNumber) {
        if(serialNumber== null){
            throw new IllegalStateException(String.format("Serial number can not be null"));
        }
        if(serialNumber.length() == 0){
            throw new IllegalStateException(String.format("Serial number can not be empty"));
        }
    }

    @Override
    public Collection<ComplaintAnalysisDto> list(int limit, int page) {
        log.info("Fetching all complaints");
        return complaintRepository.findAllComplaintAnalysis(PageRequest.of(page,limit));
    }

    @Override
    public ComplaintAnalysisDto get(Long id) {
        log.info("Fetching complaint by id: {}", id);
        Optional<ComplaintAnalysisDto> complaintAnalysisDtoOptional = complaintRepository.findComplaintAnalysisById(id);
        if(complaintAnalysisDtoOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with id: "+ id + " is not exist."));
        }
        return complaintAnalysisDtoOptional.get();
    }

    @Override
    public ComplaintAnalysisDto search(String serialNumber) {
        log.info("Search complaint by serial number: {}", serialNumber);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintBySerNo(serialNumber);
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with serial number: "+ serialNumber + " is not exist."));
        }
        return complaintRepository.findComplaintAnalysisById(complaintOptional.get().getId()).get();
    }

    @Override
    public Collection<ComplaintAnalysisDto> update( ComplaintUpdateDto complaintDto) {
        log.info("Update complaint: {}", complaintDto.getId());

        // Read complaint from db
        // If it not exists throws exception
        Complaint complaint = getComplaint(complaintDto.getId());

        // Serial number cannot be null, blank or taken
        isSerialNumberValid(complaintDto.getSerialNumber());

        // Check if serial number is taken
        throwExceptionIfSernoIsTaken(complaintDto.getId(), complaintDto);

        // Check if QMS number is valid or taken
        isQMSValidAndNotTaken(complaintDto);

        // Check if responsible exists in db then add to complaint or throws exception
        addResponsibleToComplaint(complaintDto.getResponsible(), complaint);

        // Set analysis fields
        Analysis analysis = getAnalysis(complaintDto, complaint);

        // Check if analyzedBy exists in db then add to Analysis or throws exception
        updateAnalyzedBy(complaintDto.getAnalyzedBy(), analysis);

        // Set complaint fields and add the analysis to complaint
        setComplaintAnalysis(complaintDto, complaint, analysis);

        complaintRepository.save(complaint);
        return complaintRepository.findAllComplaintAnalysis(PageRequest.of(0,10));
    }

    private void setComplaintAnalysis(ComplaintUpdateDto complaintDto, Complaint complaint, Analysis analysis) {
        complaint.setSerialNumber(complaintDto.getSerialNumber());
        complaint.setQmsNumber(complaintDto.getQmsNumber());
        complaint.setCustomerRefNumber(complaintDto.getCustomerRefNumber());
        complaint.setClaimedFault(complaintDto.getClaimedFault());
        complaint.setArrivedAt(complaintDto.getArrivedAt());
        if(complaintDto.getIsPrio() != null){
            complaint.setPrio(complaintDto.getIsPrio());
        }

        if (complaintDto.getProductInfo() != null){
            complaint.setProductInfo(complaintDto.getProductInfo());
        }
        complaint.setAnalysis(analysis);
    }

    private Analysis getAnalysis(ComplaintUpdateDto complaintDto, Complaint complaint) {
        Analysis analysis = complaint.getAnalysis();
        analysis.setBarcodes(complaintDto.getBarcodes());
        analysis.setLifecycleInfo(complaintDto.getLifecycleInfo());
        if(complaintDto.getFaultVerification() != null){
            analysis.setFaultVerification(complaintDto.getFaultVerification());
        }
        analysis.setVisualAnalysis(complaintDto.getVisualAnalysis());
        analysis.setElectricalAnalysis(complaintDto.getElectricalAnalysis());
        analysis.setConclusion(complaintDto.getConclusion());
        analysis.setAnalysisStartedAt(complaintDto.getAnalysisStartedAt());
        analysis.setAnalysisEndedAt(complaintDto.getAnalysisEndedAt());
        return analysis;
    }

    private void updateAnalyzedBy(UUID analyzedById, Analysis analysis) {
        if(analyzedById != null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(analyzedById);
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ analyzedById +" is not exist");
            }
            UserProfile analyzedBy = userProfileOptional.get();
            analyzedBy.addAnalysis(analysis);
            analysis.setAnalyzedBy(analyzedBy);
        }
    }

    private void addResponsibleToComplaint(UUID responsibleId, Complaint complaint) {
        if(responsibleId != null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(responsibleId);
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ responsibleId +" is not exist");
            }
            UserProfile responsible = userProfileOptional.get();
            responsible.addComplaint(complaint);
            complaint.setResponsible(responsible);
        }
    }



    private void isQMSValidAndNotTaken(ComplaintUpdateDto complaintDto) {
        if(complaintDto.getQmsNumber() != null){
            Optional<Complaint> qmsNoComplaintOptional = complaintRepository.findComplaintByQmsNo(complaintDto.getQmsNumber());
            if(qmsNoComplaintOptional.isPresent()){
                if(qmsNoComplaintOptional.get().getId() != complaintDto.getId()){
                    throw new IllegalStateException(String.format("Complaint with QMS number: "+ complaintDto.getQmsNumber() + " is already exist."));
                }
            }
        }
    }

    private void throwExceptionIfSernoIsTaken(Long id, ComplaintUpdateDto complaintDto) {
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintBySerNo(complaintDto.getSerialNumber());
        if(complaintOptional.isPresent()  ){
            // If yes then check if it belongs to the same complaint what we want to update,
            if(complaintOptional.get().getId() != id){
                // if it belongs to another claim
                throw new IllegalStateException(String.format("Complaint with serial number: " + complaintDto.getSerialNumber() + " is already exist."));
            }
        }
    }

    private Complaint getComplaint(Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint does not exist."));
        }
        Complaint complaint = complaintOptional.get();
        return complaint;
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting complaint by id: {}", id);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);

        if(complaintOptional.isEmpty()){
            throw new IllegalStateException("Complaint with id: "+ id + " does not exists.");
        }

        // First need to delete the analysis which belongs to this claim, if it exist
        Optional<Analysis> analysisOptional = analysisRepository.findAnalysisByComplaintId(id);

        if(analysisOptional.isPresent()){
            analysisRepository.deleteAnalysisByComplaintId(id);
        }

        if(complaintRepository.deleteComplaintById(id) == 0){
            throw new IllegalStateException("Complaint with id: "+ id + " cannot be deleted.");
        }
        return Boolean.TRUE;
    }


    /*
    public Boolean deleteAnalysis(Long complaintId) {
        log.info("Deleting analysis by id: {}", complaintId);
        Complaint complaint = complaintRepository.getById(complaintId);
        analysisRepository.deleteAnalysisByComplaintId(complaintId);
        return Boolean.TRUE;
    }
    */


    @Override
    public Boolean createAnalysisReport(Long complaintId) throws Exception {
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(complaintId);

        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with " + complaintId + " does not exists."));
        }

        Optional<Analysis> analysisOptional = analysisRepository.findById(complaintId);

        // If the complaint does not contain analysis then the report will only contain the complaint data
        if(analysisOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with id: " + complaintId + " does not contain analysis."));
        }
        Analysis analysis = analysisOptional.get();
        reportCreator.create(analysis);

        return Boolean.TRUE;
    }

    // object is come from the frontend
    @Override
    public Boolean printLabel(ComplaintDto complaintDto) throws IOException {

        labelPrinter.print(complaintDto);

        return Boolean.TRUE;
    }

    // read the complaint from db
    @Override
    public Boolean printSavedLabel(Long complaintId) throws IOException {

        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(complaintId);
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with " + complaintId + " does not exists."));
        }
        Complaint complaint = complaintOptional.get();
        ComplaintDto complaintDto = new ComplaintDto();
        complaintDto.setQmsNumber(complaint.getQmsNumber());
        complaintDto.setCustomerRefNumber(complaint.getCustomerRefNumber());
        complaintDto.setSerialNumber(complaint.getSerialNumber());
        //complaintDto.setProductGroup(complaint.getProductGroup());       // TODO:  implement product group
        //complaintDto.setArrivedAt(complaint.getArrivedAt().toString());  // TODO: create method what makes a formated date -> 2022.01.07
        complaintDto.setClaimedFault(complaint.getClaimedFault());
        labelPrinter.print(complaintDto);
        return Boolean.TRUE;
    }
}
