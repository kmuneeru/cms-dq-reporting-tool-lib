package tech.paramount.cmsdq.crt.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import tech.paramount.cmsdq.crt.api.dto.CreateReportReponseDTO;
import tech.paramount.cmsdq.crt.api.dto.CreateReportRequestDTO;
import tech.paramount.cmsdq.crt.api.service.ReportingManagementService;
import jakarta.validation.Valid;

import java.io.IOException;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@Validated
@RestController
@RequestMapping(ReportingController.MAPPING)
public class ReportingController {
    public static final String MAPPING = "/api/v1/crt/report";
    private final ReportingManagementService reportingService;

    @Autowired
    public ReportingController(ReportingManagementService reportingService) {
        this.reportingService = reportingService;
    }

    @PostMapping(value = "/create-new", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateReportReponseDTO> create(@Valid @RequestBody String json,
                                                         @RequestParam(name = "reportTitle") String reportTitle,
                                                         @RequestParam(name = "reportDesc", required = false) String reportDesc,
                                                         @RequestParam(name = "userName") String userName,
                                                         @RequestParam(name = "email") String email) {
        if(!isValidJSON(json)) {
            CreateReportReponseDTO error = new CreateReportReponseDTO(true, "Invalid JSON");
            return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
        }
        CreateReportRequestDTO createRequest = new CreateReportRequestDTO();
        createRequest.setReportTitle(reportTitle);
        createRequest.setReportDesc(reportDesc);
        createRequest.setUserName(userName);
        createRequest.setEmail(email);
        createRequest.setJsonValue(json);
        //TODO - validate request params and json
        log.info("Adding new Report: {}", createRequest);
        CreateReportReponseDTO response = reportingService.create(createRequest);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }



    public static boolean isValidJSON(final String json) {
        boolean valid = true;
        try{
            new ObjectMapper().readTree(json);
        } catch(JsonProcessingException e){
            valid = false;
        }
        return valid;
    }
}
