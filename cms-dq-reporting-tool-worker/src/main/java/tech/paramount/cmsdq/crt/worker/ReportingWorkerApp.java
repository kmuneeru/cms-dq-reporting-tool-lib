package tech.paramount.cmsdq.crt.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import tech.paramount.cmsdq.crt.worker.app.ReportingWorkerAppConfiguration;
import tech.paramount.cmsdq.crt.worker.service.ReportingWorkerService;

@SpringBootApplication
@ConfigurationPropertiesScan
@Import({ReportingWorkerAppConfiguration.class})
public class ReportingWorkerApp {
    @Autowired
    private ReportingWorkerService worker;

    public static void main(String[] args) {
        SpringApplication.run(ReportingWorkerApp.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startUp() {
        worker.initWorker();
    }
}