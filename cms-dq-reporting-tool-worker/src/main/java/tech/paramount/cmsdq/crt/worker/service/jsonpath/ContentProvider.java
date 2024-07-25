package tech.paramount.cmsdq.crt.worker.service.jsonpath;

import com.paramount.cms.arc.dto.JSONPathResponseIterator;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import tech.paramount.cmsdq.crt.worker.client.CaslJPClient;
import tech.paramount.cmsdq.crt.worker.dto.JsonPathQueryDTO;

@RequiredArgsConstructor
public class ContentProvider {

    @Autowired
    private final CaslJPClient caslJPClient;

    public void processReportResponse(JsonPathQueryDTO dto) {
        caslJPClient.initCaslJPClient(dto);
        JSONPathResponseIterator arcResponseIterator = caslJPClient.fetchJsonPathResponseIterator(dto);
        JSONObject arcJSONResponse = caslJPClient.fetchJsonPathResponse(dto);
        System.out.println(arcJSONResponse.toString());
    }
}
