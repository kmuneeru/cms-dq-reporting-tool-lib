package tech.paramount.cmsdq.crt.worker.adapter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tech.paramount.cmsdq.crt.worker.builder.ReportConverterBuilder;
import tech.paramount.cmsdq.crt.worker.dto.JsonPathQueryDTO;
import tech.paramount.cmsdq.crt.worker.models.FilterCriteria;
import tech.paramount.cmsdq.crt.worker.models.ReportSelectionCriteria;
import tech.paramount.cmsdq.crt.worker.models.Root;
import tech.paramount.cmsdq.crt.worker.models.SelectedFields;
import tech.paramount.cmsdq.crt.worker.util.HelperUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.DATEFORMAT_UTC;

@Slf4j
public class ReportConverter {

    private static String SERVER_PROTOCOL = "http";
    private static Integer LIMIT_DEFAULT = 10;

    public JsonPathQueryDTO convert(Root root) {
        ReportSelectionCriteria reportSelectionCriteria = root.getSelectionCriteria();
        var jpQueryDto = new JsonPathQueryDTO();
        if(null == reportSelectionCriteria) {
            log.error("Could not convert report selection json. ReportSelectionCriteria is null");
            return null;
        }

        FilterCriteria filterCriteria = reportSelectionCriteria.getReportFilters();
        //TODO - Validate each fields
        if(null == filterCriteria) {
            log.error("Could not convert report selection json. FilterCriteria is null");
            return null;
        }

        SelectedFields selectedFields = reportSelectionCriteria.getSelectedFields();
        if(null == selectedFields) {
            log.error("Could not convert report selection json. SelectedFields is null");
        }
        if(CollectionUtils.isEmpty(selectedFields.getFields())) {
            log.error("Could not convert report selection json. SelectedFields fields list is empty");
            return null;
        }

        jpQueryDto.setHostName(extractHostname(filterCriteria.getServer()));
        jpQueryDto.setEndpoint((filterCriteria.getSite()));
        jpQueryDto.setIsNoDp(!filterCriteria.getReportType().getApplyDP());
        jpQueryDto.setLimit(getReportLimit(filterCriteria));
        jpQueryDto.setServerProtocol(SERVER_PROTOCOL);

        jpQueryDto.setRequestParamMap( convertToRequestParamsMap(filterCriteria.getStage()) );
        jpQueryDto.setQueryParams( convertToQueryParamsMap(reportSelectionCriteria) );

        return jpQueryDto;
    }

    private Integer getReportLimit(FilterCriteria filterCriteria) {
        return filterCriteria.getLimit()==null ? LIMIT_DEFAULT: filterCriteria.getLimit();
    }

    private Map<String, String> convertToRequestParamsMap(String requestParams) {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", DATEFORMAT_UTC);
        requestedParamsMap.put("stage", requestParams);
        requestedParamsMap.put("omitSummary", Boolean.toString(true));
        return requestedParamsMap;
    }

    private Map<String, String> convertToQueryParamsMap(ReportSelectionCriteria reportCriteria) {
        Map<String, String> queryParamsMap = new HashMap<>();
        ReportConverterBuilder builder = new ReportConverterBuilder();
        String selectQString = builder.buildSelectString(reportCriteria.getSelectedFields());
        String whereQString = builder.buildWhereString(reportCriteria.reportFilters.getContentType());

        queryParamsMap.put("select", selectQString);
        queryParamsMap.put("where", whereQString);
        queryParamsMap.put("order", "\"DateCreatedAsc\"");
        queryParamsMap.put("rows", getReportLimit(reportCriteria.getReportFilters()).toString());
        queryParamsMap.put("omitNumFound", Boolean.toString(false));
        return queryParamsMap;
    }

    private String extractHostname(String arcServer) {
        try {
            URI uri = new URI(arcServer);
            SERVER_PROTOCOL = uri.getScheme();
            return uri.getHost();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
