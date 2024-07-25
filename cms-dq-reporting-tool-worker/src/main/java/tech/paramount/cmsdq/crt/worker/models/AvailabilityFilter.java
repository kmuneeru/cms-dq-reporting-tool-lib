package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailabilityFilter {
    @JsonProperty("regions")
    public List<String> regions = new ArrayList<>();
    @JsonProperty("platforms")
    public List<String> platforms = new ArrayList<>();
    @JsonProperty("orgChannels")
    public List<String> orgChannels = new ArrayList<>();
    @JsonProperty("orgSites")
    public List<String> orgSites = new ArrayList<>();
    @JsonProperty("orgPartners")
    public List<String> orgPartners = new ArrayList<>();
}
