package tech.paramount.cmsdq.crt.worker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SelectedFields {
    String name;
    Boolean checked;
    Boolean expanded;
    String contentType;
    List<Field> fields = new ArrayList<>();
    List<References> references = new ArrayList<>();
}
