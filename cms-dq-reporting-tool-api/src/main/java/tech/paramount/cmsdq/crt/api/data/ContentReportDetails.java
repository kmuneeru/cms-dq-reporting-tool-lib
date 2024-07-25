package tech.paramount.cmsdq.crt.api.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "CRT_REPORT_DETAILS")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ContentReportDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    public Long reportId;

    @Column(name = "report_name")
    public String reportTitle;

    @Column(name = "report_desc")
    public String reportDesc;

    @Column(name = "last_run_date")
    public Instant lastRunDate;

    @Column(name = "user_id")
    public String userName;

    @Column(name = "email_id")
    public String email;

    @Column(name = "json_value")
    public String jsonValue;

    @Column(name = "percentage")
    public Integer percentage;

    @Column(name = "status")
    public String runStatus;

    @Column(name = "error_count")
    public Integer errorCount;

    @Column(name = "is_rerun")
    public boolean rerunFlag;

    /*@Column(name = "date_created")
    public LocalDateTime dateCreated;

    @Column(name = "date_modified")
    public LocalDateTime dateUpdated;*/
}
