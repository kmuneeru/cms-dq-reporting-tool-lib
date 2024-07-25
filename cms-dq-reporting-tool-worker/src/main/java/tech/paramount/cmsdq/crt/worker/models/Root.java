package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties
public class Root {
    ReportSelectionCriteria selectionCriteria;
    @JsonIgnore
    String ui;
}
