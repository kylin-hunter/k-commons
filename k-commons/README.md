# k-commons

## Description

a Common tool collection


## Installation

### 1、build and publish to local

```
        gradle clean build publishToMavenLocal-x test
```

### 2、gradle (gradle.org)

```
        implementation 'io.github.kylin-hunter:k-commons:1.0.14' 
        //The latest version can be found in the Maven repository 

```

### 3、maven (maven.apache.org)

```
        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-commons</artifactId>
          <version>1.0.14</version> //The latest version can be found in the Maven repository
        </dependency>

```

## Document

> [bean copy](doc/bean/BeanCopyUtils.md)

> [bit state](doc/state/BitState.md)

> [UID generator(snowflake)](doc/uid/UidGenerator.md)

#### License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
