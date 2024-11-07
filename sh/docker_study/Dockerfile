FROM ubuntu:20.04

# 复制不同的启动脚本
COPY ./app/start-prod.sh start-prod.sh
COPY ./app/start-dev.sh start-dev.sh

# 默认情况下选择开发环境的启动脚本
ENV ENTRYPOINT_PATH=/app/start-dev.sh

RUN echo $PWD

# 设置脚本的执行权限
RUN chmod +x ./app/start-prod.sh ./app/start-dev.sh

# 设置 ENTRYPOINT
ENTRYPOINT ["/bin/sh", "-c", "$ENTRYPOINT_PATH"]
