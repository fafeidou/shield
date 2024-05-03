FROM adoptopenjdk/openjdk8
# 将当前目录下的jar包复制到docker容器的/目录下
COPY arthas-study.jar /usr/local
WORKDIR /usr/local
CMD java -jar arthas-study.jar
