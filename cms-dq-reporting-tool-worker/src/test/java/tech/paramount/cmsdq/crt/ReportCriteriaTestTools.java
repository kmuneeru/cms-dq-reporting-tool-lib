package tech.paramount.cmsdq.crt;

import org.springframework.beans.factory.annotation.Autowired;
import tech.paramount.cmsdq.crt.worker.adapter.ReportCriteriaParser;
import tech.paramount.cmsdq.crt.worker.models.Root;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tech.paramount.cmsdq.crt.worker.service.ReportingWorkerService;

import java.io.IOException;
import java.io.InputStream;

public class ReportCriteriaTestTools {

    static final ReportCriteriaParser reportParser = new ReportCriteriaParser();
    static ReportingWorkerService service;

    public static Root generateReportSelectionFromJSON(String filename) {
        try {
            return reportParser.unmarshall(new ReportCriteriaTestTools().getInputStream(filename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getInputStream(String filePath) throws IOException {
        var resourceResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        return resourceResolver.getResource(filePath).getInputStream();
    }

    public static void makeQueryBuilderCall() {
        service.fetchReportToProcess();
    }
}
