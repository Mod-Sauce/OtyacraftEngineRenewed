package red.felnull.otyacraftengine.api.register;

import java.util.Set;

public interface ISingleRegister<T> {
    default void register(T entry) {
        if (entry != null)
            getSet().add(entry);
    }

    Set<T> getSet();
}
