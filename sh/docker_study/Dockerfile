FROM ubuntu:22.04

ENV WORKDIR_PATH=/home/test

# 设置时区为Asia/Shanghai
ENV TZ Asia/Shanghai

# 设置环境变量，避免交互式提示
ENV DEBIAN_FRONTEND=noninteractive



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
