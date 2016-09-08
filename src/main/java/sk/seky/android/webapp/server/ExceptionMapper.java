package sk.seky.android.webapp.server;

import android.webkit.WebResourceResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by lsekerak on 6. 6. 2016.
 */
public abstract class ExceptionMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionMapper.class);
    private ObjectMapper mapper;

    public ExceptionMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public abstract WebResourceResponse resolve(Exception ex);

    protected WebResourceResponse createResponse(int status, Object body) throws UnsupportedEncodingException, JsonProcessingException {
        String jsonBody = mapper.writeValueAsString(body);
        LOGGER.debug("{}", jsonBody);
        InputStream is = new ByteArrayInputStream(jsonBody.getBytes("UTF-8"));
        WebResourceResponse response = new WebResourceResponse("application/javascript", "UTF-8", is);
        response.setStatusCodeAndReasonPhrase(status, "-");
        return response;
    }

    protected WebResourceResponse createResponse(int status, String msg) throws UnsupportedEncodingException, JsonProcessingException {
        ObjectNode body = mapper.getNodeFactory().objectNode();
        body.put("msg", msg);
        return createResponse(status, body);
    }
}
