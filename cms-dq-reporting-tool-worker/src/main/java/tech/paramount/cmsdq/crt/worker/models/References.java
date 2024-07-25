package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class References {
    String name;
    Boolean checked;
    Boolean expanded;
    String contentType;
    List<Field> fields = new ArrayList<>();
    @JsonProperty("references")
    List<References> subreferences = new ArrayList<>();
}
