package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Field {
    String name;
    Boolean checked;
    @JsonIgnore
    String contentType;
    @JsonIgnore
    Boolean expanded;
}
