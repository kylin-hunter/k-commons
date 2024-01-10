# k-commons

## Description

A tool  for manipulating images
Reference:
http://www.graphicsmagick.org/utilities.html

## Prerequisites
[Install GraphicsMagick](http://www.graphicsmagick.org/download.html)




## Installation

### 1、build and publish to local

```
        gradle clean build publishToMavenLocal-x test
```

### 2、gradle (gradle.org)

```
        implementation 'io.github.kylin-hunter:k-utils:1.0.2' 
        //The latest version can be found in the Maven repository 

```

### 3、maven (maven.apache.org)

```
        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-utils</artifactId>
          <version>1.0.2</version> //The latest version can be found in the Maven repository
        </dependency>

```

## Document

> [BeanCopyUtils](doc/bean/BeanCopyUtils-en.md)

#### License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)