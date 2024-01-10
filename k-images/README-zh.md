# k-commons

## 介绍

一个操作图像的工具类
参考：
http://www.graphicsmagick.org/utilities.html

## 使用前准备
安装 [GraphicsMagick](http://www.graphicsmagick.org/download.html)


## 安装教程

### 1、编译并发布到本地仓库

```
        gradle clean build publishToMavenLocal-x test
```

### 2、gradle (gradle.org)

```

        implementation 'io.github.kylin-hunter:k-utils:1.0.2'
        //最新版本号可以去中央仓库查找

```

### 3、maven (maven.apache.org)

```
        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-utils</artifactId>
          <version>1.0.2</version>//最新版本号可以去中央仓库查找
        </dependency>

```

## 文档支持

> [BeanCopyUtils](doc/bean/BeanCopyUtils-zh.md)



#### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)