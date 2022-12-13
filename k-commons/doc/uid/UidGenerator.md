# bit state

### Description

A Unique ID Generator Based on Snowflake Algorithm

### Software Architecture

Based on Snowflake Algorithm

### Installation

####1、build and publish to local

```java
        gradle clean build publishToMavenLocal -x test
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

### Instructions

#### 1. generate uid 

##### 1.1 api

```java

/**
 * @param type business  type
 * @param workerId worker id
 * @param datacenterId data center id
 * @title UidGenerator constructor
 * @description
 * @author BiJi'an
 * @date 2022-12-11 16:19
 */
public UidGenerator(long type,long workerId,long datacenterId);
/**
 * @param sequenceBits  sequence no bits
 * @param typeBits business  type bits
 * @param workerIdBits worker id bits
 * @param datacenterIdBits data center id bits
 * @param type business  type
 * @param workerId worker id
 * @param datacenterId data center id
 * @title UidGenerator constructor
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

##### 1.2 exampe 1


###### 1.2.1 code

```java

    // business type =3
    // worker id = 4
    // data center id =1
        UidGenerator worker = new UidGenerator(3, 4, 1);
        for (int i = 0; i < 10; i++) {
          long nextId = worker.nextId();
          System.out.println(nextId + "=>" + worker.parse(nextId));
        }
       
```

###### 1.2.2 print result

```java

        2022-12-13 16:54:26.177 [Test worker] INFO   -  i.g.k.commons.uid.UidGenerator[107]: timestampBits 41, datacenterIdBits 2, workerIdBits 4,typeBits 4, sequenceBits 12
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


### copyright | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
