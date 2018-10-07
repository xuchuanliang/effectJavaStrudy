package capter03.com.snail;

public class Test {
    public static void main(String[] args){
        int result = String.CASE_INSENSITIVE_ORDER.compare("snail","SNAIL");
        System.out.print(result);// result=0
    }
}
