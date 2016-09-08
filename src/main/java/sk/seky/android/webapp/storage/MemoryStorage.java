package sk.seky.android.webapp.storage;

/**
 * Created by lsekerak on 5. 6. 2016.
 */
public class MemoryStorage<T> implements IProvider<T> {
    private T storage;

    @Override
    public T get() {
        return storage;
    }

    @Override
    public void set(T data) {
        storage = data;
    }
}
