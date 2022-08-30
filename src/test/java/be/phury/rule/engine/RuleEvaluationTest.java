package be.phury.rule.engine;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RuleEvaluationTest {

    @Test
    public void foobar() {

        Rule authLevelRule = Rules.parse("authenticationLevel >= 20");
        Rule userTypeRule = Rules.parse("userType = JEDI");
        Rule pipAttributeRule = Rules.parse("pipAttribute in ['GOOD_GUY','BAD_GUY']");
        Rule permissionAttributeRule = Rules.parse("permissionAttribute in ['DRIVE_LANDSPEEDER','FLY_CARGO','FLY_FIGHTER']");
        Rule userQualityRule = Rules.parse("quality in ['AMBASSADOR']");

        User boba = new User("boba");
        boba.set("firstName", "Boba");
        boba.set("lastName", "Fett");
        boba.set("userType", "BOUNTY_HUNTER");
        boba.set("authenticationLevel", 0);
        boba.set("permissionAttribute", "FLY_FIGHTER");
        boba.set("quality", "BOUNTY_HUNTER");

        Context bobaContext = Context.create("user", boba);
        Assertions.assertEquals("false", authLevelRule.eval(bobaContext));
        Assertions.assertEquals("false", userTypeRule.eval(bobaContext));
        Assertions.assertEquals("true", pipAttributeRule.eval(bobaContext));
        Assertions.assertEquals("true", permissionAttributeRule.eval(bobaContext));

        User luke = new User("luke");
        luke.set("firstName", "Luke");
        luke.set("lastName", "Skywalker");
        luke.set("userType", "JEDI");
        luke.set("authenticationLevel", 20);
        luke.set("permissionAttribute", "DRIVE_LANDSPEEDER");
        luke.set("quality", "AMBASSADOR");

        Context lukeContext = Context.create("user", luke);
        Assertions.assertEquals("true", authLevelRule.eval(lukeContext));
        Assertions.assertEquals("true", userTypeRule.eval(lukeContext));
        Assertions.assertEquals("true", pipAttributeRule.eval(lukeContext));
        Assertions.assertEquals("true", permissionAttributeRule.eval(bobaContext));

        RuleEngine engine = new RuleEngine(
                authLevelRule,
                userTypeRule,
                pipAttributeRule,
                permissionAttributeRule,
                userQualityRule);

        Assertions.assertEquals("false", engine.eval(bobaContext));
        System.out.println(engine.report(bobaContext));

        Assertions.assertEquals("true", engine.eval(lukeContext));
        System.out.println(engine.report(lukeContext));
    }
}
