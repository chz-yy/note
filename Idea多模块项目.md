# Idea多模块项目

1. 创建空的maven项目

2. 创建两个spring boot模块

3. 父项目pom

   ```xml
       <modules>
           <module>gateway</module>  //子项目artifactId
           <module>wesay</module>
       </modules>
   
       <packaging>pom</packaging>
   
       <parent>
           <groupId>org.springframework.boot</groupId>  //spring boot依赖
           <artifactId>spring-boot-starter-parent</artifactId>
           <version>2.6.13</version>
       </parent>
   ```

4. 父pom文件可以写<dependencyManagement> <dependencies> <build> <properties>子项目都能继承

5. 子项目标记父项目

   ```xml
   	<parent>
           <groupId>com.chat</groupId>
           <artifactId>chat-parent</artifactId>
           <version>1.0-SNAPSHOT</version>
       </parent>
   ```

   