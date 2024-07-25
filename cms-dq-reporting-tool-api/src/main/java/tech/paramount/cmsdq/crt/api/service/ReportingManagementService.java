package tech.paramount.cmsdq.crt.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.paramount.cmsdq.crt.api.dto.CreateReportReponseDTO;
import tech.paramount.cmsdq.crt.api.dto.CreateReportRequestDTO;
import tech.paramount.cmsdq.crt.api.data.ContentReportDetails;
import tech.paramount.cmsdq.crt.api.data.ContentReportDetailsRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

@Slf4j
@Service
public class ReportingManagementService {
    private final ContentReportDetailsRepository reportDetailsRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ReportingManagementService(ContentReportDetailsRepository repository) {
        this.reportDetailsRepository = repository;
        configureMapper();
    }

    public CreateReportReponseDTO create(CreateReportRequestDTO reportRequest) {
        log.debug("Create New Report: {}", reportRequest);
        var requestEntity = mapAll(reportRequest);
        log.debug("request entity {}",requestEntity);
        var responseEntity = reportDetailsRepository.saveAndFlush(requestEntity);
        log.info("New report request saved to database with id {}", responseEntity.getReportId());
        return CreateReportReponseDTO.of(responseEntity);
    }

    private ContentReportDetails mapAll(final CreateReportRequestDTO entity) {
        return modelMapper.map(entity, ContentReportDetails.class);
    }

    private void configureMapper() {
        modelMapper.addMappings(new PropertyMap<CreateReportRequestDTO, ContentReportDetails>() {
            @Override
            protected void configure() {
                skip(destination.getReportId());
            }
        });
    }
}
