FROM ubuntu:20.04

ENV WORKDIR_PATH=/home/test

# 设置时区为Asia/Shanghai
ENV TZ Asia/Shanghai

# 设置环境变量，避免交互式提示
ENV DEBIAN_FRONTEND=noninteractive

# 更新APT源为阿里云镜像源并安装常用工具
RUN echo "deb http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse" > /etc/apt/sources.list \
    && echo "deb-src http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse" >> /etc/apt/sources.list \
    && apt-get update \
    && apt-get install -y --no-install-recommends \
       curl \
       gnupg \
       lsb-release \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# 安装其他依赖包
RUN apt-get update && apt-get install -y \
    build-essential \
    vim \
    git

RUN apt-get update && apt install python3 python3-pip -y

RUN python3 --version && \
    pip3 --version && \
    pip3 install rich && \
    python3 -m rich --version


# 默认情况下选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=$WORKDIR_PATH/start-dev.sh

WORKDIR $WORKDIR_PATH

# 复制不同的启动脚本
COPY ./app/start-prod.sh $WORKDIR_PATH/start-prod.sh
COPY ./app/start-dev.sh $WORKDIR_PATH/start-dev.sh

RUN ls /home/test

# 设置脚本的执行权限
RUN chmod +x $WORKDIR_PATH/start-prod.sh $WORKDIR_PATH/start-dev.sh

# 设置 ENTRYPOINT
ENTRYPOINT ["/bin/sh", "-c", "$ENTRYPOINT_PATH"]
