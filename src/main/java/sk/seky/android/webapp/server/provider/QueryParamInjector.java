package sk.seky.android.webapp.server.provider;

import android.net.Uri;
import android.webkit.WebResourceRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import sk.seky.android.webapp.server.RequestRecord;
import sk.seky.android.webapp.server.jaxrs.Annotations;

import javax.ws.rs.QueryParam;
import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by lsekerak on 6. 6. 2016.
 * napodobenina org.jboss.resteasy.core.QueryParamInjector
 */
public class QueryParamInjector {
    private final ObjectMapper mapper;

    public QueryParamInjector(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public Object[] mapArguments(RequestRecord record, WebResourceRequest request) throws IOException {
        Uri uri = request.getUrl();
        ObjectReader[] paramReaders = record.getParamReaders();
        Annotation[][] annotations = record.getParametersAnnotation();
        Object[] args = new Object[paramReaders.length];
        for (int i = 0; i < paramReaders.length; i++) {
            // TODO: tu sa nemusi uplatnit dedenie
            QueryParam annotation = Annotations.findAnnotation(annotations[i], QueryParam.class);
            String param = uri.getQueryParameter(annotation.value());
            Object arg = null;
            if (param != null) {
                arg = paramReaders[i].readValue(param);
            }
            args[i] = arg;
        }
        return args;
    }
}
