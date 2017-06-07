
注解形式:

一:提供者接口:

1.创建一个maven项目

    (1)选择简单项目方式
    (2)选择打包方式为jar
    
2.创建一个接口

    包名:com.chenmeidan.service
    接口名:DubboService
    --------------------------------------------------------
        /**
     * 创建dubbo服务接口
     * @author liudan
     *
     */
    public interface DubboService {
       String getString();
    }
    --------------------------------------------------------


二:提供者实现类:

1.创建一个maven项目

      (1)选择简单项目方式
      (2)选择打包方式为jar
      
2.导入依赖 pom
      (1)sringboot起始依赖 版本1.5.3
         <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.3.RELEASE</version>
            <relativePath />
        </parent>
      (2)dubbo依赖         版本2.5.3
         #一定要除去spring的相关依赖  dubbo自带spring的版本与我们使用的版本冲突
         <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>dubbo</artifactId>
            <version>2.5.3</version>
            <!--除去依赖冲突-->
            <exclusions>
                <exclusion>
                    <artifactId>spring</artifactId>
                    <groupId>org.springframework</groupId>
                </exclusion>
                <exclusion>
                    <artifactId>netty</artifactId>
                    <groupId>org.jboss.netty</groupId>
                </exclusion>
            </exclusions>
        </dependency>

      (3)zookeeper依赖     版本3.4.7

       <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
            <version>3.4.7</version>
              <!--除去相关冲突的依赖-->
            <exclusions>
                <exclusion>
                    <groupId>org.slf4j</groupId>
                    <artifactId>slf4j-log4j12</artifactId>
                </exclusion>
                <exclusion>
                    <groupId>log4j</groupId>
                    <artifactId>log4j</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>com.github.sgroschupf</groupId>
            <artifactId>zkclient</artifactId>
            <version>${zkclient.version}</version>
        </dependency>
        (4)web依赖
         <dependency>
            <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
        (5)dubbo的服务接口依赖
            <!--dubbo 服务接口  -->
            <dependency>
                <groupId>com.liudan.springboot</groupId>
                <artifactId>Springboot-dubbo-interface</artifactId>
                <version>0.0.1-SNAPSHOT</version>
            </dependency>



3.创建一个包,并创建

          提供者接口
          提供者实现类
    包名:com.chenmeidan.springboot.dubbo
    接口
    ---------------------------------------
       public interface FooService {
        String getString();
    }
    --------------------------------------------
    实现类
    -------------------------------------------
    @Service
     //表示是一个dubbo服务 同时也会将FooServiceImpl交给spring容器管理
     //引入的 @Service   是dubbo的注解  不是spring的注解
    public class FooServiceImpl implements FooService {
        //具体业务
        @Override
        public String getString() {
            return "hello";
        }
    }
    -----------------------------------------------------

3.创建dubbo-provider.xml dubbo的配置文件

        (1)提供者应用名
        <dubbo:application name="dubbo-provider" />
        (2)需要暴露的接口
           package表示需要暴露的接口的地址
        <dubbo:annotation package="com.chenmeidan.springboot" />
        (3)将dubbo服务发布zookpeer注册中心
        <dubbo:registry protocol="zookeeper" address="192.168.238.128:2181" />


完整配置文件:
       -------------------------------------------------------------------------
       <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
        xmlns:context="http://www.springframework.org/schema/context"
        xmlns:dubbo="http://code.alibabatech.com/schema/dubbo" xmlns:mvc="http://www.springframework.org/schema/mvc"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
            http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd
            http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
            http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd">

        <!-- 提供方应用名称信息-->
        <dubbo:application name="dubbo-provider" />

        <!-- 要暴露的服务接口 -->
        <!-- package 表示提供方的接口的包的路径-->
        <dubbo:annotation package="com.chenmeidan.springboot" />

        <!-- 使用zookeeper注册中心暴露服务地址 -->
        <!--写自己的zookeeper地址-->
        <dubbo:registry protocol="zookeeper" address="192.168.238.128:2181" />
        <!-- 要暴露的服务接口 -->

    </beans>
   -------------------------------------------------------------------------

4.创建sringboot启动类
     类名:SpringDubboApplication
