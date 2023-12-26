# bit state

### 介绍

一个基于雪花算法的唯一ID生成器

### 软件架构

基于雪花算法

### 安装教程

#### 1、编译并发布到本地仓库

```
        gradle clean build publishToMavenLocal -x test
```

#### 2、gradle (gradle.org)

```

    implementation'io.github.kylin-hunter:k-commons:x.x.x'
    //最新版本号可以去中央仓库查找

```

#### 3、maven (maven.apache.org)

```

        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
            <artifactId>io.github.kylin-hunter:k-commons</artifactId>
          <version>x.x.x</version> //最新版本号可以去中央仓库查找
        </dependency>

```

### 使用说明

#### 1. 生成id

##### 1.1 api

```

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
public UidGenerator(int sequenceBits,int typeBits,int workerIdBits,int datacenterIdBits,long type,long workerId,long datacenterId);

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
public UidInfo parse(long uid);

```

##### 1.2 示例1


###### 1.2.1 代码

```

    // 业务代码 3，默认业务代码支持范围 0-15
    // 机器编码 4，默认机器编码支持范围0-15
    // 机房编码 1，默认机房支持范围 0-4
    // 各个编码范围，均可以调整
        UidGenerator worker = new UidGenerator(3, 4, 1);
        for (int i = 0; i < 10; i++) {
          long nextId = worker.nextId();
          System.out.println(nextId + "=>" + worker.parse(nextId));
        }
       
```

###### 1.2.2 打印结果

```

        2022-12-11 22:54:26.177 [Test worker] INFO   -  i.g.k.commons.uid.UidGenerator[107]: timestampBits 41,datacenterIdBits 2, workerIdBits 4,typeBits 4, sequenceBits 12
        161155503589961728=>UidInfo[sequence=0, type=3, workerId=4, datacenterId=1, timestamp=1670921666180/2022-12-13 16:54:26]
        161155503644487680=>UidInfo[sequence=0, type=3, workerId=4, datacenterId=1, timestamp=1670921666193/2022-12-13 16:54:26]
        161155503648681984=>UidInfo[sequence=0, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681985=>UidInfo[sequence=1, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681986=>UidInfo[sequence=2, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681987=>UidInfo[sequence=3, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681988=>UidInfo[sequence=4, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681989=>UidInfo[sequence=5, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681990=>UidInfo[sequence=6, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]
        161155503648681991=>UidInfo[sequence=7, type=3, workerId=4, datacenterId=1, timestamp=1670921666194/2022-12-13 16:54:26]


```


### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
