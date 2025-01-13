- symbolic-10.2.1-py2.py3-none-linux_x86_64.whl 可以从github下载 https://juejin.cn/post/7436039899428929548#heading-1

```
docker build -f DockerfileBase -t test:base .

docker run -it test:base /bin/bash

python3 app/hello.py

```
