package be.phury.rule.engine;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private final Map<String, Object> contextMap = new HashMap<>();

    public static Context create(String key, Object value) {
        return new Context().put(key, value);
    }

    public Context put(String key, Object value) {
        this.contextMap.put(key, value);
        return this;
    }

    /**
     * Creates a new context off this one with the new entry
     * @param key
     * @param value
     * @return
     */
    public Context with(String key, Object value) {
        Context newContext = new Context();
        newContext.contextMap.putAll(this.contextMap);
        newContext.put(key, value);
        return newContext;
    }

    public User getUser() {
        return (User) contextMap.get("user");
    }

    public int getInt(String key) {
        return (Integer) contextMap.get(key);
    }

    public List<Object> getList(String key) {
        return (List<Object>) contextMap.get(key);
    }

    public Object get(String key) {
        if (!contextMap.containsKey(key)) return new NullObject();
        return contextMap.get(key);
    }

    private static final class NullObject {

    }
}
