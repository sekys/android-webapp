package sk.seky.android.webapp.server.jaxrs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lsekerak on 5. 7. 2016.
 */
public abstract class AnnnotationWalker {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnnotationWalker.class);
    private boolean once = false;

    public static <A extends Annotation> A findAnnotation(Annotation[] searchList, Class<A> annotation) {
        if (searchList == null) return null;
        for (Annotation ann : searchList) {
            if (ann.annotationType().equals(annotation)) {
                return (A) ann;
            }
        }
        return null;
    }

    public void walktree(Object controller, Class<? extends Annotation> mainAnnotation) {
        Class<?> clazz = controller.getClass();
        while (true) {
            if (clazz == null || Object.class == clazz) {
                break;
            }

            checkClass(clazz, mainAnnotation);

            // go to width for interfaces
            for (Class<?> iface : clazz.getInterfaces()) {
                checkClass(iface, mainAnnotation);
            }
            clazz = clazz.getSuperclass(); // go deep for inheritance
        }
        if (!once) {
            notFound(controller);
        }
    }

    private void checkClass(Class<?> clazz, Class<? extends Annotation> mainAnnotation) {
        for (Method method : clazz.getDeclaredMethods()) {
            Annotation[] annotations = method.getAnnotations();
            Annotation lookingAnon = findAnnotation(annotations, mainAnnotation);
            if (lookingAnon != null) {
                // mainAnnotation was found
                once = true;
                foundAnnottatedObject(clazz, method);
            }
        }
    }

    protected abstract void foundAnnottatedObject(Class<?> clazz, Method method);

    protected void notFound(Object controller) {
        LOGGER.warn("No annotation found for controller {}", controller.getClass().getName());
    }
}
