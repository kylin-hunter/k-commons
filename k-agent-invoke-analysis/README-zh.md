# bit state

### 介绍

一个代码生成工具

### 软件架构

利用jdbc读取表信息，生成源代码

### 安装教程

#### 1、编译并发布到本地仓库

```java
        gradle clean build publishToMavenLocal-x test
```

#### 2、gradle (gradle.org)

```java

        implementation 'io.github.kylin-hunter:k-code-generator:1.0.2'

```

#### 3、maven (maven.apache.org)

```java

        <dependency>
          <groupId>io.github.kylin-hunter</groupId>
          <artifactId>k-code-generator</artifactId>
          <version>1.0.2</version>
        </dependency>

```

### 使用说明

#### 1.  创建一个表 test_user
```java

CREATE TABLE IF NOT EXISTS `test_user`(
        `id`                  bigint(20)     NOT NULL COMMENT 'primary unique id' AUTO_INCREMENT,
        `name`                varchar(64)    NOT NULL DEFAULT '' COMMENT 'user name ',
        `birth`               datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'birthday',
        `leave_company_time`  timestamp      NOT NULL COMMENT 'the time leave company',
        `join_company_date`   date           NOT NULL COMMENT 'what time to join the company',
        `work_time_work_time` time           NOT NULL COMMENT 'what time to work ervery moring',
        `work_hours`          int            NOT NULL DEFAULT 0 COMMENT 'how many hours to work everyday',
        `age`                 smallint       NOT NULL DEFAULT 0 COMMENT 'age',
        `height`              float(9, 2)    NOT NULL DEFAULT 0 COMMENT 'height',
        `weight`              double(19, 2)  NOT NULL DEFAULT 0 COMMENT 'weight',
        `money_income`        decimal(20, 2) NOT NULL DEFAULT 0 COMMENT 'all money income',
        `money_spend`         decimal(19, 0) NOT NULL DEFAULT 0 COMMENT 'the money spent',
        `address`             varchar(512)   NOT NULL DEFAULT 0 COMMENT 'address',
        `delete_flag`         tinyint(1)     NOT NULL DEFAULT 0 COMMENT 'is deleted',
        `sex`                 tinyint(2)     NOT NULL DEFAULT 0 COMMENT '0 unkown 1 male 2 female',
        `role_id`             bigint(20)     NOT NULL DEFAULT 0 COMMENT '角色 ID',
        `extend_1`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段1',
        `extend_2`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段2',
        `extend_3`            varchar(256)   NOT NULL DEFAULT 0 COMMENT '预留字段3',
        PRIMARY KEY (`id`),
        constraint test_user_role_fk
        foreign key (role_id) references test_role (id)
) comment ='the user'


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
    properties:
      cachePrepStmts: true
      prepStmtCacheSize: 250
      prepStmtCacheSqlLimit: 2048
```

#### 3.  创建模板的配置文件
##### 3.1 k-generator-config.yaml (放到classpath下)

```java
global: # 全局配置
  template_path: '/User/bijian/template'   # 模板所在目录，可以使用当前目录， 例如 $user.dir$/templates
  output:
    path: '/User/bijian/output'    #   生成的源码所在目录，可以使用当前目录， 例如 $user.dir$/output
    #  output_path: '/Users/bijian/workspace_gitee/k-commons/k-generator/output'
    auto_clean: true # 自动清理上一次生成结果
    auto_create: true # 如果目录不存在自动生成输出目录       
modules:
  database:
    name: "kp"
    sql_types: # 支持一些自定义的sql类型映射（可选）
      "datetime": "java.time.LocalTime"  # 自定义：datetime类型, 映射为 java.time.LocalTime
      "smallint": "java.lang.Short"  # 自定义： smallint 类型, 映射为 java.lang.Short
      "(type=='decimal' && size<=19 && precision==0)": "java.lang.Long"
      # decimal(size<=19,precision==0), 会被映射为 java.lang.Long 例如 decimal(10,0) 就会被映射为  java.lang.Long  
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
        column_types: # 支持自定义 column 的映射类型（可选）
          "role_id": "java.lang.Integer"  # role_id 列 被映射为 java.lang.Integer
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
        package_name: '"com.kylinhunter."+k.str_lower(module.name)' #根据模块名字定义包名
        class_name: k.str_camel(module.name,'upper')+'Basic' #根据模块的名字定义类名
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
        package_name: '"com.kylinhunter."+k.str_lower(module.name)' #根据模块名字定义包名
        class_name: k.str_camel(module.name,'upper')+'BasicSwagger' #根据模块的名字定义类名
        super_class: 'java.util.ArrayList'  # 父类
        interfaces: # 接口
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  # 生成文件的扩展名
    - name: basic_swagger_snippet.vm
      enabled: true
      context:
        'basic_swagger_snippet_vm_custom_property': 'basic_swagger_snippet_vm_custom_property_value'  # 模板的自定义变量
      strategy:
        package_name: '"com.kylinhunter."+k.str_lower(module.name)' #根据模块名字定义包名
        class_name: k.str_camel(module.name,'upper')+'BasicSwaggerSnippet' #根据模块的名字定义类名
        super_class: 'java.util.ArrayList'  # 父类
        interfaces:# 接口
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  # 生成文件的扩展名
```


