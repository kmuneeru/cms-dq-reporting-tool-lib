package tech.paramount.cmsdq.crt.worker.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriBuilder;
import tech.paramount.cmsdq.crt.worker.adapter.ReportConverter;
import tech.paramount.cmsdq.crt.worker.adapter.ReportCriteriaParser;
import tech.paramount.cmsdq.crt.worker.builder.JPQueryBuilder;
import tech.paramount.cmsdq.crt.worker.dto.JsonPathQueryDTO;
import tech.paramount.cmsdq.crt.worker.models.Root;
import tech.paramount.cmsdq.crt.worker.service.helper.ReportHelper;
import tech.paramount.cmsdq.crt.worker.util.JsonPathException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Service
public class ReportingWorkerService {
    //public final String FILE_NAME = "report-criteria-contract.json";
    //public final String FILE_NAME = "report-criteria_franchise.json";
    //public final String FILE_NAME = "report-criteria_series.json";
    public final String FILE_NAME = "report-criteria_co1.json";

    private final ReportingInvokerService invokerService;
    private final ReportContentService contentService ;
    @Autowired
    public ReportingWorkerService(ReportingInvokerService invokerService, ReportContentService contentService) {
        this.invokerService = invokerService;
        this.contentService = contentService;
    }

    public void initWorker() {
        invokerService.fetchReportsToProcess();
    }

    public void fetchReportToProcess() {
        InputStream inputStream = null;
        try {
            inputStream = new ReportHelper().getInputStream(FILE_NAME);
            Root rawReport = new ReportCriteriaParser().unmarshall(inputStream);
            JsonPathQueryDTO dto = convertReportCriteria(rawReport);
            JPQueryBuilder queryBuilder = new JPQueryBuilder(dto.getHostName(),dto.getEndpoint(),dto.getLimit(),dto.getIsNoDp(),dto.getQueryParams(),dto.getRequestParamMap(),"http");
            try {
                UriBuilder uriBuilder = queryBuilder.buildJSONPathURI(dto.getHostName(),dto.getEndpoint(),dto.getIsNoDp(),dto.getQueryParams(),false);
                String decoded = URLDecoder.decode(uriBuilder.toUriString(), StandardCharsets.UTF_8);
                log.info(decoded);
                //TODO - Save the JP Query and JSON Value to the database in vdrsion log table for this report id for debug purpose.
            } catch (JsonPathException e) {
                throw new RuntimeException(e);
            }
            contentService.processReportContent(dto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public JsonPathQueryDTO convertReportCriteria(Root rawReport) {
        ReportConverter jpConverter = new ReportConverter();
        return jpConverter.convert(rawReport);
    }
}
