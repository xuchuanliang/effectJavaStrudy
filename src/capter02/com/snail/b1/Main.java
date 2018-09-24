package capter02.com.snail.b1;

public class Main {
    public static void main(String[] args){
//        testBuilder();
        test1();
        test2();
    }
    public static void testBuilder(){
        NurtritionFacts nurtritionFacts = new NurtritionFacts.Builder(1,2).calories(3).carbohydrate(4).servings(5).soduim(6).build();
        System.out.print(nurtritionFacts);
    }
    public static void test1(){
        Long sum = 0L;
        long time1 = System.nanoTime();
        for(long i = 0;i<Integer.MAX_VALUE;i++){
            sum += i;
        }
        long time2 = System.nanoTime();
        System.out.println(sum);
        System.out.println(time2-time1);//时间：8968603117
    }
    public static void test2(){
        long sum = 0L;
        long time1 = System.nanoTime();
        for(long i = 0;i<Integer.MAX_VALUE;i++){
            sum += i;
        }
        long time2 = System.nanoTime();
        System.out.println(sum);
        System.out.println(time2-time1);//时间：683136392 差了13倍
    }
}
