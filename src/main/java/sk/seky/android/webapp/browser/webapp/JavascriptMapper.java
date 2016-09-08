package sk.seky.android.webapp.browser.webapp;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lsekerak on 6. 6. 2016.
 */
public class JavascriptMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavascriptMapper.class);

    private final ObjectMapper mapper;

    public JavascriptMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public WebResourceRequest deserialize(String method, String url, String body) {
        /*InputStream is = null;
        if (body != null) {
			is = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
		}*/
        return new JavascriptWebResourceRequest(Uri.parse(url), method, body);
    }

    public String serialize(WebResourceResponse response) {
        try {
            JsonNode data = mapper.readTree(response.getData());
            ObjectNode wrapper = mapper.getNodeFactory().objectNode();
            wrapper.set("body", data);
            wrapper.put("status", response.getStatusCode());
            return mapper.writeValueAsString(wrapper);
        } catch (IOException e) {
            LOGGER.error("serialize", e);
            return "";
        }
    }

    private static class JavascriptWebResourceRequest implements WebResourceRequestByJavascript {
        private final Uri uri;
        private final String method;
        private final String body;

        public JavascriptWebResourceRequest(Uri uri, String method, String body) {
            this.uri = uri;
            this.method = method;
            this.body = body;
        }

        @Override
        public Uri getUrl() {
            return uri;
        }

        @Override
        public boolean isForMainFrame() {
            return true;
        }

        @Override
        public boolean hasGesture() {
            return true;
        }

        @Override
        public String getMethod() {
            return method;
        }

        @Override
        public Map<String, String> getRequestHeaders() {
            // Zatial je podporovany len json
            Map<String, String> map = new HashMap<>();
            map.put("Accept", "application/json");
            map.put("Content-Type", "application/json; charset=utf-8");
            map.put("X-Requested-With", "JavascriptBridge");
            return map;
        }

        @Override
        public String getBody() {
            return body;
        }
    }
}
