# jvm

> 版本号 - 44=jdk版本

```java
int i=0;
i=i++;  //i=0
int i=0;
i=++i //i=1
字节码指令执行顺序不一样
```

## javap

```sh
java -xvf jar包  解压jar包
javap -v 字节码文件 >test.txt 将字节码解析输出到test.txt
```

[arthas (aliyun.com)](https://arthas.aliyun.com/)

## 查看jvm状态

```java
java -cp sa-jdi.jar sun.jvm.hotspot.HSDB
```

```shell
jps 查看所有java进程id
```

## jvm组成

![image-20241102152354474](images/image-20241102152354474.png)

## 类生命周期

![image-20241017165801091](images\image-20241017165801091.png)

![image-20241017171139198](images\image-20241017171139198.png)

**子类在clint初始化调用前会先调用父类的cliint初始化方法**

## 类加载器分类

![image-20241031155311043](images/image-20241031155311043.png)

## 类加载器

![image-20241031161527285](images/image-20241031161527285.png)

![image-20241102102755661](images/image-20241102102755661.png)

## 双亲委派机制

![image-20241102103703073](images/image-20241102103703073.png)

![image-20241102104505894](images/image-20241102104505894.png)

## 打破双亲委派

![image-20241102110036298](images/image-20241102110036298.png)

1.自定义类加载器

![image-20241102105827386](images/image-20241102105827386.png)

## 热部署

![image-20241102130817029](images/image-20241102130817029.png)

![image-20241102130910936](images/image-20241102130910936.png)

## 运行时数据区

![image-20241102152205838](images/image-20241102152205838.png)

## 栈帧组成

![image-20241102133800790](images/image-20241102133800790.png)

## 栈内存大小

![image-20241102134440294](images/image-20241102134440294.png)

## 堆内存大小

默认max为内存的1/4,total为1/64， total和max设置为一样，减少内存不够时申请分配内存的时间开销

![image-20241102135835798](images/image-20241102135835798.png)

## 方法区内存大小

![image-20241102150102892](images/image-20241102150102892.png)

## 直接内存大小

![image-20241102151851694](images/image-20241102151851694.png)

## 方法区回收

![image-20241102153519054](images/image-20241102153519054.png)

## 堆回收

![image-20241102164341867](images/image-20241102164341867.png)

![image-20241102164741584](images/image-20241102164741584.png)

![image-20241102165951806](images/image-20241102165951806.png)

![image-20241102172110366](images/image-20241102172110366.png)

![image-20241102172154067](images/image-20241102172154067.png)

## 分代GC

![image-20241102185732870](images/image-20241102185732870.png)

![image-20241102190229475](images/image-20241102190229475.png)

![image-20241102190404460](images/image-20241102190404460.png)

![image-20241102183738651](images/image-20241102183738651.png)

**G1垃圾回收**

![image-20241102193102308](images/image-20241102193102308.png)

![image-20241102193339215](images/image-20241102193339215.png)

## 监控

![image-20241107174756884](images/image-20241107174756884.png)

## 内存泄漏

![image-20241107180102566](images/image-20241107180102566.png)