#### 4.  模板语法介绍和使用

模板的语法参考volecity的语法

##### 4.1 基本使用


基本示例       | 释义
------------- | -------------
${class.packageName} |输出包名
${class.name} | 输出类名
${class.imports("import ",";")} | 输出所有的引用包， 'import '开头 ;结尾，自动回车，简写为${class.imports}
$!{class.comment}  | 类的注释，默认为数据库表的注释
${class.superClass("extends ")} | 输出父类， extends开头 ,简写为 ${class.superClass}
${class.interfaces("implements ")}  | 输出所有接口， 'implements '开头，简写为 ${class.interfaces}
#foreach($field in ${class.fields}) <br>&nbsp;&nbsp;${field.comment}<br>&nbsp;&nbsp;${field.type}<br>&nbsp;&nbsp;${field.name}<br>#end  |<li>${class.fields} => 遍历输出所有类型<br><li>${field.comment}=>属性注释，来自数据库的列注释<br><li>${field.type}==>属性类型，例如 String Long等等<br><li>${field.name}==>属性名，来自数据库列的名字（驼峰形式）
#call($class.imports.add("io.swagger.annotations.ApiModel"))  | 添加一个import引用类到文件头部
#call($class.snippets.add("class_before","xxx")) |定义一个代码片段，片段名字是 class_before
${class.snippets('class_before')} |输出名为 class_before 的 代码片段


##### 4.2 velocity GenericTools
```java
在模板中，你可以使用velocity GenericTools
官网参考: https://velocity.apache.org/tools/devel/generic.html
默认的配置读取自：io/github/kylinhunter/commons/template/velocity-tools.xml（k-commons.jar)

<tools>
  <toolbox scope="application">
    <tool class="org.apache.velocity.tools.generic.DateTool" format="yyyy-MM-dd"></tool>
    <tool class="org.apache.velocity.tools.generic.NumberTool"></tool>
    <tool class="org.apache.velocity.tools.generic.MathTool"></tool>
    <tool class="org.apache.velocity.tools.generic.DisplayTool"></tool>
    <tool class="org.apache.velocity.tools.generic.EscapeTool"></tool>
    <tool class="org.apache.velocity.tools.generic.FieldTool"></tool>
  </toolbox>
</tools>

示例：
${date}=>输出日期

```

##### 4.3 自定义变量

 在上面 k-generator-config.yaml 中，共有有三个位置去可以自定义变量,分别是
 1. modules.list[x].context
 2. templates.context
 3. templates.list[x].context

```java

示例：
templates:
  context:
    'author': 'bijian'  # 定义一个名为 author 的变量
${author}=>输出变量author的值

```
#### 5 示例1 基础模板


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
import java.time.LocalTime;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
/**
 * @description  the user
 * @author bijian
 * @since 2023-02-28
 * basic_vm_custom_property_value
 **/
