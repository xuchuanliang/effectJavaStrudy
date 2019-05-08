package capter06.com.snail;

public class Test {
    public static void main(String[] args){
        for(BasicOperate b:BasicOperate.class.getEnumConstants()){
            System.out.println(b);
        }
    }

}
