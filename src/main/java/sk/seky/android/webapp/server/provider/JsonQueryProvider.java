package sk.seky.android.webapp.server.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import sk.seky.android.webapp.browser.webapp.WebResourceRequestByJavascript;
import sk.seky.android.webapp.server.RequestRecord;
import sk.seky.android.webapp.server.jaxrs.Annotations;

import javax.ws.rs.QueryParam;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;

/**
 * Created by lsekerak on 1. 10. 2016.
 */
public class JsonQueryProvider {
    private final ObjectMapper mapper;

    public JsonQueryProvider(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Object[] mapArguments(RequestRecord record, WebResourceRequestByJavascript request) throws IOException {
        JsonNode nodes = mapper.readTree(request.getBody());
        ObjectReader[] paramReaders = record.getParamReaders();
        Annotation[][] annotations = record.getParametersAnnotation();
        Object[] args = new Object[paramReaders.length];
        for (int i = 0; i < paramReaders.length; i++) {
            QueryParam annotation = Annotations.findAnnotation(annotations[i], QueryParam.class);
            JsonNode node = nodes.get(annotation.value());
            Object arg = null;
            if (node != null) {
                arg = paramReaders[i].readValue(node);
            }
            args[i] = arg;
        }
        return args;
    }
}
