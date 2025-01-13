FROM docker.unsee.tech/ubuntu:22.04

# 设置工作目录
ENV WORKDIR_PATH=/home/test

# 设置时区
ENV TZ Asia/Shanghai

# 防止交互式提示
ENV DEBIAN_FRONTEND=noninteractive

# 默认选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=$WORKDIR_PATH/start-dev.sh

# 更换apt源
RUN sed -i 's@/deb.debian.org/@/mirrors.aliyun.com/@g' /etc/apt/sources.list

# 更新apt源
RUN apt-get clean && apt-get update

# 安装python环境
RUN apt-get install -y python3 python3-pip python3-dev \
    build-essential libssl-dev libffi-dev \

WORKDIR $WORKDIR_PATH

# 打印当前工作目录
RUN echo "Current directory: $(pwd)"

# 打印当前目录下的文件列表
RUN echo "Files in current directory:" && ls -l

# 复制启动脚本
COPY ./app/start-prod.sh $WORKDIR_PATH/start-prod.sh
COPY ./app/start-dev.sh $WORKDIR_PATH/start-dev.sh

# 给脚本赋予可执行权限
RUN chmod +x $WORKDIR_PATH/start-prod.sh $WORKDIR_PATH/start-dev.sh

# 确保 $ENTRYPOINT_PATH 解析是通过 shell 启动
ENTRYPOINT ["/bin/sh", "-c", "exec $ENTRYPOINT_PATH $@"]

CMD ["--help"]
