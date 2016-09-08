package sk.seky.android.webapp.server.jaxrs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lsekerak on 3. 7. 2016.
 * https://github.com/spring-projects/spring-framework/blob/master/spring-core/src/main/java/org/springframework/core/annotation/AnnotationUtils.java
 */
public class Annotations {
    public static <A extends Annotation> A findAnnotation(Annotation[] searchList, Class<A> annotation) {
        if (searchList == null) return null;
        for (Annotation ann : searchList) {
            /*
            Class a = ann.getClass();
			Class b = ann.annotationType();
			Class c = ann.annotationType().getClass();
			System.out.println(a);
			System.out.println(b);
			System.out.println(c);
			System.out.println(annotation);
			*/
            if (ann.annotationType().equals(annotation)) {
                return (A) ann;
            }
        }
        return null;
    }

    // TODO: bottleneck
    public static <A extends Annotation> A findAnnotation(Method method, Class<A> annotation) {
        A result = findAnnotation(method.getAnnotations(), annotation);
        if (result != null) {
            return result;
        }

        Class<?> clazz = method.getDeclaringClass();
        while (result == null) {
            if (clazz == null || Object.class == clazz) {
                return null;
            }
            result = searchMethodAnnotationOnInterfaces(method, annotation, clazz.getInterfaces());
            if (result != null) {
                return result;
            }

            try {
                Method equivalentMethod = clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
                result = findAnnotation(equivalentMethod.getAnnotations(), annotation);
            } catch (NoSuchMethodException ex) {
                // No equivalent method found
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    public static <A extends Annotation> A findClassAnnotation(Method method, Class<?> aClass, Class<A> annotation) {
        A result = null;
        Class<?> clazz = aClass;
        while (result == null) {
            if (clazz == null || Object.class == clazz) {
                return null;
            }

            result = searchClassAnnotationOnInterfaces(method, annotation, clazz.getInterfaces());
            if (result != null) {
                return result;
            }
            clazz = clazz.getSuperclass();
        }
        return result;
    }

    private static <A extends Annotation> A searchMethodAnnotationOnInterfaces(Method method, Class<A> annotationType, Class<?>... ifcs) {
        Method equivalentMethod = findOverridedMethod(method, ifcs);
        if (equivalentMethod == null) {
            return null;
        }
        return findAnnotation(equivalentMethod.getAnnotations(), annotationType);
    }

    private static <A extends Annotation> A searchClassAnnotationOnInterfaces(Method method, Class<A> annotationType, Class<?>... ifcs) {
        Method equivalentMethod = findOverridedMethod(method, ifcs);
        if (equivalentMethod == null) {
            return null;
        }
        return findAnnotation(equivalentMethod.getDeclaringClass().getAnnotations(), annotationType);
    }

    private static Method findOverridedMethod(Method method, Class<?>... ifcs) {
        for (Class<?> iface : ifcs) {
            try {
                Method equivalentMethod = iface.getMethod(method.getName(), method.getParameterTypes());
                return equivalentMethod;
            } catch (NoSuchMethodException ex) {
                // Skip this interface - it doesn't have the method...
            }
        }
        return null;
    }

    public static <A extends Annotation> Class<?> findsAnnotatedClass(Method method, Class<?> aClass, Class<A> annotation) {
        A result = null;
        Class<?> clazz = aClass;
        while (result == null) {
            if (clazz == null || Object.class == clazz) {
                return null;
            }

            result = searchClassAnnotationOnInterfaces(method, annotation, clazz.getInterfaces());
            if (result != null) {
                return aClass;
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

}
