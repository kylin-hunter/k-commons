# bit state

### 介绍

代码生成工具

### 软件架构

利用jdbc读取表信息，生成源代码

### 安装教程

#### 1、编译并发布到本地仓库

```java
        gradle clean build publishToMavenLocal-x test
```

#### 2、gradle (gradle.org)

```java

        implementation 'io.github.kylin-hunter:k-code-generator:1.0.1'

```

#### 3、maven (maven.apache.org)

```java

        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-code-generator</artifactId>
          <version>1.0.1</version>
        </dependency>

```

### 使用说明

#### 1.  创建一个表 test_user
```java

CREATE TABLE IF NOT EXISTS `test_user`
(
    `id`          bigint(20)     NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
    `name`        varchar(64)    NOT NULL DEFAULT '' COMMENT 'user name ',
    `birth`       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'birthday',
    `age`         int            NOT NULL DEFAULT 0 COMMENT 'age',
    `height`      float(9, 2)    NOT NULL DEFAULT 0 COMMENT 'height',
    `weight`      double(19, 2)  NOT NULL DEFAULT 0 COMMENT 'weight',
    `money`       decimal(20, 2) NOT NULL DEFAULT 0 COMMENT 'money',
    `address`     varchar(512)   NOT NULL DEFAULT 0 COMMENT 'address',
    `delete_flag` int            NOT NULL DEFAULT 0 COMMENT 'is deleted',
    `sex`         tinyint(2)     NOT NULL DEFAULT 0 COMMENT '0 unkown 1 male 2 female',
    `role_id`     bigint(20)     NOT NULL DEFAULT 0 COMMENT '角色 ID',
    `extend_1`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段1',
    `extend_2`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段2',
    `extend_3`    varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段3',
    PRIMARY KEY (`id`),
    constraint test_user_role_fk
        foreign key (role_id) references test_role (id)
) comment='the user'


```

#### 2.  创建数据库配置文件

##### 2.1 k-db-config.yaml (放到classpath下)

```java

datasources:
  - driverClassName: 'com.mysql.cj.jdbc.Driver'
    jdbcUrl: 'jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai'
    username: 'root'
    password: 'root'
    pool:
      minimumIdle: 1
      maximumPoolSize: 10
      connectionTimeout: 30000 # default 30000
      idleTimeout: 600000 # default: 600000 (10 minutes)
      maxLifetime: 1800000  # default: 1800000 (30 minutes
      validationTimeout: 5000 # default: 5000
    dataSourceProperties:
      cachePrepStmts: true
      prepStmtCacheSize: 250
      prepStmtCacheSqlLimit: 2048
```

#### 3.  模板配置文件
##### 3.1 k-generator-config.yaml (放到classpath下)

```java
global: # 全局配置
  template_path: '/User/bijian/template'   # 模板所在目录，可以使用当前目录， 例如 $user.dir$/templates
  output_path: '/User/bijian/output'  # 生成的源码所在目录，可以使用当前目录， 例如 $user.dir$/output
modules:
  database_name: "kp" # 数据库的名字
  list:
    - name: 'User' # 模块的名字
      context:
        'module_custom_propery': 'module_custom_propery_value'  # 自定义一个模板的变量
      table:
        name: "test_user" #表名
        skip_columns: #  屏蔽某些表的列
          - 'delete_flag'
          - 'extend_1'
          - 'extend_2'
          - 'extend_3'
    - name: 'Role' # 支持定义多个模块，这是第二个模块
      table:
        name: 'test_role'
templates: # 模板定义
  context:
    'author': 'bijian'  # 模板的全局变量  定一个作者 
  strategy: # 
    template_encoding: UTF-8 # 模板编码
    output_encoding: UTF-8 # 生成的源代码编码
  list:
    - name: basic.vm  #定义一个模板 basic.vm
      enabled: true
      context:
        'basic_vm_custom_property': 'basic_vm_custom_property_value'  #  模板的自定义变量
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #根据模块名字定义包名
        class_name: k.string_to_camel(module.name,'upper')+'Basic' #根据模块的名字定义类名
        super_class: 'java.util.ArrayList' # 父类
        interfaces:  # 接口
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java' # 生成文件的扩展名
    - name: basic_swagger.vm #定义第二个模板 basic_swagger.vm
      enabled: true
      context:
        'basic_swagger_vm_custom_property': 'basic_swagger_vm_custom_property_value'  # 模板的自定义变量
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #根据模块名字定义包名
        class_name: k.string_to_camel(module.name,'upper')+'BasicSwagger' #根据模块的名字定义类名
        super_class: 'java.util.ArrayList'  # 父类
        interfaces: # 接口
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  # 生成文件的扩展名
    - name: basic_swagger_widget.vm
      enabled: true
      context:
        'basic_swagger_widget_vm_custom_property': 'basic_swagger_widget_vm_custom_property_value'  # 模板的自定义变量
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #根据模块名字定义包名
        class_name: k.string_to_camel(module.name,'upper')+'BasicSwaggerWidget' #根据模块的名字定义类名
        super_class: 'java.util.ArrayList'  # 父类
        interfaces:# 接口
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  # 生成文件的扩展名
```


