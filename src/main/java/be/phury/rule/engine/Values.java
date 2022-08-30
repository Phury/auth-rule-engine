package be.phury.rule.engine;

import java.util.Arrays;
import java.util.List;

public class Values {

    public static List<Object> asList(String str) {
        String[] tokens = str.replace("[", "").replace("]", "").replace("'", "").split(",");
        return Arrays.asList(tokens); //.stream().map(elt -> ).collect(Collectors.toList());
    }
}
