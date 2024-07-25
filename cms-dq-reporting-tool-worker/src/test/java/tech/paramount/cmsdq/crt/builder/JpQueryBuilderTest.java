package tech.paramount.cmsdq.crt.builder;

import org.mockito.MockitoAnnotations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriBuilder;
import tech.paramount.cmsdq.crt.worker.builder.JPQueryBuilder;
import tech.paramount.cmsdq.crt.worker.util.JsonPathException;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JpQueryBuilderTest {
    JPQueryBuilder jpQueryBuilder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        jpQueryBuilder = new JPQueryBuilder();
    }

    @Test
    @DisplayName("Build URI with HttpSkipCacheParam as false")
    void buildURIWhenEnableHttpSkipCacheParamIsFalse() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "10000");
        queryParamsMap.put("omitNumFound", "true");
        queryParamsMap.put("start", "0");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":10000}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);
        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        System.out.println("uri"+decodedActualUrl);
        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI with HttpSkipCacheParam as true")
    void buildURIWhenEnableHttpSkipCacheParamIsTrue() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");

        jpQueryBuilder.setHostName("mongo-arc-v2.mtvnservices-q.mtvi.com");
        jpQueryBuilder.setEndpoint("bet.com");
        jpQueryBuilder.setRequestParams(requestedParamsMap);
        jpQueryBuilder.setQueryParams(queryParamsMap);

        UriBuilder actualURIBuilder = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, true);
        // URI will contain 'ts' parameter as httpSkipCacheParam is true
        String actualURI = actualURIBuilder.build().toString();
        String expectedURIRegex = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&ts=(-?(?:[1-9][0-9]*)?[0-9]{4})-(1[0-2]|0[1-9])-(3[01]|0[1-9]|[12][0-9])T(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])(\\.[0-9]+)?(Z)?Z&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":1000}";
        String decodeActualURI = URLDecoder.decode(actualURI, StandardCharsets.UTF_8);
    }


    @Test
    @DisplayName("Build URI when query is of nodp")
    void buildURIWhenIsNoDPAsTrue() throws JsonPathException {

        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/nodp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":1000}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", true, queryParamsMap, false);

        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI when query is not nodp")
    void buildURIWhenIsNoDPAsFalse() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":1000}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);

        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI when user does not pass default query param values")
    void buildURIWithAllDefaultQueryParamVariables() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":1000}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);

        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI When User specifies all default query param values")
    void buildURIWithoutAllDefaultQueryParamValues() throws JsonPathException {

        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "50");
        queryParamsMap.put("start", "10");
        queryParamsMap.put("omitNumFound", "false");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":10,\"omitNumFound\":false,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":50}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);

        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI with some default query param values")
    void buildURIWithSomeDefaultQueryParamValues() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "50");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":50}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);

        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);

        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI when request params is empty")
    void buildURIWhenRequestParamsIsEmpty() {

        Map<String, String> requestedParamsMap = new HashMap<>();

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "50");

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        JsonPathException jsonPathException = assertThrows(JsonPathException.class, () -> jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false));

        String actualMessage = jsonPathException.getMessage();
        String expectedMessage = "Requests params is null or empty";

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Build URI when request params is null")
    void buildURIWhenRequestParamsIsNull() {

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "50");

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(null);

        JsonPathException jsonPathException = assertThrows(JsonPathException.class, () -> jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false));

        String actualMessage = jsonPathException.getMessage();
        String expectedMessage = "Requests params is null or empty";

        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Build URI when query params is empty")
    void buildURIWhenQueryParamsIsEmpty() throws JsonPathException {

        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC";

        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);
        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);

        assertEquals(expectedURI, decodedActualUrl);

    }

    @Test
    @DisplayName("Build URI when query params is null")
    void buildURIWhenQueryParamsIsNull() throws JsonPathException {

        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC";

        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, null, false);
        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);

        assertEquals(expectedURI, decodedActualUrl);
    }

    @Test
    @DisplayName("Build URI when request params is empty and query params is not empty")
    void buildURIWhenRequestParamsIsEmptyAndQueryParamsIsNotEmpty() {

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("rows", "50");

        Map<String, String> requestedParamsMap = new HashMap<>();

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        JsonPathException jsonPathException = assertThrows(JsonPathException.class, () -> jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false));

        String actualMessage = jsonPathException.getMessage();
        String expectedMessage = "Requests params is null or empty";

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Build URI when request params is not empty and query params is empty")
    void buildURIWhenRequestParamsIsNotEmptyAndQueryParamsIsEmpty() throws JsonPathException {

        Map<String, String> queryParamsMap = new HashMap<>();

        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        // Passing an empty request params map
        jpQueryBuilder.setRequestParams(requestedParamsMap);

        String expectedURI = "http://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC";

        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);
        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);

        assertEquals(expectedURI, decodedActualUrl);

    }

    @Test
    @DisplayName("Build URI when server protocol is https")
    void buildURIWhenServerProtocolIsHttps() throws JsonPathException {
        Map<String, String> requestedParamsMap = new HashMap<>();
        requestedParamsMap.put("dateFormat", "UTC");
        requestedParamsMap.put("stage", "authoring");

        Map<String, String> queryParamsMap = new HashMap<>();
        queryParamsMap.put("select", "{\"ImportCode|mtvi:id\":1}");
        queryParamsMap.put("where", "{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}");
        queryParamsMap.put("omitNumFound", "true");

        // Setting request params for DTO
        jpQueryBuilder.setRequestParams(requestedParamsMap);
        // Setting protocol as https
        jpQueryBuilder.setServerProtocol("https");

        String expectedURI = "https://mongo-arc-v2.mtvnservices-q.mtvi.com/jp/bet.com?stage=authoring&dateFormat=UTC&q={\"select\":{\"ImportCode|mtvi:id\":1},\"start\":0,\"omitNumFound\":true,\"where\":{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]},\"rows\":1000}";
        UriBuilder actualURI = jpQueryBuilder.buildJSONPathURI("mongo-arc-v2.mtvnservices-q.mtvi.com", "bet.com", false, queryParamsMap, false);
        String decodedActualUrl = URLDecoder.decode(actualURI.toUriString(), StandardCharsets.UTF_8);
        assertEquals(expectedURI, decodedActualUrl);
    }
}
