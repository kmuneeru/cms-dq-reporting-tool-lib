package tech.paramount.cmsdq.crt.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.paramount.cmsdq.crt.api.data.ContentReportDetails;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CreateReportReponseDTO extends CreateReportRequestDTO {
    @NotBlank
    public Long reportId;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
    private boolean isError;
    private String errorMessage;
    public CreateReportReponseDTO(boolean isError, String errorMessage) {
        this.isError = isError;
        this.errorMessage = errorMessage;
    }

    private CreateReportReponseDTO(Long id, String reportTitle, String reportDesc, String userName, String email, String jsonValue, String status) {
        super(reportTitle, reportDesc, userName, email, jsonValue, status);
        this.reportId = id;
        this.dateCreated = LocalDateTime.now();
        this.dateUpdated = LocalDateTime.now();
    }

    public static CreateReportReponseDTO of(ContentReportDetails report) {
        return new CreateReportReponseDTO(report.getReportId(), report.getReportTitle(), report.getReportDesc(), report.getUserName(),
                report.getEmail(), report.getJsonValue(), report.getRunStatus());
    }
}
