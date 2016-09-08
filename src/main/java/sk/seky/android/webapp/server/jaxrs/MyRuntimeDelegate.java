package sk.seky.android.webapp.server.jaxrs;

import javax.ws.rs.core.*;
import javax.ws.rs.ext.RuntimeDelegate;

/**
 * Created by lsekerak on 9. 6. 2016.
 */
public final class MyRuntimeDelegate extends RuntimeDelegate {
    public MyRuntimeDelegate() {
        RuntimeDelegate.setInstance(this);
    }


    public UriBuilder createUriBuilder() {
        return null;
    }


    public Response.ResponseBuilder createResponseBuilder() {
        return new MyResponseBuilder();
    }


    public Variant.VariantListBuilder createVariantListBuilder() {
        return null;
    }


    public <T> T createEndpoint(Application application, Class<T> endpointType) throws IllegalArgumentException, UnsupportedOperationException {
        return null;
    }


    public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type) throws IllegalArgumentException {
        return null;
    }


    public Link.Builder createLinkBuilder() {
        return null;
    }
}
