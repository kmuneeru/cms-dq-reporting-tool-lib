package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportSelectionCriteria {
    @JsonProperty("filters")
    public FilterCriteria reportFilters;

    @JsonProperty("availabilityFilter")
    public AvailabilityFilter availabilityFilters;

    @JsonProperty("selectedFields")
    public SelectedFields selectedFields;
}
