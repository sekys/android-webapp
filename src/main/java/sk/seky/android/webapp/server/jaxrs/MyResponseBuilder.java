package sk.seky.android.webapp.server.jaxrs;

import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by lsekerak on 9. 6. 2016.
 */
public final class MyResponseBuilder extends Response.ResponseBuilder {

    private final MyResponse reponse = new MyResponse();

    public Response build() {
        return reponse;
    }


    public Response.ResponseBuilder clone() {
        return this;
    }


    public Response.ResponseBuilder status(int status) {
        reponse.setStatus(status);
        return this;
    }


    public Response.ResponseBuilder entity(Object entity) {
        return this;
    }


    public Response.ResponseBuilder entity(Object entity, Annotation[] annotations) {
        return this;
    }


    public Response.ResponseBuilder allow(String... methods) {
        return this;
    }


    public Response.ResponseBuilder allow(Set<String> methods) {
        return this;
    }


    public Response.ResponseBuilder cacheControl(CacheControl cacheControl) {
        return this;
    }


    public Response.ResponseBuilder encoding(String encoding) {
        return this;
    }


    public Response.ResponseBuilder header(String name, Object value) {
        return this;
    }


    public Response.ResponseBuilder replaceAll(MultivaluedMap<String, Object> headers) {
        return this;
    }


    public Response.ResponseBuilder language(String language) {
        return this;
    }


    public Response.ResponseBuilder language(Locale language) {
        return this;
    }


    public Response.ResponseBuilder type(MediaType type) {
        return this;
    }


    public Response.ResponseBuilder type(String type) {
        return this;
    }


    public Response.ResponseBuilder variant(Variant variant) {
        return this;
    }


    public Response.ResponseBuilder contentLocation(URI location) {
        return this;
    }


    public Response.ResponseBuilder cookie(NewCookie... cookies) {
        return this;
    }


    public Response.ResponseBuilder expires(Date expires) {
        return this;
    }


    public Response.ResponseBuilder lastModified(Date lastModified) {
        return this;
    }


    public Response.ResponseBuilder location(URI location) {
        return this;
    }


    public Response.ResponseBuilder tag(EntityTag tag) {
        return this;
    }


    public Response.ResponseBuilder tag(String tag) {
        return this;
    }


    public Response.ResponseBuilder variants(Variant... variants) {
        return this;
    }


    public Response.ResponseBuilder variants(List<Variant> variants) {
        return this;
    }


    public Response.ResponseBuilder links(Link... links) {
        return this;
    }


    public Response.ResponseBuilder link(URI uri, String rel) {
        return this;
    }


    public Response.ResponseBuilder link(String uri, String rel) {
        return this;
    }
}
