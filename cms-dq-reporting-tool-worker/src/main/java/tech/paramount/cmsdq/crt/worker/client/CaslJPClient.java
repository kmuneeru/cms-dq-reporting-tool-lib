package tech.paramount.cmsdq.crt.worker.client;

import com.paramount.cms.arc.config.CaslHttpConfiguration;
import com.paramount.cms.arc.dto.JSONPathResponseIterator;
import com.paramount.cms.arc.dto.JsonPathServiceDTO;
import com.paramount.cms.arc.exception.CaslJsonPathException;
import com.paramount.cms.arc.service.JsonPathServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import tech.paramount.cmsdq.crt.worker.config.CaslJPConfigurationProperties;
import tech.paramount.cmsdq.crt.worker.dto.JsonPathQueryDTO;

@Slf4j
public class CaslJPClient {
    private CaslHttpConfiguration caslHttpConfiguration;
    private JsonPathServiceDTO jsonPathServiceDTO;

    public CaslJPClient(CaslJPConfigurationProperties config) {
        this.caslHttpConfiguration = new CaslHttpConfiguration();
        caslHttpConfiguration.setEnableHttpSkipCacheParam(config.enableHttpSkipCacheParam());
        caslHttpConfiguration.setQueryRetryMaxAttemptsFor5xxStatus(config.queryRetryMaxAttemptsFor5xxStatus());
        caslHttpConfiguration.setQueryMinBackoffFor5xxStatusInMillis(config.queryMinBackoffFor5xxStatusInMillis());
        caslHttpConfiguration.setQueryMaxPeriodForRetryInMillis(config.queryMaxPeriodForRetryInMillis());
        caslHttpConfiguration.setQueryRetryAfterInMillis(config.queryRetryAfterInMillis());
    }

    public void initCaslJPClient(JsonPathQueryDTO dto) {
        jsonPathServiceDTO = new JsonPathServiceDTO();
        // Setting all the parameters in DTO
        jsonPathServiceDTO.setHostName(dto.getHostName());
        jsonPathServiceDTO.setEndpoint(dto.getEndpoint());
        jsonPathServiceDTO.setIsNoDp(dto.getIsNoDp());
        jsonPathServiceDTO.setRequestParams(dto.getRequestParamMap());
        jsonPathServiceDTO.setQueryParams(dto.getQueryParams());
        jsonPathServiceDTO.setLimit(dto.getLimit());
        jsonPathServiceDTO.setServerProtocol(dto.getServerProtocol());
    }

    public JSONObject fetchJsonPathResponse(JsonPathQueryDTO dto) {
        JsonPathServiceImpl jsonPathService = new JsonPathServiceImpl(caslHttpConfiguration);
        // Main Service Method call
        JSONObject arcJSONResponse = null;
        try {
            arcJSONResponse = jsonPathService.fetchJSONPathResponseFromArc(jsonPathServiceDTO);
        } catch (CaslJsonPathException e) {
            throw new RuntimeException(e);
        }
        return arcJSONResponse;
    }

    public JSONPathResponseIterator fetchJsonPathResponseIterator(JsonPathQueryDTO dto) {
        JsonPathServiceImpl jsonPathService = new JsonPathServiceImpl(caslHttpConfiguration);
        // Main Service Method call
        JSONPathResponseIterator responseIterator = null;
        try {
            responseIterator = jsonPathService.fetchJSONPathIteratorFromArc(jsonPathServiceDTO);
        } catch (CaslJsonPathException e) {
            throw new RuntimeException(e);
        }
        return responseIterator;
    }
}
