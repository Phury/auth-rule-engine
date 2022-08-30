package be.phury.rule.engine;

import be.phury.rule.engine.User;

import java.util.HashMap;
import java.util.Map;

/**
 * (Entity-Attribute-Value) data
 */
public abstract class EAVEntity<T> {

    private Map<String, Object> properties = new HashMap<>();

    public void set(String key, Object property) {
        this.properties.put(key, property);
    }

    public Object get(String key) {
        return this.properties.get(key);
    }
}
