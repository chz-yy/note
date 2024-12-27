# docker操作

在 Docker 容器中，`~/.bashrc` 文件不会在容器启动时自动执行。`~/.bashrc` 主要用于交互式 Bash 会话，通常在用户登录时加载。

```shell
docker save -o your-image-name.tar your-image-name:tag
docker load -i your-image-name.tar
```

 **-it 容器保持运行直到退出 Bash 终端。**

> docker run -d -it pytorch/pytorch bash

**当docker容器中没有运行进程时会自动关闭。使用docker start打开，则可保持其打开**
一个镜像可以run多个容器
**进入正在运行的容器**

> docker exec -it <container_name_or_id> bash



## Docker使用GPU

**用pytorch当基础镜像打开容器，打开gpu,挂载数据，端口，重命名**

> docker run -it --gpus all --shm-size 16G --name chz-pytorch-cuda -p 0.0.0.0:7870:7870 \
> -v /data2/chenhaozhe/data:/data \
> -v /data2/chenhaozhe/data/env:/opt/conda/envs \
> -w /data \
> pytorch/pytorch:2.5.0-cuda12.1-cudnn9-devel /bin/bash

## 在docker中选择conda的虚拟环境

> source /opt/conda/etc/profile.d/conda.sh
>
> conda create --name yolov10
>
> conda activate yolov10

## docker设置中文

> apt-get -y install language-pack-zh-hans
>
> export LANG=zh_CN.UTF-8

# docker镜像源配置

1. 使用编辑器打开配置文件 `/etc/docker/daemon.json`（如果没有该文件，可以新建一个）。

2. 将以下内容粘贴进去：

   ```prolog
   {
     "registry-mirrors": [
       "https://docker.hpcloud.cloud",
       "https://docker.m.daocloud.io",
       "https://docker.unsee.tech",
       "https://docker.1panel.live",
       "http://mirrors.ustc.edu.cn",
       "https://docker.chenby.cn",
       "http://mirror.azure.cn",
       "https://dockerpull.org",
       "https://dockerhub.icu",
       "https://hub.rat.dev"
     ]
   }
   ```

3. 保存文件后，重新启动 Docker 服务：

   ```nsis
   sudo systemctl daemon-reload
   sudo systemctl restart docker
   ```

