package com.weibo.keeplooking.pattern.builder;

/**
 * The label of packaged food, demo for buider pattern.
 * 
 * @author Johnny
 */
public class NutritionFacts {

    private final int servingSize; // (ml), required
    private final int servings; // (per container), required
    private final int calories; // optional
    private final int fat; // (g), optional
    private final int sodium; // (mg), optional
    private final int carbohydrate; // (g), optional

    public static class Builder {
        // required parameters
        private final int servingSize;
        private final int servings;

        // optional parameters, initialized to default values
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(final int servingSize, final int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int calories) {
            this.calories = calories;
            return this;
        }

        public Builder fat(int fat) {
            this.fat = fat;
            return this;
        }

        public Builder sodium(int sodium) {
            this.sodium = sodium;
            return this;
        }

        public Builder carbohydrate(int carbohydrate) {
            this.carbohydrate = carbohydrate;
            return this;
        }

        public NutritionFacts build() {
            return new NutritionFacts(this);
        }
    }

    private NutritionFacts(Builder builder) {
        this.servingSize = builder.servingSize;
        this.servings = builder.servings;
        this.calories = builder.calories;
        this.fat = builder.fat;
        this.sodium = builder.sodium;
        this.carbohydrate = builder.carbohydrate;
    }

    @Override
    public String toString() {
        return String
                .format("nutrition facts: servingSize=%d, servings=%d, calories=%d, fat=%d, sodium=%d, carbohydrate=%d",
                        servingSize, servings, calories, fat, sodium,
                        carbohydrate);
    }
}
