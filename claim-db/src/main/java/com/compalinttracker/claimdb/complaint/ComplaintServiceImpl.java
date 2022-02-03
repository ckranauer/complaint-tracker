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
        log.info("Saving new complaint: {}", complaintDto.getQmsNumber());
        log.info("Responsible: {}", complaintDto.getResponsible());
        // Check if the complaint is already exists, if the serial number is already used then the complaint is already exist
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintBySerNo(complaintDto.getSerialNumber());

        if(complaintOptional.isPresent()){
            throw new IllegalStateException(String.format("Complaint with "+ complaintDto.getSerialNumber() + " serial number is already exists"));
        }
        // TODO: from the frontend there was posible to complaint data with null value in SNR, QMS. Solve this issue
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

        if (complaintDto.getProductInfo() != null){
            complaint.setProductInfo(complaintDto.getProductInfo());
        }




        System.out.println("responsible"+ complaintDto.getResponsible());
        if(complaintDto.getResponsible() != null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(complaintDto.getResponsible());
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ complaintDto.getResponsible()+" is not exist");
            }
            UserProfile responsible = userProfileOptional.get();
            responsible.addComplaint(complaint);
            complaint.setResponsible(responsible);
        }
        Analysis analysis = new Analysis();
        analysis.setComplaint(complaint);
        complaint.setAnalysis(analysis);

        complaintRepository.save(complaint);
        complaintRepository.findAllComplaintAnalysis().stream().forEach(System.out::println);
        return complaintRepository.findAllComplaintAnalysis();
    }

    @Override
    public Collection<ComplaintAnalysisDto> list(int limit, int page) {   // TODO: change it to complaintDto
        log.info("Fetching all complaints");                    // TODO: create a query which collect only the necessary datas
        // TODO: create the complaintDTO what is in the frontend

        // TODO: create  A projection interface to retrieve a subset of attributes
        // TODO: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
        ;
        Collection<ComplaintAnalysisDto> complaints = new ArrayList<ComplaintAnalysisDto>();
       // complaintRepository.findAll(PageRequest.of(page,limit)).toList();
        // TODO : stream

        List<ComplaintAnalysisDto> rawComplaints = complaintRepository.findAllComplaintAnalysis();

        return rawComplaints;
    }

    @Override
    public ComplaintAnalysisDto get(Long id) {
        log.info("Fetching complaint by id: {}", id);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);
        Optional<ComplaintAnalysisDto> complaintAnalysisDtoOptional = complaintRepository.findComplaintAnalysisById(id);
        if(complaintAnalysisDtoOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint with id: "+ id + " is not exist."));
        }
        return complaintAnalysisDtoOptional.get();
    }

    @Override
    public Collection<ComplaintAnalysisDto> update(Long id, ComplaintUpdateDto complaintDto) {
        log.info("Update complaint: {}", id);
        Optional<Complaint> complaintOptional = complaintRepository.findComplaintById(id);
        if(complaintOptional.isEmpty()){
            throw new IllegalStateException(String.format("Complaint does not exist."));
        }
        Complaint actualComplaint = complaintOptional.get();

        // Serial number cannot be null
        if(complaintDto.getSerialNumber() == null){
            throw new IllegalStateException("Serial number cannot be null.");
        }

        // Check if serial number is already exist
        Optional<Complaint> serNoComplaintOptional = complaintRepository.findComplaintBySerNo(complaintDto.getSerialNumber());
        if(serNoComplaintOptional.isPresent()  ){
            // If yes then check if it belongs to the same complaint what we want to update,
            if(serNoComplaintOptional.get().getId() !=  id){
                // if it belongs to another claim
                throw new IllegalStateException(String.format("Complaint with serial number: " + complaintDto.getSerialNumber() + " is already exist."));
            }else{
                // If yes, save
                actualComplaint.setSerialNumber(complaintDto.getSerialNumber());
            }
        }else{
            // if not present then save
            actualComplaint.setSerialNumber(complaintDto.getSerialNumber());
        }


        if(complaintDto.getQmsNumber() != null){
            Optional<Complaint> qmsNoComplaintOptional = complaintRepository.findComplaintByQmsNo(complaintDto.getQmsNumber());
            if(qmsNoComplaintOptional.isPresent()){
                if(qmsNoComplaintOptional.get().getId() != id){
                    throw new IllegalStateException(String.format("Complaint with QMS number: "+ complaintDto.getQmsNumber() + " is already exist."));
                }else{
                    actualComplaint.setQmsNumber(complaintDto.getQmsNumber());
                }
            }else{
                actualComplaint.setQmsNumber(complaintDto.getQmsNumber());
            }
        }

        actualComplaint.setCustomerRefNumber(complaintDto.getCustomerRefNumber());
        actualComplaint.setClaimedFault(complaintDto.getClaimedFault());

        // TODO: solve the date conversion
        if (complaintDto.getArrivedAt() != null) {
            if(complaintDto.getArrivedAt().length() > 0){
                int year = Integer.parseInt(complaintDto.getArrivedAt().substring(0,4)); // TODO: create method in the class
                int month = Integer.parseInt(complaintDto.getArrivedAt().substring(5,7));
                int day = Integer.parseInt(complaintDto.getArrivedAt().substring(8,10));
                LocalDate date = LocalDate.of(year, Month.of(month), day);
                actualComplaint.setArrivedAt(date );
            }

        }

        if(complaintDto.getResponsible() != null) {
            log.info("User profile before read");
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(complaintDto.getResponsible());
            log.info("User profile after read the optional");
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ complaintDto.getResponsible()+" is not exist");
            }
            UserProfile responsible = userProfileOptional.get();
            log.info("got the user profile from the optional");
            responsible.addComplaint(actualComplaint);
            log.info("complaint added to user_profile(responsible) set");
            actualComplaint.setResponsible(responsible);
            log.info("responsible set to complaint");
        }

        if(complaintDto.getIsPrio() != null){
            actualComplaint.setPrio(complaintDto.getIsPrio());
        }

        log.info("UPDATE: Product group: " + complaintDto.getProductInfo());
        if (complaintDto.getProductInfo() != null){
            actualComplaint.setProductInfo(complaintDto.getProductInfo());
        }
        log.info("Product group is updated");


        Analysis analysis = actualComplaint.getAnalysis();
        log.info("analysis is read");
        analysis.setBarcodes(complaintDto.getBarcodes());
        log.info("barcodes is updated");
        analysis.setLifecycleInfo(complaintDto.getLifecycleInfo());
        log.info("lifecycle is updated");
        if(complaintDto.getFaultVerification() != null){
            analysis.setFaultVerification(complaintDto.getFaultVerification());
        }

        log.info("fault verif updated");
        analysis.setVisualAnalysis(complaintDto.getVisualAnalysis());
        log.info("VA is updated");
        analysis.setElectricalAnalysis(complaintDto.getElectricalAnalysis());
        log.info("EA is updated");
        analysis.setConclusion(complaintDto.getConclusion());
        log.info("Conclusion is updated");

        if (complaintDto.getAnalysisStartedAt() != null) {
            if(complaintDto.getAnalysisStartedAt().length() > 0){
                int year = Integer.parseInt(complaintDto.getAnalysisStartedAt().substring(0,4)); // TODO: create method in the class
                int month = Integer.parseInt(complaintDto.getAnalysisStartedAt().substring(5,7));
                int day = Integer.parseInt(complaintDto.getAnalysisStartedAt().substring(8,10));
                LocalDate date = LocalDate.of(year, Month.of(month), day);
                analysis.setAnalysisStartedAt(date);
            }
        }
        log.info("Analysis started is updated");

        if (complaintDto.getAnalysisEndedAt() != null) {
            if(complaintDto.getAnalysisEndedAt().length() > 0){
                int year = Integer.parseInt(complaintDto.getAnalysisEndedAt().substring(0,4)); // TODO: create method in the class
                int month = Integer.parseInt(complaintDto.getAnalysisEndedAt().substring(5,7));
                int day = Integer.parseInt(complaintDto.getAnalysisEndedAt().substring(8,10));
                LocalDate date = LocalDate.of(year, Month.of(month), day);
                analysis.setAnalysisEndedAt(date);
            }
        }

        log.info("Analysis ended is updated");

        if(complaintDto.getAnalyzedBy() != null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(complaintDto.getAnalyzedBy());
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ complaintDto.getAnalyzedBy()+" is not exist");
            }
            UserProfile analyzedBy = userProfileOptional.get();
            analyzedBy.addAnalysis(analysis);
            analysis.setAnalyzedBy(analyzedBy);
        }
        log.info("AnalyzedBy is updated");

        actualComplaint.setAnalysis(analysis);
        complaintRepository.save(actualComplaint);


        /*
        if(complaintAnalysisDto.getIsPrio() != null){
            actualComplaint.setPrio(complaintDto.getIsPrio());
        }
*/
        // TODO: implement it ??
        //actualComplaint.setAnalysis(complaintDto.getAnalysis());
        log.info("Update is finished");
        return complaintRepository.findAllComplaintAnalysis();
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

    @Override
    public Analysis addAnalysis(AnalysisDto analysisDto) {
        log.info("Add analysis to the complaint.");

        // in the analysis repo check if another analysis already contains this complaint id
        // it means that the complaint already has analysis, so it is not possible to add more
        // it is possible to update but in another method
        Optional<Analysis> analysisOptional = analysisRepository.findById(analysisDto.getComplaintId());
        if(analysisOptional.isPresent()){
            throw new IllegalStateException("Complaint with id: "+ analysisDto.getComplaintId()+ " already has analysis.");
        }

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
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(analysisDto.getAnalyzedBy());
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ analysisDto.getAnalyzedBy()+" is not exist");
            }
            UserProfile user = userProfileOptional.get();
            user.addAnalysis(analysis);
            analysis.setAnalyzedBy(user);
        }
        complaint.setAnalysis(analysis);
        return analysisRepository.save(analysis);
    }

    @Override
    public Analysis getAnalysis(Long id) {
        log.info("Fetching analysis by id: {}", id);
        Optional<Analysis> analysisOptional = analysisRepository.findAnalysisByComplaintId(id);
        if(analysisOptional.isEmpty()){
            throw new IllegalStateException(String.format("Analysis with id: "+ id + " is not exist."));
        }
        return analysisOptional.get();
    }

    @Override
    public Boolean deleteAnalysis(Long complaintId) {
        log.info("Deleting analysis by id: {}", complaintId);
        Complaint complaint = complaintRepository.getById(complaintId);
        analysisRepository.deleteAnalysisByComplaintId(complaintId);
        return Boolean.TRUE;
    }

    @Override
    public Analysis update(Long complaintId, AnalysisDto analysisDto) {

        Optional<Analysis> analysisOptional = analysisRepository.findById(complaintId);
        Analysis analysis;
        if(analysisOptional.isEmpty()){
            analysis = new Analysis();
        }

        analysis = analysisOptional.get();
        analysis.setBarcodes(analysisDto.getBarcodes());
        analysis.setLifecycleInfo(analysisDto.getLifecycleInfo());
        analysis.setVisualAnalysis(analysisDto.getVisualAnalysis());
        analysis.setElectricalAnalysis(analysisDto.getElectricalAnalysis());
        analysis.setConclusion(analysisDto.getConclusion());
        analysis.setAnalysisEndedAt(analysisDto.getAnalysisEndedAt());
        analysis.setAnalysisStartedAt(analysisDto.getAnalysisStartedAt());
        if(analysisDto.getAnalyzedBy() != null){
            Optional<UserProfile> userProfileOptional = userProfileRepository.findUserProfileById(analysisDto.getAnalyzedBy());
            if(userProfileOptional.isEmpty()){
                throw new IllegalStateException("User with id: "+ analysisDto.getAnalyzedBy()+" is not exist");
            }
            UserProfile analyzedBy = userProfileOptional.get();
            analyzedBy.addAnalysis(analysis);
            analysis.setAnalyzedBy(analyzedBy);
        }
        return analysisRepository.save(analysis);
    }

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
        complaintDto.setArrivedAt(complaint.getArrivedAt().toString());  // TODO: create method what makes a formated date -> 2022.01.07
        complaintDto.setClaimedFault(complaint.getClaimedFault());
        labelPrinter.print(complaintDto);
        return Boolean.TRUE;
    }
}
