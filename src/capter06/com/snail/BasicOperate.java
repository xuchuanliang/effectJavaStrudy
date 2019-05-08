package capter06.com.snail;

/**
 * 实现操作接口的枚举类，每个枚举实现自己的计算方式
 */
public enum BasicOperate implements Operate{
    PLUS("+"){
        @Override
        public double apply(double x, double y) {
            return x+y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x-y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x*y;
        }
    },
    DIVIE("/") {
        @Override
        public double apply(double x, double y) {
            return x/y;
        }
    };
    private final String symbol;
    BasicOperate(String symbol){
        this.symbol = symbol;
    }
}
