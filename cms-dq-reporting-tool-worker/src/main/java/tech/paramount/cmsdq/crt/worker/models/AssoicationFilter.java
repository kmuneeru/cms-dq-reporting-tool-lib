package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssoicationFilter {
    /*@JsonProperty("elements")
    public List<String> ids = new ArrayList<>();
    @JsonProperty("relationType")
    public String relationType;*/
    @JsonProperty("or")
    List<String> orList = new ArrayList<>();
    @JsonProperty("and")
    List<String> andList = new ArrayList<>();
}
