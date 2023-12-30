# k-commons

## 介绍

一个常用的工具类。

## 安装教程

### 1、编译并发布到本地仓库

```
        gradle clean build publishToMavenLocal-x test
```

### 2、gradle (gradle.org)

```

        implementation 'io.github.kylin-hunter:k-commons:1.0.14'
        //最新版本号可以去中央仓库查找

```

### 3、maven (maven.apache.org)

```
        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-commons</artifactId>
          <version>1.0.14</version>//最新版本号可以去中央仓库查找
        </dependency>

```

## 文档支持

> [bean复制](doc/bean/BeanCopyUtils-zh.md) 

> [bit状态管理](doc/state/BitState-zh.md)

> [uid生成器-基于雪花算法](doc/uid/UidGenerator-zh.md)


#### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
