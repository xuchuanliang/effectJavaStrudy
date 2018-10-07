#对于所有对象都通用的方法
- 覆盖equals时请遵守约定
>覆盖equals必须遵守通用约定：自反性、对称性、传递性、一致性

-覆盖equals时总要覆盖hashCode 
>相等的对象必须具有相等的散列码

- 使用要覆盖toString

- 谨慎的覆盖clone
>永远不要让客户端去做任何类库能够替客户完成的事情

- 考虑实现Comparable接口
>comparaTo方法的通用约定于equals方法的相似：将这个对象与指定的对象进行比较。当该对象小于、等于或大于指定对象时，分别返回一个负整数、零或者正整数。
>String中不区分大小写的比较
```java
package capter03.com.snail;

public class Test {
    public static void main(String[] args){
        int result = String.CASE_INSENSITIVE_ORDER.compare("snail","SNAIL");
        System.out.print(result);// result=0
    }
}
```
