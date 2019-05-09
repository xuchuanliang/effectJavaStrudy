package capter06.com.snail.b2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xuchuanliangbt
 * @title: AnnoDeal
 * @projectName effectJavaStrudy
 * @description:
 * @date 2019/5/98:15
 * @Version
 */
public class AnnoDeal {
    public static void main(String[] args) throws ClassNotFoundException {
        int test = 0;
        int passed = 0;
        Class clazz = Class.forName(args[0]);
        for(Method m:clazz.getDeclaredMethods()){
            if(m.isAnnotationPresent(Test.class)){
                test++;
                try {
                    m.invoke(null);
                } catch (IllegalAccessException e) {
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
