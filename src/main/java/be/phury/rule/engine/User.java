package be.phury.rule.engine;

/**
 *  EAV (Entity-Attribute-Value) data
 */
public class User extends EAVEntity<User> {
    private final String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
