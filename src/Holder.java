import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Holder<T> {
    public T data;
    public Consumer<T> callback;

    public Holder(T data) {
        this.data = data;
    }

    public void setData(T data) {
        T oldData = this.data;
        this.data = data;
        // if we have a callback and data changed
        if (this.callback != null && !Objects.equals(oldData, data)) {
            this.callback.accept(data);
        }
    }

    public T getData() {
        return data;
    }

    public void setCallback(Consumer<T> callback) {
        this.callback = callback;
    }
}
