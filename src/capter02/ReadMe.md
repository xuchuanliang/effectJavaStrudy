#创建和销毁对象
- 考虑用静态工厂方法代替构造器
>优点：
>>他们有名称  
>>不必每次调用它们的时候都创建一个新对象  
>>它们可以返回原返回类型的任何子类型的对象  
>>在创建参数化实例时，它们使代码变得更加简洁  

>缺点：  
>>类如果不含公有的或者受保护的构造器，就不能被子类化  
>>它们与其他的静态方法实际上没有任何区别  

- 遇到多个构造参数时要考虑用构建器
>重叠构造器模式可行，但是当有许多参数的时候，客户端代码会很难编写，并且仍然较难以阅读  
>javaBeans模式可以上一中方式的不足，但是在构造过程中，javaBeans可能处于不一致的状态  
>Builder模式  

- 用私有构造器或者枚举类型强化Singleton属性
>单元素的枚举类型已经成为实现Singleton的最佳方法  
``
public enum Singleton{
    INSTANCE;
}
``
- 通过私有构造器强化不可实例化的能力
>企图通过将类做成抽象类来强制该类不可被实例化，这是行不通的。该类可以被子类化，并且该子类也可以被实例化  

- 避免创建不必要的对象
```java
class Test{
    String s = new String("snail");//do not do this
    String s = "snail";//全局只有一个字符串，对象的复用性
}
```
>对于同时提供了静态工厂方法和构造器的不可变类，通常可以使用静态工厂方法而不是构造器，以避免创建不必要的对象
```java
class Test{
    public static void main(String[] args){
        Boolean b1 = Boolean.valueOf(true);//推荐这种方法
        Boolean b2 = true;//存在装箱和拆箱成本
        Boolean b3 = new Boolean(true);//存在对象不复用的问题
    }
}
```
>要优先使用基本类型而不是装箱基本类型，要当心无意识的自动装箱
```java
public class Test{
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
```

- 消除过期的对象引用
```java
/**
 * 内存泄漏：如果一个栈是先增长，然后再收缩，那么从栈中弹出的对象将不会被当做垃圾回收，即使使用栈的程序不再引用这些对象，它们也不会被回收
 * 因为栈中维护着对这些对象的过期引用，过期引用是指永远不会被解除的引用
 */
public class Stack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size==0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length==size){
            elements = Arrays.copyOf(elements,2 * size+1);
        }
    }
}
```
>修复以上问题很简单，只需要在对象过期的时候，清空这些引用即可
```java
public class Stack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }
    //在弹出对象的时候解除引用即可
    public Object pop(){
        if(size==0)
            throw new EmptyStackException();
        Object e = elements[--size];
        elements[size] = null;
        return e;
    }

    private void ensureCapacity(){
        if(elements.length==size){
            elements = Arrays.copyOf(elements,2 * size+1);
        }
    }
}
```
>清空对象引用应该是一种例外，而不是一种规范行为  
>>只要类是自己管理内存，程序员就应该警惕内存泄漏问题  
>>内存泄漏另一个常见的来源是缓存，可以使用WeakHashMap代表缓存  
>>监听器和其他回调  

- 避免终结方法
>终结方法（finalizer）通常是不可预测的，也是很危险的，一般情况下是不必要的
