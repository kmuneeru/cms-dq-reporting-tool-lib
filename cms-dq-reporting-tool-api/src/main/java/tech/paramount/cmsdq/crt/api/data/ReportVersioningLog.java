package tech.paramount.cmsdq.crt.api.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "CRT_VERSION_LOG")
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ReportVersioningLog {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "log_uuid", updatable = false, nullable = false)
    public UUID logId;

    @Column(name = "report_id")
    public String reportId;

    @Column(name = "logged_on")
    public Instant loggedOn;

    @Column(name = "site")
    public String site;

    @Column(name = "stage")
    public String stage;

    @Column(name = "content_type")
    public String contentType;

    @Column(name = "response_time")
    public String responseTime;

    @Column(name = "version_id")
    public String versionId;

    @Column(name = "user_action")
    public String userAction;

    @Column(name = "json_value")
    public String jsonValue;

    @Column(name = "json_query")
    public String jsonQuery;

    @Column(name = "log_type")
    public String logType;

    @Column(name = "user_id")
    public String userId;

    @Column(name = "error_desc")
    public String errorDesc;
}
