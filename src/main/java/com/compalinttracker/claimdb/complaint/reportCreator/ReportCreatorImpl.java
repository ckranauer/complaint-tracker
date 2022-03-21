package com.compalinttracker.claimdb.complaint.reportCreator;

import com.compalinttracker.claimdb.analysis.Analysis;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.docx4j.Docx4J;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportCreatorImpl implements ReportCreator {

    // TODO: save the report to AWS E3 bitbucket
    // TODO: give back the report and send to the frontend

    @Override
    public void create(Analysis analysis) throws Exception {

        InputStream templateInputStream = this.getClass().getClassLoader().getResourceAsStream("report template.docx");
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
        MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
        VariablePrepare.prepare(wordMLPackage);

        // Fill the template
        HashMap<String, String> variables = addVariables(analysis);
        documentPart.variableReplace(variables);

        File exportFile = new File("Analysis Report - " + analysis.getComplaint().getQmsNumber() + ".docx");
        wordMLPackage.save(exportFile);

    }

    private HashMap<String, String> addVariables(Analysis analysis) {
        HashMap<String, String> variables = new HashMap<>();

        variables.put("qmsNumber", analysis.getComplaint().getQmsNumber());
        variables.put("customerRefNumber", analysis.getComplaint().getCustomerRefNumber());
        variables.put("partDescription", "Default");
        variables.put("failureDescription", analysis.getComplaint().getClaimedFault());
        variables.put("arrivedAt", analysis.getComplaint().getArrivedAt().toString().substring(0, 10).replace("-", "."));
        variables.put("analysisEndedAt", analysis.getAnalysisEndedAt().toString().substring(0, 10).replace("-", "."));
        if (analysis.getAnalyzedBy() != null) {
            variables.put("analyzedBy", analysis.getAnalyzedBy().getFirstName() + " " + analysis.getAnalyzedBy().getLastName());
        } else {
            variables.put("analyzedBy", "");
        }
        variables.put("productBarcodes", analysis.getBarcodes());
        variables.put("lifecycleInfo", analysis.getLifecycleInfo());
        variables.put("faultVerification", analysis.isConfirmed());
        variables.put("visualAnalysis", analysis.getVisualAnalysis());
        variables.put("electricalAnalysis", analysis.getElectricalAnalysis());
        variables.put("conclusion", analysis.getConclusion());

        return variables;
    }
}
