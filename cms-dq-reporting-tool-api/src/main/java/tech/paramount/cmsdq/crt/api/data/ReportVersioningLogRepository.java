package tech.paramount.cmsdq.crt.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportVersioningLogRepository extends JpaRepository<ContentReportDetails, UUID> {
}