public class UserBasic extends ArrayList implements Serializable,Cloneable{
    private Long id; // primary unique id
    private String name; // user name
    private LocalTime birth; // birthday
    private Timestamp leaveCompanyTime; // the time leave company
    private Date joinCompanyDate; // what time to join the company
    private Time workTimeWorkTime; // what time to work ervery moring
    private Integer workHours; // how many hours to work everyday
    private Short age; // age
    private Float height; // height
    private Double weight; // weight
    private BigDecimal moneyIncome; // all money income
    private Long moneySpend; // the money spent
    private String address; // address
    private Boolean deleteFlag; // is deleted
    private Integer sex; // 0 unkown 1 male 2 female
    private Integer roleId; // 角色 ID
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
import java.time.LocalTime;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the user
 * @author bijian
 * @since 2023-02-28
 * basic_swagger_vm_custom_property_value
 **/
@ApiModel(value="UserBasicSwagger", description="the user")
public class UserBasicSwagger extends ArrayList implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "user name")
    private String name;
    @ApiModelProperty(value = "birthday")
    private LocalTime birth;
    @ApiModelProperty(value = "the time leave company")
    private Timestamp leaveCompanyTime;
    @ApiModelProperty(value = "what time to join the company")
    private Date joinCompanyDate;
    @ApiModelProperty(value = "what time to work ervery moring")
    private Time workTimeWorkTime;
    @ApiModelProperty(value = "how many hours to work everyday")
    private Integer workHours;
    @ApiModelProperty(value = "age")
    private Short age;
    @ApiModelProperty(value = "height")
    private Float height;
    @ApiModelProperty(value = "weight")
    private Double weight;
    @ApiModelProperty(value = "all money income")
    private BigDecimal moneyIncome;
    @ApiModelProperty(value = "the money spent")
    private Long moneySpend;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "is deleted")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "0 unkown 1 male 2 female")
    private Integer sex;
    @ApiModelProperty(value = "角色 ID")
    private Integer roleId;
}



```


#### 7 示例3 （增加swagger功能支持+利用 snippets 功能进行抽象）



##### 7.1 利用 snippets 定义swagger代码片段  swagger.vm

```java

#parse("io/github/kylinhunter/commons/generator/common.vm")
##添加开发包导入
#call($class.imports.add("io.swagger.annotations.ApiModel"))
#call($class.imports.add("io.swagger.annotations.ApiModelProperty"))
##定一个名为class_before 的代码片段
#set( $classAnnotation ='@ApiModel(value="'+${class.name}+'", description="'+$!{class.comment}+'")' )
#call($class.snippets.add("class_before",$classAnnotation))
#foreach($field in ${class.fields})
#set( $fieldAnnotation =  '@ApiModelProperty(value = "'+${field.comment}+'")')
##定义一个名为field_before 的代码片段
#call($field.snippets.add("field_before",$fieldAnnotation) )
#end


```
##### 7.2 定义模板 basic_swagger_snippet.vm ,引用 swagger.vm

```java

#parse("commons/swagger.vm")
package ${class.packageName};
${class.imports("import ",";")}

/**
* @description  $!{class.comment}
* @author ${author}
* @since ${date}
* ${basic_swagger_snippet_vm_custom_property}
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
import java.time.LocalTime;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description  the user
 * @author bijian
 * @since 2023-02-28
 * basic_swagger_snippet_vm_custom_property_value
 **/
@ApiModel(value="UserBasicSwaggerSnippet", description="the user")
public class UserBasicSwaggerSnippet extends ArrayList implements Serializable,Cloneable{
    @ApiModelProperty(value = "primary unique id")
    private Long id;
    @ApiModelProperty(value = "user name")
    private String name;
    @ApiModelProperty(value = "birthday")
    private LocalTime birth;
    @ApiModelProperty(value = "the time leave company")
    private Timestamp leaveCompanyTime;
    @ApiModelProperty(value = "what time to join the company")
    private Date joinCompanyDate;
    @ApiModelProperty(value = "what time to work ervery moring")
    private Time workTimeWorkTime;
    @ApiModelProperty(value = "how many hours to work everyday")
    private Integer workHours;
    @ApiModelProperty(value = "age")
    private Short age;
    @ApiModelProperty(value = "height")
    private Float height;
    @ApiModelProperty(value = "weight")
    private Double weight;
    @ApiModelProperty(value = "all money income")
    private BigDecimal moneyIncome;
    @ApiModelProperty(value = "the money spent")
    private Long moneySpend;
    @ApiModelProperty(value = "address")
    private String address;
    @ApiModelProperty(value = "is deleted")
    private Boolean deleteFlag;
    @ApiModelProperty(value = "0 unkown 1 male 2 female")
    private Integer sex;
    @ApiModelProperty(value = "角色 ID")
    private Integer roleId;

}




```


### 版权 | License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