-------------------------------------------------------------------
    @SpringBootApplication
    //加载dubbo-provider.xml配置文件
    @ImportResource({"classpath:dubbo-provider.xml"})
    public class SpringDubboApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringDubboApplication.class, args);
        }
    }
-------------------------------------------------------------------
5.运行main方法即可

注意:
   1.一定要加载配置文件
   2.springboot启动类与提供者一定要在同一目录
      或者springboot启动类在根目录  提供者在子目录
      不然会导致提供者不能被spring管理 导致服务不能发布
   3.导致duubo和zookeeper一定要出去相关冲突的jar包
------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------
三:消费者

1.创建一个maven项目

      (1)选择简单项目方式
      (2)选择打包方式为jar
2.引入依赖

        (1)需要引入提供者依赖(你自己建立的提供者依赖)
        --------------------------------------
        <dependency>
            <groupId>com.chenmeidan</groupId>
            <artifactId>springboot</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
        ---------------------
        (2)sringboot起始依赖 版本1.5.3
         <parent>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-parent</artifactId>
            <version>1.5.3.RELEASE</version>
            <relativePath />
        </parent>
         (3)web依赖
         <dependency>
            <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-web</artifactId>
         </dependency>
      (4)dubbo的服务接口依赖
        <!--dubbo 服务接口  -->
        <dependency>
            <groupId>com.liudan.springboot</groupId>
            <artifactId>Springboot-dubbo-interface</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>

3.创建业务层

   (1)创建接口
   
   包名:com.chenmeidan.springboot.service
   接口名:DubboServiceImpl
   ----------------------------------------
    public interface DubboService {
    String index();
    }
   -------------------------------------------------
   (2)创建实现类
   
     包名:com.chenmeidan.springboot.service.impl
     类名:DubboServiceImpl
     --------------------------------------

        @Service  //注意是spring的注解
        public class DubboServiceImpl implements DubboService {

            @Reference  //引用dubbo服务
            private FooService fooService;

            public String index() {
                return fooService.getString();
            }
        }
     -------------------------------------
4.创建dubbo-consumer.xml配置文件

    (1)消费者名称
    <dubbo:application name="dubbo-consumer" />
    (2)使用dubbo服务的包名
    <dubbo:annotation package="com.chenmeidan.springboot.service" />
    (3)注册中心
    <dubbo:registry protocol="zookeeper" address="192.168.238.128:2181" />

    完整配置 dubbo.xml
    ------------------------------------------------------
      <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:dubbo="http://code.alibabatech.com/schema/dubbo" xmlns:mvc="http://www.springframework.org/schema/mvc"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.2.xsd
                http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc-4.2.xsd
                http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd
                http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.2.xsd">

            <!-- 提供方应用名称信息，这个相当于起一个名字，我们dubbo管理页面比较清晰是哪个应用暴露出来的 -->
            <dubbo:application name="dubbo-consumer" />
            <!--使用注解方式暴露接口 -->
            <dubbo:annotation package="com.chenmeidan.springboot.service" />

            <!-- 使用zookeeper注册中心暴露服务地址 -->
            <!--写自己的zookeeper地址-->
            <dubbo:registry protocol="zookeeper" address="192.168.238.128:2181" />
            <!-- 要暴露的服务接口 -->
            <!-- <dubbo:reference interface="com.chenmeidan.springboot.dubbo.FooService"
                id="fooService" /> -->
        </beans>
    ------------------------------------------------------
5.创建控制类:用于调用业务层

   包名:com.chenmeidan.springboot.dubbo
   类名:DubboController
   --------------------------
       @Controller
    public class DubboController {

        @Autowired
        private DubboService dubboService;

        @RequestMapping("/")
        @ResponseBody
        public String index() {
            return dubboService.index();
        }
    }

   ---------------------
6.创建springboot启动类
  类名:SpringDubboApplication
  包名:com.chenmeidan.springboot
  ----------------------------------------
    @SpringBootApplication
    @ImportResource("classpath:dubbo-consumer.xml") //加载dubbo.xml配置文件
    public class SpringDubboApplication {

        public static void main(String[] args) {
            SpringApplication.run(SpringDubboApplication.class, args);
        }
    }
-----------------------------------------

7.执行main方法即可

(1)注意需要  @Controller 与@Service(dubbo) 不能同时使用,否则不能使用