#### 4.  模板语法介绍

```java
默认以volecity语法为准
        
${class.packageName};  =>包名
${class.name} =>类名
${class.imports("import ",";")} =>输出所有的引用包， import开头 ;结尾 
$!{class.comment} =>类的注释，默认为数据库表的注释
${class.superClass("extends ")}  =>输出父类， extends开头
${class.interfaces("implements ")} =>输出所有接口， implements开头
${date}=>日期
${class.fields}=>输出所有的类属性
#call($class.imports.add("io.swagger.annotations.ApiModel")) =>添加类引用
#call($class.addSnippet("class_before","xxx")) =>添加一个代码片段，片段名字是 class_before
${class.snippets('class_before')} =>输出名为 class_before 的 代码片段
${xxx}=>输出引用自定义的变量xxx


```
#### 5 示例1


##### 5.1 定义模板 basic.vm

```java
package ${class.packageName};
${class.imports("import ",";")}
 /**
  * @description  $!{class.comment}
  * @author ${author}
  * @since ${date}
  * ${basic_vm_custom_property}
  * ${module_custom_propery}
  **/
 public class ${class.name} ${class.superClass("extends ")} ${class.interfaces("implements ")}{
#foreach($field in ${class.fields})
 private ${field.type} ${field.name}; // ${field.comment}
#end
}


```

##### 5.2 执行代码生成

```java
new CodeGenerator().execute();
```

##### 5.3 最终生成的源代码

```java
package com.kylinhunter.user;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Cloneable;
import java.lang.Long;
import java.lang.String;
import java.sql.Timestamp;
import java.lang.Integer;
import java.lang.Float;
import java.lang.Double;
import java.math.BigDecimal;
/**
 * @description  the user
 * @author bijian
 * @since 2023-02-23
 * basic_vm_custom_property_value
 * module_custom_propery_value
 **/
public class UserBasic extends ArrayList implements Serializable,Cloneable{
    private Long id; // primary unique id
    private String name; // user name
    private Timestamp birth; // birthday
    private Integer age; // age
    private Float height; // height
    private Double weight; // weight
    private BigDecimal money; // money
    private String address; // address
    private Integer sex; // 0 unkown 1 male 2 female
    private Long role_id; // 角色 ID
}


```

#### 6 示例2 （增加swagger功能支持）


##### 6.1 定义模板 basic_swagger.vm 

```java
package ${class.packageName};
${class.imports("import ",";")}
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  $!{class.comment}
 * @author ${author}
 * @since ${date}
 * ${basic_swagger_vm_custom_property}
 * ${module_custom_propery}
 **/
@ApiModel(value="${class.name}", description="$!{class.comment}")
public class ${class.name} ${class.superClass("extends ")} ${class.interfaces("implements ")}{
#foreach($field in ${class.fields})
@ApiModelProperty(value = "${field.comment}")
    private ${field.type} ${field.name};
#end
}



```

