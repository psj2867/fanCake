package ml.psj2867.demo.util.exceptionwrap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;

public class ExceptionOptional<T> {
    private Supplier<T> base;
    private Map<Exception, Function<Exception, T>> map = new LinkedHashMap<>();

    public static <K> ExceptionOptional<K> of(Supplier<K> base){
        return new ExceptionOptional(base);
    }
    public ExceptionOptional(Supplier<T> base){
        this.base = base;
    }

    public ExceptionOptional<T> then(Exception e, Function<Exception, T> function) {
        map.put(e, function);
        return this;
    }

    public T get() {
        try {
            return base.get();
        } catch (Exception e) {
            for (Entry<Exception, Function<Exception, T>> ef : map.entrySet()) {
                if( e.getClass().isAssignableFrom(ef.getKey().getClass()) )
                    return ef.getValue().apply(e);
            }
            throw e;
        }
    }

    public T fin(Function<Exception,T> function){
        try {
            return get();
        } catch (Exception e) {
            return function.apply(e);
        }
    }
}
