package be.phury.rule.engine;

public class Rules {
    public static Rule parse(String rule) {
        String[] tokens = rule.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Rule is not valid. Should have 3 elements, found " + tokens.length);
        }
        String subject = tokens[0];
        String predicate = tokens[1];
        String object = tokens[2];

        switch (subject) {
            case "pipAttribute" :
                return new PipAttributeRule(subject, predicate, object);
            case "authenticationLevel" :
            case "permissionAttribute" :
            case "quality" :
            case "userType" :
                return new ContextEvaluationRule(subject, predicate, object);
            default:
                throw new UnsupportedOperationException("Subject ["+subject+"] not known");
        }
    }

    /**
     * A rule expressed through a {subject, predicate, object} triple.
     */
    public static abstract class Triple implements Rule {
        protected String subject;
        protected String predicate;
        protected String object;

        public Triple(String subject, String predicate, String object) {
            this.subject = subject;
            this.predicate = predicate;
            this.object = object;
        }

        @Override
        public String toString() {
            return "[" + String.join(" ", this.subject, this.predicate, this.object) + "]";
        }
    }

    public static class ContextEvaluationRule extends Triple {

        public ContextEvaluationRule(String subject, String predicate, String object) {
            super(subject, predicate, object);
        }

        @Override
        public String eval(Context context) {
            User u = context.getUser();
            return Operators.parse(this.predicate, this.object).eval(context.with("subject", u.get(this.subject)));
        }
    }

    public static class PipAttributeRule extends Triple {

        public PipAttributeRule(String subject, String predicate, String object) {
            super(subject, predicate, object);
        }

        @Override
        public String eval(Context context) {
            // TODO: call url to retrieve values then eval as follows
            User u = context.getUser();
            final Context newContext;
            if (u.getId().equals("boba")) {
                newContext = context.with("subject", "BAD_GUY");
            } else {
                newContext = context.with("subject", "GOOD_GUY");
            }
            return Operators.parse(this.predicate, this.object).eval(newContext);
        }
    }
}
