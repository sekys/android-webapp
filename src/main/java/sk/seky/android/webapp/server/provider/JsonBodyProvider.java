package sk.seky.android.webapp.server.provider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import sk.seky.android.webapp.browser.webapp.WebResourceRequestByJavascript;
import sk.seky.android.webapp.server.RequestRecord;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lsekerak on 6. 6. 2016.
 * javax.ws.rs.ext.MessageBodyReader a MessageBodyWriter
 */
public class JsonBodyProvider {
    private final ObjectMapper mapper;

    public JsonBodyProvider(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Object[] mapArguments(RequestRecord record, WebResourceRequestByJavascript request) throws IOException {
        JsonNode nodes = mapper.readTree(request.getBody());
        ObjectReader[] paramReaders = record.getParamReaders();
        Object[] args = new Object[paramReaders.length];
        for (int i = 0; i < paramReaders.length; i++) {
            // JsonNode node = nodes.get(i);
            //if (node == null) {
            //    throw new IllegalArgumentException("Missing argument for " + record.getMethod().getName() + " by " + nodes.toString());
            //}
            Object arg = paramReaders[i].readValue(nodes); // TODO: nateraz povolime len 1 parameter
            args[i] = arg;
        }
        return args;
    }

    public InputStream mapResponse(Object result) throws IOException {
        return new ByteArrayInputStream(mapper.writeValueAsBytes(result));
    }

}
