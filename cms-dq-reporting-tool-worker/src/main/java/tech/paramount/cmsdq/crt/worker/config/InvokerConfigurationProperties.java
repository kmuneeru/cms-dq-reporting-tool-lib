package tech.paramount.cmsdq.crt.worker.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "crt.invoker")
@Getter
@RequiredArgsConstructor
@Validated
public class InvokerConfigurationProperties {
    @NotBlank
    private final String baseUrl;
}