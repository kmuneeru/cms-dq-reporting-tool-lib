package tech.paramount.cmsdq.crt.worker.builder;

import org.springframework.util.CollectionUtils;
import tech.paramount.cmsdq.crt.worker.models.Field;
import tech.paramount.cmsdq.crt.worker.models.References;
import tech.paramount.cmsdq.crt.worker.models.SelectedFields;
import tech.paramount.cmsdq.crt.worker.util.HelperUtils;
import tech.paramount.cmsdq.crt.worker.util.JsonPathConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.BY_TYPE;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_COMMA;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_FIELD_SEPARATOR;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_KEY_VALUE_COLON;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_LEFT_CURLY_BRACES;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_LIFT_ANGLE_BRACES;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_RIGHT_ANGLE_BRACES;
import static tech.paramount.cmsdq.crt.worker.util.JsonPathConstants.JP_QUERY_PARAM_RIGHT_CURLY_BRACES;

public class ReportConverterBuilder {

    public String buildSelectString(SelectedFields userFields) {
        /*
            "{
                \"mtvi:id|ModificationReason|Title|Title_ci|Title_cs\":1,
                \"Colors\":{\"mtvi:id\":1},
                \"Predicates\":{\"mtvi:id|mtvi:shortId|Title_ci|Title_cs\":1}
            }"
        */
        AtomicReference<String> query = new AtomicReference<>("");
        userFields.getFields();

        if(CollectionUtils.isEmpty(userFields.getFields()))
            return query.get();

        Set<Field> primaryFields = userFields.getFields().stream()
                                        .filter(it -> it.getChecked())
                                        .collect(Collectors.toSet());

        String primaryFieldString = buildPrimaryFieldsString(primaryFields);

        if(CollectionUtils.isEmpty( userFields.getReferences()) )
            return query.get(); //empty query string

        List<References> references  = userFields.getReferences();

        Map<Boolean, List<References>> primaryReferences = references.stream()
                                            .collect(partitioningBy(ref -> ref.getSubreferences().isEmpty()));

        List<References> linkReferences = primaryReferences.get(true);
        List<References> linklistReferences = primaryReferences.get(false);

        Map<String, Set<Field>> linkReferencesFieldMap = filterUncheckedFields(linkReferences);

        String linkReferenceString = buildLinkReferencesString(linkReferencesFieldMap);
        String linklistReferencesString = buildLinklistReferencesString(linklistReferences);

        query.set(query.get() + JP_QUERY_PARAM_LEFT_CURLY_BRACES);
        query.set(query.get() + linkReferenceString);
        if(!linklistReferencesString.isBlank()) {
            query.set(query.get() + linklistReferencesString);
        }
        query.set(query.get() + primaryFieldString);
        query.set(query.get() + JP_QUERY_PARAM_RIGHT_CURLY_BRACES);
        return query.get();
    }

    private static Map<String, Set<Field>> filterUncheckedFields(List<References> linkReferences) {
        Map<String, Set<Field>> map = new HashMap<>();
        linkReferences.forEach(ref ->
                {
                    Set<Field> checkedFields = ref.getFields().stream().filter(it -> it.getChecked()).collect(Collectors.toSet());
                    map.put(ref.getName(), checkedFields);
                });
        return map;
    }

    private static String buildPrimaryFieldsString(Set<Field> primaryFields) {
        // \"mtvi:id|ModificationReason|Title|Title_ci|Title_cs\":1,
        AtomicReference<String> query = new AtomicReference<>("");
        query.set(query.get() + "\"");
        primaryFields.forEach(field -> query.set(query.get() + field.getName() + JP_QUERY_PARAM_FIELD_SEPARATOR));
        // Removing the last element ',' and adding closing curly braces to the end of query params
        query.set(query.get().substring(0, query.get().length() - 1) + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + 1);
        return query.get();
    }

    private static String buildLinkReferencesString(Map<String, Set<Field>> linkReferencesMap) {
//        \"Colors\":{\"mtvi:id\":1},
//        \"Predicates\":{\"mtvi:id|mtvi:shortId|Title_ci|Title_cs\":1}
        AtomicReference<String> query = new AtomicReference<>("");
        linkReferencesMap
                .forEach((key, value) ->  {
                    query.set(query.get() + "\"" + key + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + JP_QUERY_PARAM_LEFT_CURLY_BRACES);
                    String fieldString = buildFieldString(value);
                    query.set(query.get() + fieldString + JP_QUERY_PARAM_RIGHT_CURLY_BRACES + JP_QUERY_PARAM_COMMA);
                });
        // Removing the last element ',' and adding closing curly braces to the end of query params
        // query.set(query.get() + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + 1 + JP_QUERY_PARAM_RIGHT_CURLY_BRACES);
        return query.get();
    }

    private static String buildFieldString(Set<Field> fields) {
        AtomicReference<String> query = new AtomicReference<>("");
        query.set(query.get() + "\"");
        fields.forEach(field -> {
            query.set(query.get() + field.getName() + JP_QUERY_PARAM_FIELD_SEPARATOR);
        });
        query.set(query.get().substring(0, query.get().length() - 1) + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + 1);
        return query.get();
    }

    private static String buildLinklistReferencesString(List<References> linklistReferences) {
        // "ImagesWithCaptions":{"Image":{"mtvi:id":1}},
        // "Ratings":{"Rating":{"mtvi:id|mtvi:contentType":1},"Descriptors":{"mtvi:id|mtvi:contentType":1},"Notes":1},
        AtomicReference<String> query = new AtomicReference<>("");
        linklistReferences.forEach( ref -> {
                query.set(query.get() + "\"" + ref.getName() + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + JP_QUERY_PARAM_LEFT_CURLY_BRACES);
                String subReferenceString = buildSubreferencesString(ref.getSubreferences());
                query.set(query.get() + subReferenceString);
                query.set(query.get().substring(0, query.get().length() - 1) + JP_QUERY_PARAM_RIGHT_CURLY_BRACES + JP_QUERY_PARAM_COMMA);
            }
        );
        //query.set(query.get() + JP_QUERY_PARAM_COMMA);
        return query.get();
    }

    private static String buildSubreferencesString(List<References> subReferencesList) {
        Map<String, Set<Field>> subReferencesFieldMap = filterUncheckedFields(subReferencesList);
        return buildLinkReferencesString(subReferencesFieldMap);
    }

    public static String buildWhereString(String contentType) {
        //"{\"byType\":[\"Standard:BroadcastTelevisionChannel\"]}"
        AtomicReference<String> query = new AtomicReference<>("");
        query.set(JP_QUERY_PARAM_LEFT_CURLY_BRACES);
        query.set(query.get() + "\"" + BY_TYPE + "\"" + JP_QUERY_PARAM_KEY_VALUE_COLON + JP_QUERY_PARAM_LIFT_ANGLE_BRACES);
        query.set(query.get() + "\"" + contentType + "\"" +  JP_QUERY_PARAM_RIGHT_ANGLE_BRACES + JP_QUERY_PARAM_RIGHT_CURLY_BRACES);
        return query.get();
    }

}
