#对于所有对象都通用的方法
- 覆盖equals时请遵守约定
>覆盖equals必须遵守通用约定：自反性、对称性、传递性、一致性

-覆盖equals时总要覆盖hashCode 
>相等的对象必须具有相等的散列码

- 使用要覆盖toString

- 谨慎的覆盖clone
