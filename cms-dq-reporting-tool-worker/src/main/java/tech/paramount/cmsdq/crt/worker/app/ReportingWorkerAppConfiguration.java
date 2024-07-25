package tech.paramount.cmsdq.crt.worker.app;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tech.paramount.cmsdq.crt.worker.client.CaslJPClient;
import tech.paramount.cmsdq.crt.worker.client.ReportingInvokerClient;
import tech.paramount.cmsdq.crt.worker.config.CaslJPConfiguration;
import tech.paramount.cmsdq.crt.worker.config.InvokerConfigurationProperties;
import tech.paramount.cmsdq.crt.worker.service.jsonpath.ContentProvider;

@Import({
        InvokerConfigurationProperties.class,
        CaslJPConfiguration.class
})
@RequiredArgsConstructor
public class ReportingWorkerAppConfiguration {

    @Bean
    public ReportingInvokerClient reportingInvokerClient(InvokerConfigurationProperties properties) {
        return new ReportingInvokerClient(properties);
    }

    @Bean
    public ContentProvider reportContentProvider(CaslJPClient caslJPClient) {
        return new ContentProvider(caslJPClient);
    }
}
