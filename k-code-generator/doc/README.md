# bit state

### Description

A code generator

### Software Architecture

read table info using jdbc,to generate source code。

### Installation

####1、build and publish to local

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

### Instructions

#### 1. create a table
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

#### 2. create db-config file 

##### 2.1  k-db-config.yaml (put it to classpath)

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

#### 3.  create a template config file 
##### 3.1 k-generator-config.yaml (put it to classpath)

```java
global: # global config
  template_path: '/User/bijian/template'   # template dir,can use current dir: $user.dir$/templates
  output_path: '/User/bijian/output'  # output dir,can use current dir: $user.dir$/output
modules:
  database_name: "kp" # database name
  list:
    - name: 'User' # module name
      context:
        'module_custom_propery': 'module_custom_propery_value'  # a module vairable 
      table:
        name: "test_user" #table name
        skip_columns: #  skip some columns
          - 'delete_flag'
          - 'extend_1'
          - 'extend_2'
          - 'extend_3'
    - name: 'Role' # the second module
      table:
        name: 'test_role'
templates: # tempalte definition
  context:
    'author': 'bijian'  # tempalte variable 
  strategy: # 
    template_encoding: UTF-8 # template encoding
    output_encoding: UTF-8 # output file encoding
  list:
    - name: basic.vm  #template name
      enabled: true
      context:
        'basic_vm_custom_property': 'basic_vm_custom_property_value'  #  template vairable
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #package name
        class_name: k.string_to_camel(module.name,'upper')+'Basic' #class name
        super_class: 'java.util.ArrayList' # super class
        interfaces:  # the interfaces
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java' #  a  file  with extension *.java
    - name: basic_swagger.vm # the sencond template  basic_swagger.vm
      enabled: true
      context:
        'basic_swagger_vm_custom_property': 'basic_swagger_vm_custom_property_value'  # template vairable
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #package name
        class_name: k.string_to_camel(module.name,'upper')+'BasicSwagger' #class name
        super_class: 'java.util.ArrayList'  # super class
        interfaces: # the interfaces
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  #  a  file  with extension *.java
    - name: basic_swagger_widget.vm # the thrid template  basic_swagger_widget.vm
      enabled: true 
      context:
        'basic_swagger_widget_vm_custom_property': 'basic_swagger_widget_vm_custom_property_value'  # template vairable
      strategy:
        package_name: '"com.kylinhunter."+k.string_to_lower(module.name)' #package name
        class_name: k.string_to_camel(module.name,'upper')+'BasicSwaggerWidget' #class name
        super_class: 'java.util.ArrayList'  # super class
        interfaces:# the interfaces
          - java.io.Serializable
          - java.lang.Cloneable
        extension: 'java'  # a  file  with extension *.java
```


#### 4.  Describe the syntax of the template

```java
same with volecity
        
${class.packageName};  =>output pakageName
${class.name} =>output className
${class.imports("import ",";")} =>output imports  start with import,end with;
$!{class.comment} =>output comment of the class
${class.superClass("extends ")}  =>output superclass ,start with 'extends'
${class.interfaces("implements ")} =>output all interfaces ,start with 'implements' 
${date}=>output a date
${class.fields}=>outut field
#call($class.imports.add("io.swagger.annotations.ApiModel")) =>add a class to imports
#call($class.addSnippet("class_before","xxx")) =>add a  code Snippet with name 'class_before' 
${class.snippets('class_before')} =>output the code Snippet with name 'class_before'
${xxx}=>output a vriable xxx


```
#### 5 example1


##### 5.1 difine template basic.vm

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

##### 5.2 exec

```java
new CodeGenerator().execute();
```

##### 5.3 the file generated

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

#### 6 example2 （add swagger）


##### 6.1 define basic_swagger.vm 

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

##### 6.2 exec

```java
new CodeGenerator().execute();
```

##### 6.3 the file generated

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


#### 7 example3 （add swagger ,using,snippets）



##### 7.1 define a code snippet tempalte： swagger.vm

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
##### 7.2 define basic_swagger_widget.vm , using swagger.vm

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

##### 7.3 exec

```java
new CodeGenerator().execute();
```

##### 7.4 the file generated

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


#### License

[Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
