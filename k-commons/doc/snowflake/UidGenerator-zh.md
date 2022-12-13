# bit state

### 介绍

一个基于位运算的状态管理工具

### 软件架构

利用位运算，以及java的枚举类型，管理状态

### 安装教程

#### 1、编译并发布到本地仓库

```java
        gradle clean build publishToMavenLocal-x test
```

#### 2、gradle (gradle.org)

```java

implementation'io.github.kylin-hunter:k-commons:1.0.7'

```

#### 3、maven (maven.apache.org)

```java

<dependency>
<groupId>io.github.kylin-hunter</groupId>
<artifactId>io.github.kylin-hunter:k-commons</artifactId>
<version>1.0.7</version>
</dependency>

```

### 使用说明

#### 1. 枚举状态管理

##### 1.1 api

```java

/**
 * @param type 业务类型
 * @param workerId 机器编号
 * @param datacenterId 机房编号
 * @title UidGenerator 构造器
 * @description
 * @author BiJi'an
 * @date 2022-12-11 16:19
 */
public UidGenerator(long type,long workerId,long datacenterId);
/**
 * @param sequenceBits  序列号 占的bit位数
 * @param typeBits 业务类型 占的bit位数
 * @param workerIdBits 机器编号 占的bit位数
 * @param datacenterIdBits 机房编号 占的bit位数
 * @param type 业务类型
 * @param workerId 机器编号
 * @param datacenterId 机房编号
 * @title UidGenerator 构造器
 * @description
 * @author BiJi'an
 * @date 2022-12-11 16:20
 */
public UidGenerator(int sequenceBits,int typeBits,int workerIdBits,int datacenterIdBits,
        long type,long workerId,
        long datacenterId);

/**
 * @return long
 * @title 获取下一个uid
 * @description
 * @author BiJi'an
 * @date 2022-12-11 00:39
 */
public synchronized long nextId();


/**
 * @param uid uid
 * @return io.github.kylinhunter.commons.uid.UidInfo
 * @title 通过uid 反解出uid的信息
 * @description
 * @author BiJi'an
 * @date 2022-12-11 16:30
 */

```

##### 1.2 示例1

###### 1.2.1 代码

```java
UidGenerator worker = new UidGenerator(12, 2, 2, 2, 1, 2, 3);
        for (int i = 0; i < 10; i++) {
        long nextId = worker.nextId();
        System.out.println(nextId);
        UidInfo parse = worker.parse(nextId);
        System.out.println(parse);

        }
```

###### 1.2.2 打印结果

```java
    AbstractBitState[bitStates[0]=1]
        AbstractBitState[bitStates[0]=11]
        AbstractBitState[bitStates[0]=111]
        AbstractBitState[bitStates[0]=10]

```

##### 1.3 示例2

###### 1.3.1 代码

```java

// 定义一个基于枚举类型的状态
public enum TestState2 {
    STATE1,
    STATE2,
    STATE3,
    STATE4,
    STATE5,
    STATE6,
    STATE7,
    STATE8,
    STATE9,
    STATE10,
    STATE11,
    STATE12,
    STATE13,
    STATE14,
    STATE15,
    STATE16,
    STATE17,
    STATE18,
    STATE19,
    STATE20,
    STATE21,
    STATE22,
    STATE23,
    STATE24,
    STATE25,
    STATE26,
    STATE27,
    STATE28,
    STATE29,
    STATE30,
    STATE31,
    STATE32,
    STATE33,
    STATE34,
    STATE35,
    STATE36,
    STATE37,
    STATE38,
    STATE39,
    STATE40,
    STATE41,
    STATE42,
    STATE43,
    STATE44,
    STATE45,
    STATE46,
    STATE47,
    STATE48,
    STATE49,
    STATE50,
    STATE51,
    STATE52,
    STATE53,
    STATE54,
    STATE55,
    STATE56,
    STATE57,
    STATE58,
    STATE59,
    STATE60,
    STATE61,
    STATE62,
    STATE63,
    STATE64,
    STATE65
}

// 继承 io.github.kylinhunter.commons.state.AbstractBitState 类
public static class TestStateBitState2 extends AbstractBitState<TestState> {

}

    TestStateBitState2 abstractBitState = new TestStateBitState2();
    abstractBitState.setState(TestState2.STATE1);
            abstractBitState.setState(TestState2.STATE63);
            System.out.println(abstractBitState);
            assertTrue(abstractBitState.hasState(TestState2.STATE1));
            assertFalse(abstractBitState.hasState(TestState2.STATE2));
            assertFalse(abstractBitState.hasState(TestState2.STATE3));

            assertTrue(abstractBitState.hasState(TestState2.STATE63));
            assertFalse(abstractBitState.hasState(TestState2.STATE64));
            assertFalse(abstractBitState.hasState(TestState2.STATE65));

            abstractBitState.setState(TestState2.STATE2);
            abstractBitState.setState(TestState2.STATE64);
            System.out.println(abstractBitState);
            assertTrue(abstractBitState.hasState(TestState2.STATE1));
            assertTrue(abstractBitState.hasState(TestState2.STATE2));
            assertFalse(abstractBitState.hasState(TestState2.STATE3));
            assertTrue(abstractBitState.hasState(TestState2.STATE63));
            assertTrue(abstractBitState.hasState(TestState2.STATE64));
            assertFalse(abstractBitState.hasState(TestState2.STATE65));

            abstractBitState.setState(TestState2.STATE3);
            abstractBitState.setState(TestState2.STATE65);
            System.out.println(abstractBitState);
            assertTrue(abstractBitState.hasState(TestState2.STATE1));
            assertTrue(abstractBitState.hasState(TestState2.STATE2));
            assertTrue(abstractBitState.hasState(TestState2.STATE3));
            assertTrue(abstractBitState.hasState(TestState2.STATE63));
            assertTrue(abstractBitState.hasState(TestState2.STATE64));
            assertTrue(abstractBitState.hasState(TestState2.STATE65));

            abstractBitState.removeState(TestState2.STATE1,TestState2.STATE3,TestState2.STATE63,TestState2.STATE65);
            System.out.println(abstractBitState);

            assertFalse(abstractBitState.hasState(TestState2.STATE1));
            assertTrue(abstractBitState.hasState(TestState2.STATE2));
            assertFalse(abstractBitState.hasState(TestState2.STATE3));

            assertFalse(abstractBitState.hasState(TestState2.STATE63));
            assertTrue(abstractBitState.hasState(TestState2.STATE64));
            assertFalse(abstractBitState.hasState(TestState2.STATE65));

```

###### 1.3.2 打印结果

```java
    AbstractBitState[bitStates[0]=100000000000000000000000000000000000000000000000000000000000001,bitStates[1]=0]
        AbstractBitState[bitStates[0]=1100000000000000000000000000000000000000000000000000000000000011,bitStates[1]=0]
        AbstractBitState[bitStates[0]=1100000000000000000000000000000000000000000000000000000000000111,bitStates[1]=1]
        AbstractBitState[bitStates[0]=1000000000000000000000000000000000000000000000000000000000000010,bitStates[1]=0]

```

### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
