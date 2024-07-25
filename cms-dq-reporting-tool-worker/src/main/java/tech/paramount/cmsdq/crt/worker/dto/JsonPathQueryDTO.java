package tech.paramount.cmsdq.crt.worker.dto;

import lombok.Data;

import java.util.Map;

@Data
public class JsonPathQueryDTO {
    String hostName;
    String endpoint;
    Boolean isNoDp;
    Integer limit;
    String serverProtocol;
    Map<String, String> queryParams;
    Map<String, String> requestParamMap;
}
