package be.phury.rule.engine;

import be.phury.rule.engine.Context;
import be.phury.rule.engine.Rule;

import java.util.Collection;
import java.util.List;

public class Operators {
    public static Rule parse(String predicate, String object) {
        switch (predicate) {
            case ">=": return new GreaterOrEqualOperator(object);
            case "<=": return new SmallerOrEqualOperator(object);
            case "=": return new EqualsOperator(object);
            case "in": return new InOperator(object);
            default: throw new UnsupportedOperationException("Operator ["+predicate+"] not known");
        }
    }

    private static abstract class Operator implements Rule {
        protected String object;

        protected Operator(String object) {
            this.object = object;
        }
    }

    private static class GreaterOrEqualOperator extends Operator {
        public GreaterOrEqualOperator(String object) {
            super(object);
        }

        @Override
        public String eval(Context context) {
            return (context.getInt("subject") >= Integer.parseInt(this.object)) + "";
        }
    }

    private static class SmallerOrEqualOperator extends Operator {
        public SmallerOrEqualOperator(String object) {
            super(object);
        }

        @Override
        public String eval(Context context) {
            return (context.getInt("subject") <= Integer.parseInt(this.object)) + "";
        }
    }

    private static class EqualsOperator extends Operator {
        public EqualsOperator(String object) {
            super(object);
        }

        @Override
        public String eval(Context context) {
            return context.get("subject").equals(this.object) + "";
        }
    }

    private static class InOperator extends Operator {
        public InOperator(String object) {
            super(object);
        }

        @Override
        public String eval(Context context) {
            return Values.asList(this.object).contains(context.get("subject")) + "";
        }
    }
}
