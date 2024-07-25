package tech.paramount.cmsdq.crt.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tech.paramount.cmsdq.crt.api.domain.ReportRunStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateReportRequestDTO {
    @NotBlank
    public String reportTitle;

    @NotNull
    public String reportDesc;

    @NotBlank
    public String userName;

    @NotBlank
    public String email;

    //@NotBlank
    public String jsonValue;

    public String runStatus = ReportRunStatus.PENDING;
}
