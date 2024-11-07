FROM ubuntu:20.04

# 默认情况下选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=start-dev.sh

WORKDIR /home/test

# 复制不同的启动脚本
COPY ./app/start-prod.sh $WORKDIR/start-prod.sh
COPY ./app/start-dev.sh $WORKDIR/start-dev.sh

RUN ls /home/test

# 设置脚本的执行权限
# RUN chmod +x start-prod.sh start-dev.sh

# 设置 ENTRYPOINT
# ENTRYPOINT ["/bin/sh", "-c", "$ENTRYPOINT_PATH"]
