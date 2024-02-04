

# 集群身份认证与用户鉴权


```dtd

docker run -d --name elasticsearch0 -p 9203:9200 -p 9303:9300 -e "discovery.type=single-node" -e "cluster.name=cluster0" -e  "node.name=cluster0node" -e "xpack.security.enabled=true" docker.elastic.co/elasticsearch/elasticsearch:7.1.0

#运行密码设定的命令，设置ES内置用户及其初始密码。
bin/elasticsearch-setup-passwords interactive

curl -u elastic 'localhost:9203/_cat/nodes?pretty'


```

# 集群内部间的安全通信


```dtd
```
docker run -d --name elasticsearch0 -p 9203:9200 -p 9303:9300 -e "discovery.seed_hosts=192.168.56.112" -e "cluster.initial_master_nodes=cluster0node" -e "cluster.name=cluster_a" -e  "node.name=cluster0node" docker.elastic.co/elasticsearch/elasticsearch:7.1.0

# 生成证书
# 为您的Elasticsearch 集群创建一个证书颁发机构。例如，使用elasticsearch-certutil ca命令：
bin/elasticsearch-certutil ca

#为群集中的每个节点生成证书和私钥。例如，使用elasticsearch-certutil cert 命令：
bin/elasticsearch-certutil cert --ca elastic-stack-ca.p12

#将证书拷贝到 config/certs目录下
elastic-certificates.p12

# 将容器的证书拷贝到宿主机上
docker cp fa0ac54de1c5:/usr/share/elasticsearch/elastic-stack-ca.p12 /root/es-study

docker run -v /root/es-study/elastic-stack-ca.p12:/usr/share/elasticsearch/config/elastic-stack-ca.p12 -d --name elasticsearch1 -p 9204:9200 -p 9304:9300 -e "transport.tcp.port=9303" -e "discovery.seed_hosts=192.168.56.112" -e "cluster.initial_master_nodes=cluster0node" -e "cluster.name=cluster_a" -e  "node.name=cluster1node" -e "xpack.security.enabled=true" -e "xpack.security.transport.ssl.enabled=true" -e "xpack.security.transport.ssl.verification_mode=certificate" -e "xpack.security.transport.ssl.keystore.path=elastic-stack-ca.p12" -e "xpack.security.transport.ssl.truststore.path=elastic-stack-ca.p12" docker.elastic.co/elasticsearch/elasticsearch:7.1.0

docker run -d --name elasticsearch2 -p 9205:9200 -p 9305:9300 -e "transport.tcp.port=9303" -e "discovery.seed_hosts=192.168.56.112" -e "cluster.initial_master_nodes=cluster0node" -e "cluster.name=cluster_a" -e  "node.name=cluster2node" docker.elastic.co/elasticsearch/elasticsearch:7.1.0

docker run -d --name elasticsearch1 -p 9204:9200 -p 9304:9300 -e "discovery.type=single-node" -e "cluster.name=cluster1" -e  "node.name=cluster1node"  -E "xpack.security.enabled=true" -E "xpack.security.transport.ssl.enabled=true" -E "xpack.security.transport.ssl.verification_mode=certificate" -E "xpack.security.transport.ssl.keystore.path=/root/es-study/elastic-stack-ca.p12" -E "xpack.security.transport.ssl.truststore.path=/root/es-study/elastic-stack-ca.p12" docker.elastic.co/elasticsearch/elasticsearch:7.1.0




bin/elasticsearch -E node.name=node0 -E cluster.name=geektime -E path.data=node0_data -E http.port=9200 -E xpack.security.enabled=true -E xpack.security.transport.ssl.enabled=true -E xpack.security.transport.ssl.verification_mode=certificate -E xpack.security.transport.ssl.keystore.path=certs/elastic-certificates.p12 -E xpack.security.transport.ssl.truststore.path=certs/elastic-certificates.p12

bin/elasticsearch -E node.name=node1 -E cluster.name=geektime -E path.data=node1_data -E http.port=9201 -E xpack.security.enabled=true -E xpack.security.transport.ssl.enabled=true -E xpack.security.transport.ssl.verification_mode=certificate -E xpack.security.transport.ssl.keystore.path=certs/elastic-certificates.p12 -E xpack.security.transport.ssl.truststore.path=certs/elastic-certificates.p12


#不提供证书的节点，无法加入
bin/elasticsearch -E node.name=node2 -E cluster.name=geektime -E path.data=node2_data -E http.port=9202 -E xpack.security.enabled=true -E xpack.security.transport.ssl.enabled=true -E xpack.security.transport.ssl.verification_mode=certificate


```


```
## elasticsearch.yml 配置

#xpack.security.transport.ssl.enabled: true
#xpack.security.transport.ssl.verification_mode: certificate

#xpack.security.transport.ssl.keystore.path: certs/elastic-certificates.p12
#xpack.security.transport.ssl.truststore.path: certs/elastic-certificates.p12

```

```
