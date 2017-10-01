package com.weibo.keeplooking.pattern.builder;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test case for builder pattern.
 * 
 * @author Johnny
 */
public class BuilderTest {

    @Test
    public void testNuritionFacts() {
        NutritionFacts facts = new NutritionFacts.Builder(240, 8).calories(100)
                .sodium(35).carbohydrate(27).build();
        Assert.assertEquals(
                "nutrition facts: servingSize=240, servings=8, calories=100, fat=0, sodium=35, carbohydrate=27",
                facts.toString());
    }

}
