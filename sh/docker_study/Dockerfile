FROM ubuntu:20.04

# 设置工作目录
ENV WORKDIR_PATH=/home/test

# 设置时区
ENV TZ Asia/Shanghai

# 防止交互式提示
ENV DEBIAN_FRONTEND=noninteractive

# 默认选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=$WORKDIR_PATH/start-dev.sh

# 替换为国内镜像源
# 替换为阿里云镜像源
RUN sed -i 's|http://archive.ubuntu.com/ubuntu/|http://mirrors.aliyun.com/ubuntu/|g' /etc/apt/sources.list
RUN sed -i 's|http://security.ubuntu.com/ubuntu/|http://mirrors.aliyun.com/ubuntu/|g' /etc/apt/sources.list

# 更新包列表并安装 Python 3 和 pip
RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    apt-get clean

# 设置 Python 3 为默认的 Python 版本
RUN update-alternatives --install /usr/bin/python python /usr/bin/python3 1

# 验证安装
RUN python --version
RUN pip3 --version

WORKDIR $WORKDIR_PATH

# 打印当前工作目录
RUN echo "Current directory: $(pwd)"

# 打印当前目录下的文件列表
RUN echo "Files in current directory:" && ls -l

# 复制启动脚本
COPY ./app/start-prod.sh $WORKDIR_PATH/start-prod.sh
COPY ./app/start-dev.sh $WORKDIR_PATH/start-dev.sh
COPY ./app/hello.py $WORKDIR_PATH/hello.py
# 给脚本赋予可执行权限
RUN chmod +x $WORKDIR_PATH/start-prod.sh $WORKDIR_PATH/start-dev.sh

# 确保 $ENTRYPOINT_PATH 解析是通过 shell 启动
ENTRYPOINT ["/bin/sh", "-c", "exec $ENTRYPOINT_PATH $@"]

CMD ["--help"]
