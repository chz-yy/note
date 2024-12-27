# zookeeper

```bash
docker run -d \
  --name chz-zookeeper \
  -p 2181:2181 \
  -v ./zookeeeper:/data \
  zookeeper
```
## 远程连接
./conf/zoo.cfg   zookeeper-bin目录下
```bash
clientPort=2181                  # 默认的客户端连接端口
clientPortAddress=0.0.0.0        # 绑定到所有网络接口，允许远程连接
```

