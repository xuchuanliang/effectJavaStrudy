package capter06.com.snail;

/**
 * 除了基础运算，这是扩展运算
 */
public enum ExtendedOperation implements Operate {
    EXP("^"){
        @Override
        public double apply(double x, double y) {
            return Math.pow(x,y);
        }
    },
    REMAINDER("%") {
        @Override
        public double apply(double x, double y) {
            return x % y;
        }
    };
    private final String symbol;
    ExtendedOperation(String symbol){
        this.symbol = symbol;
    }
}
