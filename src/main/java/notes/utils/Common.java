package notes.utils;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;

@UtilityClass
public class Common {

    /**
     * Consumer accepts the value if the value is not null.
     *
     * @param consumer Consumer (e.g. a Setter)
     * @param value Value of type T
     * @param <T> Generic type used in Consumer
     */
    public <T> void setIfValueNotNull(Consumer<T> consumer, T value) {
        if(value != null) {
            consumer.accept(value);
        }
    };

}
