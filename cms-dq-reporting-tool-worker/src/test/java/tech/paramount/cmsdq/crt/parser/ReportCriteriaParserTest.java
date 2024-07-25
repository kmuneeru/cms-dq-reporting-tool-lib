package tech.paramount.cmsdq.crt.parser;

import tech.paramount.cmsdq.crt.ReportCriteriaTestTools;
import tech.paramount.cmsdq.crt.worker.models.Root;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReportCriteriaParserTest {

    @Test
    void shouldUnmarshalNewValidFeed() {
        Root root = ReportCriteriaTestTools.generateReportSelectionFromJSON("report-criteria-contract.json");
        assertNotNull(root);

        var selectionCriteria = root.getSelectionCriteria();
        assertNotNull(selectionCriteria);

        var reportFilters = selectionCriteria.getReportFilters();
        assertNotNull(reportFilters);

        var availabilityFilters = selectionCriteria.getAvailabilityFilters();
        assertNotNull(availabilityFilters);

        var selectedFields = selectionCriteria.getSelectedFields();
        assertNotNull(selectedFields);
        assertThat(selectedFields.getFields()).isNotEmpty();
    }

    @Test
    void shouldUnmarshalFranchiseFeed() {
        Root root = ReportCriteriaTestTools.generateReportSelectionFromJSON("report-criteria_franchise.json");

        assertNotNull(root);

        var selectionCriteria = root.getSelectionCriteria();
        assertNotNull(selectionCriteria);

        var reportFilters = selectionCriteria.getReportFilters();
        assertNotNull(reportFilters);

        var availabilityFilters = selectionCriteria.getAvailabilityFilters();
        assertNotNull(availabilityFilters);

        var selectedFields = selectionCriteria.getSelectedFields();
        assertNotNull(selectedFields);
        assertThat(selectedFields.getFields()).isNotEmpty();
    }

    @Test
    void shouldUnmarshalNewUIFeed() {
        Root root = ReportCriteriaTestTools.generateReportSelectionFromJSON("report-criteria_co.json");
        assertNotNull(root);

        var selectionCriteria = root.getSelectionCriteria();
        assertNotNull(selectionCriteria);

        var reportFilters = selectionCriteria.getReportFilters();
        assertNotNull(reportFilters);

        var availabilityFilters = selectionCriteria.getAvailabilityFilters();
        assertNotNull(availabilityFilters);

        var selectedFields = selectionCriteria.getSelectedFields();
        assertNotNull(selectedFields);
        assertThat(selectedFields.getFields()).isNotEmpty();
    }
}
