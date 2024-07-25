package tech.paramount.cmsdq.crt.worker.service.helper;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import tech.paramount.cmsdq.crt.worker.models.Root;

import java.io.IOException;
import java.io.InputStream;

public class ReportHelper {

    public InputStream getInputStream(String filePath) throws IOException {
        var resourceResolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());
        return resourceResolver.getResource(filePath).getInputStream();
    }
}
