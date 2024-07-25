package tech.paramount.cmsdq.crt.worker.util;

import java.util.Map;

import static java.util.Map.entry;

public class JsonPathConstants {
    public static final String HTTP_SUFFIX = "://";

    //This constant is used to fetch more value from metadata to check if more docs are present in response from arc.
    public static final String JP_RESPONSE_METADATA_MORE = "more";

    //This constant is used to fetch the metadata in the response from arc
    public static final String JP_RESPONSE_METADATA = "metadata";

    //This constant is used to fetch the row size in the response from arc
    public static final String JP_RESPONSE_METADATA_ROWS = "rows";

    //This Map contains default values for query params
    public static final Map<String, String> DEFAULT_QUERY_PARAMS_VARIABLES_MAP =
            Map.ofEntries(
                    entry("rows", "1000"),
                    entry("omitNumFound", "true"),
                    entry("start", "0")
            );

    //This constant is used to add left curly brace to the start of query param
    public static final String JP_QUERY_PARAM_LEFT_CURLY_BRACES = "{";

    //This constant is used to add right curly brace to the end of query param
    public static final String JP_QUERY_PARAM_RIGHT_CURLY_BRACES = "}";

    //This constant is used to add colon after key in key:value pair
    public static final String JP_QUERY_PARAM_KEY_VALUE_COLON = ":";

    public static final String JP_QUERY_PARAM_FIELD_SEPARATOR = "|";

    public static final String JP_QUERY_PARAM_LIFT_ANGLE_BRACES = "[";
    public static final String JP_QUERY_PARAM_RIGHT_ANGLE_BRACES = "]";

    //This constant is used to add comma after every key value pair in query params
    public static final String JP_QUERY_PARAM_COMMA = ",";

    //This constant is used to add jp in the request url that will be used to fetch data from ARC.
    public static final String PATH_NAME_JP = "jp";

    //This constant is used to add nodp in the request url that will be used to fetch data from ARC.
    public static final String JP_NODP_QUERY = "nodp";

    //This constant is used to add ts in the url if enableHttpSkipCacheParam is true
    public static final String JP_TIMESTAMP = "ts";

    //This constant is used to add start in query params while pagination
    public static final String START = "start";

    //This constant is used to initialize default server protocol
    public static final String HTTP = "http";

    //This constant is used to build query params
    public static final String QUERY_PARAM = "q";

    public static final String AMPERSAND = "&";

    public static final String EQUALS_TO = "=";

    //Default row size
    public static final Integer DEFAULT_ROW_SIZE = 1000;

    public static final String DATEFORMAT_UTC = "UTC";

    public static final String BY_TYPE = "byType";
}
