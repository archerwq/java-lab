package com.weibo.keeplooking.jexl;

import org.apache.commons.jexl3.JexlBuilder;
import org.apache.commons.jexl3.JexlContext;
import org.apache.commons.jexl3.JexlEngine;
import org.apache.commons.jexl3.JexlException;
import org.apache.commons.jexl3.JexlExpression;
import org.apache.commons.jexl3.MapContext;
import org.apache.commons.jexl3.internal.Script;
import org.junit.Assert;
import org.junit.Test;

public class JexlTest {

    private static final JexlEngine jexl = new JexlBuilder().strict(false).create();

    @Test
    public void case1() {
        String calculateTax = "((G1 + G2 + G3) * 0.1) + G4";
        JexlExpression e = jexl.createExpression(calculateTax);

        JexlContext context = new MapContext();
        context.set("G1", 10);
        context.set("G2", 20);
        context.set("G3", 30);
        context.set("G4", 40);

        Number result = (Number) e.evaluate(context);

        Assert.assertEquals(46, result.intValue());
    }

    @Test
    public void case2() {
        String trigger = "changeType == 'UPDATE' && ObjectTypeA.FieldX >= 100 && ObjectTypeA.FieldX > ObjectTypeA.FieldX_old";
        JexlExpression e = jexl.createExpression(trigger);
        JexlContext context = new MapContext();
        context.set("changeType", "UPDATE");
        context.set("ObjectTypeA.FieldX", "100");
        // context.set("ObjectTypeA.FieldX_old", "10");
        Assert.assertTrue(!(Boolean) e.evaluate(context));
    }

    @Test(expected = JexlException.class)
    public void case3() {
        String trigger = "x++y++";
        jexl.createExpression(trigger);
    }

    @Test
    public void case4() {
        String trigger = "changeType == 'UPDATE' && ObjectTypeA.FieldX >= 100 && ObjectTypeA.FieldX > ObjectTypeA.FieldX_old";
        JexlExpression e = jexl.createExpression(trigger);
        System.out.println(e.getSourceText());
        System.out.println(e.getParsedText());
    }

    @Test
    public void case5() {
        String trigger = "changeType == 'UPDATE' && ObjectTypeA.FieldX >= 100 && ObjectTypeA.FieldX > ObjectTypeA.FieldX_old || ObjectTypeB.Status = _STATUS";
        Script expression = (Script) jexl.createExpression(trigger);
        System.out.println(expression.getParsedText());
        System.out.println(expression.getLocalVariables());
        System.out.println(expression.getParameters());
        System.out.println(expression.getVariables());
    }

}
