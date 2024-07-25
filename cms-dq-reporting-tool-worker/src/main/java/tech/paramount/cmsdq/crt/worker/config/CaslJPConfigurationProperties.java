package tech.paramount.cmsdq.crt.worker.config;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "casl.config")
public record CaslJPConfigurationProperties (
    @NotNull
    Boolean enableHttpSkipCacheParam,

    @Min(1)
    Integer queryMinBackoffFor5xxStatusInMillis,

    @Min(1)
    Integer queryMaxPeriodForRetryInMillis,

    @Min(1)
    @Max(5)
    int queryRetryMaxAttemptsFor5xxStatus,

    @Min(1)
    Long queryRetryAfterInMillis
) {
        public CaslJPConfigurationProperties toConfig() {
            return new CaslJPConfigurationProperties(
                    enableHttpSkipCacheParam,
                    queryMinBackoffFor5xxStatusInMillis,
                    queryMaxPeriodForRetryInMillis,
                    queryRetryMaxAttemptsFor5xxStatus,
                    queryRetryAfterInMillis
            );
        }
}
