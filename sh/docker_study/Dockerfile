FROM ubuntu:22.04

# 设置工作目录
ENV WORKDIR_PATH=/home/test

# 设置时区
ENV TZ Asia/Shanghai

# 防止交互式提示
ENV DEBIAN_FRONTEND=noninteractive

# 默认选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=$WORKDIR_PATH/start-dev.sh

WORKDIR $WORKDIR_PATH

# 复制启动脚本
COPY ./app/start-prod.sh $WORKDIR_PATH/start-prod.sh
COPY ./app/start-dev.sh $WORKDIR_PATH/start-dev.sh

# 给脚本赋予可执行权限
RUN chmod +x $WORKDIR_PATH/start-prod.sh $WORKDIR_PATH/start-dev.sh

# 确保 $ENTRYPOINT_PATH 解析是通过 shell 启动
ENTRYPOINT ["/bin/sh", "-c", "exec $ENTRYPOINT_PATH"]
