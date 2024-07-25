package tech.paramount.cmsdq.crt.worker.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tech.paramount.cmsdq.crt.worker.config.InvokerConfigurationProperties;

@Slf4j
@RequiredArgsConstructor
public class ReportingInvokerClient {
    private final InvokerConfigurationProperties properties;
}
