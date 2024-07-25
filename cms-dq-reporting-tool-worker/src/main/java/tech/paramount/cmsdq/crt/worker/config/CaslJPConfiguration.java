package tech.paramount.cmsdq.crt.worker.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import tech.paramount.cmsdq.crt.worker.client.CaslJPClient;

@RequiredArgsConstructor
@EnableConfigurationProperties(CaslJPConfigurationProperties.class)
public class CaslJPConfiguration {
    private final CaslJPConfigurationProperties properties;

    @Bean
    public CaslJPClient caslJPClient() {
        return new CaslJPClient(properties.toConfig());
    }
}
