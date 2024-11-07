FROM ubuntu:20.04

ENV WORKDIR_PATH=/home/test

RUN apt update && apt install python3 python3-pip -y

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
