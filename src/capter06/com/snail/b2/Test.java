package capter06.com.snail.b2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xuchuanliangbt
 * @title: Test
 * @projectName effectJavaStrudy
 * @description:
 * @date 2019/5/98:14
 * @Version
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {
}
