package tech.paramount.cmsdq.crt.worker.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import tech.paramount.cmsdq.crt.worker.util.JsonPathConstants;
import tech.paramount.cmsdq.crt.worker.util.JsonPathException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JPQueryBuilder {
    private String hostName;

    private String endpoint;

    @Builder.Default
    private Integer limit = Integer.MAX_VALUE;

    @Builder.Default
    private Boolean isNoDp = false;

    private Map<String, String> queryParams;

    private Map<String, String> requestParams;

    @Builder.Default
    private String serverProtocol = JsonPathConstants.HTTP;

    public UriBuilder buildJSONPathURI(String hostName, String endpoint, Boolean isNoDp, Map<String, String> queryParams, Boolean enableHttpSkipCacheParam) throws JsonPathException {
        UriBuilder uriBuilder = buildRequestParamMap(hostName, endpoint, isNoDp, enableHttpSkipCacheParam);
        if (queryParams != null && !queryParams.isEmpty()) {
            Map<String, String> query = addDefaultQueryParamVariables(queryParams);
            String finalQuery = constructQueryParamString(query);
            uriBuilder.queryParam(JsonPathConstants.QUERY_PARAM, URLEncoder
                    .encode(finalQuery, StandardCharsets.UTF_8));
        }
        return uriBuilder;
    }


    private UriBuilder buildRequestParamMap(String hostName, String endpoint, Boolean isNoDp, Boolean enableHttpSkipCacheParam) throws JsonPathException {
        // Converting request params Map to String
        String requestParams = constructRequestParamString(getRequestParams());

        Map<String, String> requestParamMap = Stream.of(requestParams.split(JsonPathConstants.AMPERSAND))
                .collect(Collectors
                        .toConcurrentMap(requestParam ->
                                requestParam.split(JsonPathConstants.EQUALS_TO)[0], requestParam -> requestParam.split(JsonPathConstants.EQUALS_TO)[1]));

        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(getServerProtocol() + JsonPathConstants.HTTP_SUFFIX + hostName);
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        UriBuilder uriBuilder = isNoDp ? uriBuilderFactory
                .builder()
                .pathSegment(JsonPathConstants.PATH_NAME_JP, JsonPathConstants.JP_NODP_QUERY, endpoint)
                : uriBuilderFactory
                .builder()
                .pathSegment(JsonPathConstants.PATH_NAME_JP, endpoint);

        if (enableHttpSkipCacheParam) {
            requestParamMap.put(JsonPathConstants.JP_TIMESTAMP, String.valueOf(Instant.now()));
        }
        requestParamMap.forEach(uriBuilder::queryParam);
        return uriBuilder;
    }

    /**
     * This method constructs the query param string
     *
     * @param queryParams Map of Query Params
     * @return query param string
     */
    private String constructQueryParamString(Map<String, String> queryParams) {
        AtomicReference<String> query = new AtomicReference<>("");
        // Adding opening curly braces to the start of query params
        query.set(JsonPathConstants.JP_QUERY_PARAM_LEFT_CURLY_BRACES);
        queryParams.forEach((key, value) -> query.set(query.get() + "\"" + key + "\"" + JsonPathConstants.JP_QUERY_PARAM_KEY_VALUE_COLON + value + JsonPathConstants.JP_QUERY_PARAM_COMMA));
        // Removing the last element ',' and adding closing curly braces to the end of query params
        query.set(query.get().substring(0, query.get().length() - 1) + JsonPathConstants.JP_QUERY_PARAM_RIGHT_CURLY_BRACES);
        return query.get();
    }

    private String constructRequestParamString(Map<String, String> requestParams) throws JsonPathException {
        AtomicReference<String> requestParam = new AtomicReference<>("");
        if (requestParams == null || requestParams.isEmpty()) {
            throw new JsonPathException("Requests params is null or empty");
        }
        requestParams.forEach((key, value) -> requestParam.set(requestParam.get() + key + JsonPathConstants.EQUALS_TO + value + JsonPathConstants.AMPERSAND));
        requestParam.set(requestParam.get().substring(0, requestParam.get().length() - 1));
        return String.valueOf(requestParam);
    }


    private Map<String, String> addDefaultQueryParamVariables(Map<String, String> queryParams) {
        JsonPathConstants.DEFAULT_QUERY_PARAMS_VARIABLES_MAP.keySet().stream().forEach(k -> {
            if (!queryParams.containsKey(k)) {
                queryParams.put(k, JsonPathConstants.DEFAULT_QUERY_PARAMS_VARIABLES_MAP.get(k));
            }
        });
        return queryParams;
    }
}
