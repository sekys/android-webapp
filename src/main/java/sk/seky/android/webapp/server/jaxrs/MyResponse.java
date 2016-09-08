package sk.seky.android.webapp.server.jaxrs;

import javax.ws.rs.core.*;
import java.lang.annotation.Annotation;
import java.net.URI;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by lsekerak on 9. 6. 2016.
 */
public final class MyResponse extends Response {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public StatusType getStatusInfo() {
        return Status.fromStatusCode(status);
    }

    public Object getEntity() {
        return null;
    }

    public <T> T readEntity(Class<T> entityType) {
        return null;
    }

    public <T> T readEntity(GenericType<T> entityType) {
        return null;
    }

    public <T> T readEntity(Class<T> entityType, Annotation[] annotations) {
        return null;
    }

    public <T> T readEntity(GenericType<T> entityType, Annotation[] annotations) {
        return null;
    }

    public boolean hasEntity() {
        return false;
    }

    public boolean bufferEntity() {
        return false;
    }

    public void close() {

    }

    public MediaType getMediaType() {
        return null;
    }

    public Locale getLanguage() {
        return null;
    }

    public int getLength() {
        return 0;
    }

    public Set<String> getAllowedMethods() {
        return null;
    }

    public Map<String, NewCookie> getCookies() {
        return null;
    }

    public EntityTag getEntityTag() {
        return null;
    }

    public Date getDate() {
        return null;
    }

    public Date getLastModified() {
        return null;
    }

    public URI getLocation() {
        return null;
    }

    public Set<Link> getLinks() {
        return null;
    }

    public boolean hasLink(String relation) {
        return false;
    }

    public Link getLink(String relation) {
        return null;
    }

    public Link.Builder getLinkBuilder(String relation) {
        return null;
    }

    public MultivaluedMap<String, Object> getMetadata() {
        return null;
    }

    public MultivaluedMap<String, String> getStringHeaders() {
        return null;
    }

    public String getHeaderString(String name) {
        return null;
    }
}
