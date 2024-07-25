package tech.paramount.cmsdq.crt.worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.paramount.cmsdq.crt.worker.service.jsonpath.ContentProvider;
import tech.paramount.cmsdq.crt.worker.dto.JsonPathQueryDTO;

@Service
public class ReportContentService {

    @Autowired
    private ContentProvider reportContentProvider ;

    public void processReportContent(JsonPathQueryDTO dto) {
        reportContentProvider.processReportResponse(dto);
    }
}
