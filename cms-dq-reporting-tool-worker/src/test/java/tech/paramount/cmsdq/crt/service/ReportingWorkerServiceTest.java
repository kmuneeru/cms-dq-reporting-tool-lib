package tech.paramount.cmsdq.crt.service;

import org.junit.jupiter.api.Test;
import tech.paramount.cmsdq.crt.ReportCriteriaTestTools;

public class ReportingWorkerServiceTest {
    //public final String FILE_NAME = "report-criteria-contract.json";
    //public final String FILE_NAME = "report-criteria_franchise.json";
    //public final String FILE_NAME = "report-criteria_series.json";
    public final String FILE_NAME = "report-criteria_co.json";

    //@Test
    void shouldprintJPQuery() {
        ReportCriteriaTestTools.makeQueryBuilderCall();
    }
}
