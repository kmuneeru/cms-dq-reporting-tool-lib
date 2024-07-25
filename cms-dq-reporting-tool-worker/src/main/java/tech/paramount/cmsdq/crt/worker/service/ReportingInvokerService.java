package tech.paramount.cmsdq.crt.worker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.paramount.cmsdq.crt.worker.client.ReportingInvokerClient;

@Service
public class ReportingInvokerService {
    private final ReportingInvokerClient invokerClient;

    @Autowired
    public ReportingInvokerService(ReportingInvokerClient invokerClient) {
        this.invokerClient = invokerClient;
    }

    public void fetchReportsToProcess() {
        invokerClient.
    }

    public void fetchReportMetadata() {

    }
}
