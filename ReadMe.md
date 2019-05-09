# 引言
- 2019年5月5日 12:45:50 16/303
- 2019年5月6日 19:39:21 64/303
## 复合优先于继承
- 继承打破了封装性，违背了封装原则，有可能导致脆弱性
- 这种自用性是实现细节，不是承诺，不能保证在java平台的所有实现中都保持不变，不能保证随着版本的不同而不发生变化，因此，这样得到的类将会非常脆弱。
- 只有当子类是超类的子类型时，才适合用继承，换句话说，对于两个类A和B，只有当两者之间确实存在is-a的关系时，类B才应该扩展A。
- 继承机制会把超类API中的缺陷传播到子类中，而复合则允许设计新的API来隐藏这些缺陷。
## 要么为继承设计，并提供说明文档，要么就禁止继承
- 好的API文档应当描述一个给定的方法做了什么工作，而不是描述他如何做到
- 为了允许继承，类还必须遵守一些其他约束，构造器决不能调用可被覆盖的方法，无论是直接调用还是间接调用。如果违反了这条规则，很有可能导致程序失败。超类的构造器
在子类的构造器运行之前运行，所以，子类中覆盖版本的方法将会在子类的构造器运行之前就先被调用。如果该覆盖版本的方法依赖于子类构造器所执行的任何初始化工作，该方法
将不会如其搬的执行。

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
#类和接口
- 使类和成员的可访问性最小化
>它可以有效的解除组成系统的各模块之间的耦合关系，使得这些模块可以独立开发、测试、优化、使用、理解和修改。这样可加快系统开发的速度，因为这些模块可以并行开发。他也减轻了维护的负担，因为程序
员可以更快的理解这些模块，并且可以在调试它们的时候可以不影响其他模块。虽然信息隐藏本身无论是对内还是对外，都不会带来更好的性能，但是它可以有效的调节性能：一旦完成一个系统，并通过剖析确定了哪些
模块影响了系统的性能，那些模块就可以进一步优化，而不会影响其他模块的正确性。最后，信息隐藏也降低了构建大型系统的风险，因为即使整个系统不可用，但是这些独立的模块却是可能可用的  

>尽可能地使每个类或者成员不被外界访问  
>实例域绝不能是公有的，包含公有可变域的类并不是线程安全的  

- 在公有类中使用访问而非共有yu
> 如果类可以在它所在的包的外部进行访问，就提供访问方法；如果类是包级私有的，或者是私有的嵌套类，直接暴露他的数据域并没有本质错误  

- 使可变性最小化
> 为了使类称为不可变，要遵循下面五条规则：
>>不要提供任何会修改对象状态的方法  
>>保证类不会被扩展  
>>使所有的域都是final的  
>>使所有的域都成为私有的
>>确保对于任何可变组件的互斥访问

- 复合优先于继承
>继承打破了封装性，子类依赖于其超类中特定功能的实现细节。超类的实现可能随着发行版本的不同而有所变化，如果真的发生变化，子类可能会遭到破坏，因此子类必须要跟着超类的更新而演变，除非
超类是专门为了扩展而设计，并且有很好的文档说明。  
>导致子类脆弱的另一个相关原因是，他们的超类在后序的发行版本中可以获得新的方法。  
>避免方法：使用复合。不用扩展现有的类，而是在新类中增加一个私有域

- 要么为继承设计，并提供文档说明，要么就禁止继承
>为了允许继承，类必须遵循一些约束，构造器决不能调用可被覆盖的方法，无论是直接调用还是间接调用。如果违反了这个规则，则可能导致程序失败。超类的构造器在子类的构造器之前运行，所以，
子类中覆盖版本的方法将会在子类的构造器运行之前就先被调用。

- 接口优于抽象类
>虽然接口不允许包含方法的实现，但是接口来定义类型并不妨碍为程序提供实现上的帮助。通过对你导出的每个重要接口都提供一个抽象的骨架实现类，把接口和抽象类的有点结合起来。接口的左右仍然是定义
类型，但是骨架实现类接管了所有与接口实现相关的工作，按照惯例骨架类被称为AbstractInterface，这里的Interface是指所实现的接口的名字。骨架实现的每秒之处是，他们为抽象类提供了实现上的帮助，
但又不强加抽象类被用作类型定义时所特有的严格限制。如果仅仅需要实现接口的一部分方法就能达到目的，就可以直接通过集成骨架类的方式，这就是简单实现。简单实现就像个骨架实现，这是因为它实现了
接口，并且是为了继承而设计的，但是区别在于它不是抽象的：它是最简单的可能的有效实现。你可以原封不动的使用它，也可以看情况将它子类化。

- 接口只用于定义类型

- 类层次优于标签类
>标签类过于冗长，容易出错，并且效率低下。标签类是类层次的一种简单的仿效。java提供了其他更好的方法来定义能表示多种风格对象的数据类型：子类型化。标签类正是类层次的一种简单的仿效

- 用函数对象表示策略


- 优先考虑静态成员类

# 泛型
- 请不要在新代码中使用原生态类型
>可以将List<String>传递给类型List的参数，但是不能将它传给类型List<Object>的参数，泛型有子类型化的规则，List<String>是原生态类型List的一个子类型，而不是参数化类型List<Object>的子类型。

- 消除非受检警告

- 列表优先于数组
```java
enum Test {

    MONDAY(PayType.WEEKDAY)
    ;
    private final PayType payType;

    Test(PayType payType) {
        this.payType = payType;
    }

    double pay(double d1,double d2){
        return this.payType.pay(d1,d2);
    }

    private enum PayType{
        WEEKDAY{
            @Override
            double overtimePay(double hrs,double payRate){
                return 0;
            }
        },
        WEEKEND{
            @Override
            double overtimePay(double hrs,double payRate){
                return 0;
            }
        };
        abstract double overtimePay(double hrs,double payRate);
        double pay(double hoursWork,double payRate){
            return overtimePay(hoursWork,payRate);
        }
    }
}
```
- 用实例域代替枚举的序数，不要使用枚举的ordinal方法

- 用EnumSet代替位域

- 用接口模拟可伸缩的枚举


2019年5月8日 20:10:21 137/303
2019年5月8日 21:58:49 147/303
2019年5月9日 08:26:09 154/303