package tech.paramount.cmsdq.crt.worker.adapter;

import com.fasterxml.jackson.databind.module.SimpleModule;
import tech.paramount.cmsdq.crt.worker.util.JsonMapperException;
import tech.paramount.cmsdq.crt.worker.models.Root;
import tech.paramount.cmsdq.crt.worker.util.JsonMapper;

import java.io.InputStream;

public class ReportCriteriaParser {
    public Root unmarshall(InputStream inputStream) {
        try {
            var jsonMapper = new JsonMapper();
            var module = new SimpleModule();
            jsonMapper.registerModule(module);
            var root = jsonMapper.parseJson(inputStream, Root.class);
            return root;
        } catch (JsonMapperException e) {

        }
        return null;
    }
}
