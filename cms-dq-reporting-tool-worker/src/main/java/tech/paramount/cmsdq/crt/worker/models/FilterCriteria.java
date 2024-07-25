package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilterCriteria {
    @NotNull
    String server;
    @NotNull
    String site;
    @NotNull
    String stage;
    @NotNull
    String contentType;
    @NotNull
    ReportType reportType;
    Instant lastDateModified;
    AssoicationFilter associatedTo;
    Integer limit;
}
