package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportType {
    Boolean applyDP;
    Boolean includeDraft;
}
