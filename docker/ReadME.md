# Docker

### 场景说明
Spring Boot的docker打包上传：

### 配置见pom文件

    <build>
        <finalName>docker</finalName>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <!-- 2.0版本以上，打出来的jar文件非常神奇，在linux系统可以直接使用./app.jar这样类似于启动shell脚本一般的方式启动项目 -->
                <configuration>
                    <executable>true</executable>
                </configuration>
            </plugin>
            <!-- Docker maven plugin -->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0</version>
                <configuration>
                    <!-- 这里是最终生成的docker镜像名称 -->
                    <imageName>jaray/${project.build.finalName}</imageName>
                    <!-- 您可以强制docker覆盖每个新构建的图像标记： -->
                    <forceTags>true</forceTags>
                    <!-- 基础镜像，运行一个springboot应用只需要基础的java环境就行 -->
                    <baseImage>java:8</baseImage>
                    <!-- docker启动的时候执行的命令 -->
                    <entryPoint>["java", "-jar", "/${project.build.finalName}.jar"]</entryPoint>
                    <exposes>1111</exposes>
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <directory>${project.build.directory}</directory>
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                    <dockerHost>http://ip:2375</dockerHost>
                </configuration>
            </plugin>
            <!-- Docker maven plugin -->
        </plugins>
    </build>

### 打包命令

    mvn package docker:build
    
### 注意：

    需要开放2375端口
    
### 查看端口是否开放

    telnet ip 2375
