package be.phury.rule.engine;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RuleEngine {
    private List<Rule> andRules;
    public RuleEngine(Rule ... andRules) {
        this.andRules = Arrays.asList(andRules);
    }

    public String eval(Context context) {
        return andRules.stream()
                .filter(r -> "false".equals(r.eval(context)))
                .findFirst()
                .map(r -> "false")
                .orElse("true");
    }

    public List<String> report(Context context) {
        return andRules.stream()
                .filter(r -> "false".equals(r.eval(context)))
                .map(Rule::toString)
                .collect(Collectors.toList());
    }
}
