package capter02.com.snail.b1;

import lombok.Data;

/**
 * builder模式
 */
@Data
public class NurtritionFacts {
    private final int servingSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int soduim;
    private final int carbohydrate;
    public static class Builder{
        private int servingSize;
        private int servings;
        private int calories = 0;
        private int fat = 0;
        private int soduim = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }
        public Builder calories(int calories){
            this.calories = calories;
            return this;
        }
        public Builder servings(int fat){
            this.fat = fat;
            return this;
        }
        public Builder soduim(int soduim){
            this.soduim = soduim;
            return this;
        }
        public Builder carbohydrate(int carbohydrate){
            this.carbohydrate = carbohydrate;
            return this;
        }

        public NurtritionFacts build(){
            return new NurtritionFacts(this);
        }
    }
    private NurtritionFacts(Builder builder){
        this.calories  = builder.calories;
        this.carbohydrate = builder.carbohydrate;
        this.fat = builder.fat;
        this.servings = builder.servings;
        this.servingSize = builder.servingSize;
        this.soduim = builder.soduim;
    }

    @Override
    public String toString() {
        return "NurtritionFacts{" +
                "servingSize=" + servingSize +
                ", servings=" + servings +
                ", calories=" + calories +
                ", fat=" + fat +
                ", soduim=" + soduim +
                ", carbohydrate=" + carbohydrate +
                '}';
    }
}
