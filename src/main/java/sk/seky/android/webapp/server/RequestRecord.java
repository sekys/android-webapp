package sk.seky.android.webapp.server;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by lsekerak on 5. 7. 2016.
 */
public class RequestRecord {
    private Object instance;
    private Method method; // annotatted method

    // Dalsie sluzi na jedno vypocitanie
    private Annotation[] methodAnnotation;
    private Annotation[][] parametersAnnotation;
    private ObjectReader[] paramReaders;

    public RequestRecord(ObjectMapper mapper, Object instance, Method method) {
        this.instance = instance;
        this.method = method;
        this.methodAnnotation = method.getAnnotations();
        this.parametersAnnotation = method.getParameterAnnotations();

        Type[] paramTypes = method.getGenericParameterTypes();
        paramReaders = new ObjectReader[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            JavaType javaType = mapper.constructType(paramTypes[i]);
            paramReaders[i] = mapper.reader().forType(javaType);
        }
    }

    public Object getInstance() {
        return instance;
    }

    public Method getMethod() {
        return method;
    }

    public Annotation[] getMethodAnnotation() {
        return methodAnnotation;
    }

    public Annotation[][] getParametersAnnotation() {
        return parametersAnnotation;
    }

    public ObjectReader[] getParamReaders() {
        return paramReaders;
    }
}
