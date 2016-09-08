package sk.seky.android.webapp.storage;

/**
 * Created by lsekerak on 5. 6. 2016.
 */
public interface IProvider<T> {
    public T get();

    public void set(T data);
}
