package tech.paramount.cmsdq.crt.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentReportDetailsRepository extends JpaRepository<ContentReportDetails, Long> {
}