##### 6.2 执行代码生成

```java
new CodeGenerator().execute();
```

##### 6.3 最终生成的源代码

```java
package com.kylinhunter.user;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Cloneable;
import java.lang.Long;
import java.lang.String;
import java.sql.Timestamp;
import java.lang.Integer;
import java.lang.Float;
import java.lang.Double;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the user
 * @author bijian
 * @since 2023-02-23
 * basic_swagger_vm_custom_property_value
 * module_custom_propery_value
 **/
@ApiModel(value="UserBasicSwagger", description="the user")
public class UserBasicSwagger extends ArrayList implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "user name")
    private String name;
    @ApiModelProperty(value = "birthday")
    private Timestamp birth;
    @ApiModelProperty(value = "age")
    private Integer age;
    @ApiModelProperty(value = "height")
    private Float height;
    @ApiModelProperty(value = "weight")
    private Double weight;
    @ApiModelProperty(value = "money")
    private BigDecimal money;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "0 unkown 1 male 2 female")
    private Integer sex;
    @ApiModelProperty(value = "角色 ID")
    private Long role_id;
}



```


#### 7 示例3 （增加swagger功能支持+利用 snippets 功能进行抽象）



##### 7.1 利用 snippets 定义swagger代码抽象片段  swagger.vm

```java

#parse("io/github/kylinhunter/commons/generator/common.vm")
##add  package import
#call($class.imports.add("io.swagger.annotations.ApiModel"))
#call($class.imports.add("io.swagger.annotations.ApiModelProperty"))
##  add  annotation before the class
#set( $classAnnotation ='@ApiModel(value="'+${class.name}+'", description="'+$!{class.comment}+'")' )
#call($class.addSnippet("class_before",$classAnnotation))
#foreach($field in ${class.fields})
#set( $fieldAnnotation =  '@ApiModelProperty(value = "'+${field.comment}+'")')
#call($field.addSnippet("field_before",$fieldAnnotation) )
#end


```
##### 7.2 定义模板 basic_swagger_widget.vm ,引用 swagger.vm

```java

#parse("commons/swagger.vm")
package ${class.packageName};
${class.imports("import ",";")}

/**
* @description  $!{class.comment}
* @author ${author}
* @since ${date}
* ${basic_swagger_widget_vm_custom_property}
* ${module_custom_propery}
**/
${class.snippets('class_before')}
public class ${class.name} ${class.superClass("extends ")} ${class.interfaces("implements ")}{
#foreach($field in ${class.fields})
    ${field.snippets('field_before')}
    private ${field.type} ${field.name};
#end

}



```

##### 7.3 执行代码生成

```java
new CodeGenerator().execute();
```

##### 7.4 最终生成的源代码

```java
package com.kylinhunter.user;
import java.util.ArrayList;
import java.io.Serializable;
import java.lang.Cloneable;
import java.lang.Long;
import java.lang.String;
import java.sql.Timestamp;
import java.lang.Integer;
import java.lang.Float;
import java.lang.Double;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the user
 * @author bijian
 * @since 2023-02-23
 * basic_swagger_widget_vm_custom_property_value
 * module_custom_propery_value
 **/
@ApiModel(value="UserBasicSwaggerWidget", description="the user")
public class UserBasicSwaggerWidget extends ArrayList implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "user name")
    private String name;
    @ApiModelProperty(value = "birthday")
    private Timestamp birth;
    @ApiModelProperty(value = "age")
    private Integer age;
    @ApiModelProperty(value = "height")
    private Float height;
    @ApiModelProperty(value = "weight")
    private Double weight;
    @ApiModelProperty(value = "money")
    private BigDecimal money;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "0 unkown 1 male 2 female")
    private Integer sex;
    @ApiModelProperty(value = "角色 ID")
    private Long role_id;

}



```


### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
