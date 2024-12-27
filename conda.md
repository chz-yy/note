# conda

## conda操作

```shell
conda env remove --name myenv
```



```shell
 /root/.condarc 配置文件
 envs_dirs:
  - D:\Anaconda3\envs
 pkgs_dirs:  //缓存
  - D:\Anaconda3\pkgs
  
channels:
  - defaults
show_channel_urls: true
default_channels:
  - http://mirrors.aliyun.com/anaconda/pkgs/main
  - http://mirrors.aliyun.com/anaconda/pkgs/r
  - http://mirrors.aliyun.com/anaconda/pkgs/msys2
custom_channels:
  conda-forge: http://mirrors.aliyun.com/anaconda/cloud
  msys2: http://mirrors.aliyun.com/anaconda/cloud
  bioconda: http://mirrors.aliyun.com/anaconda/cloud
  menpo: http://mirrors.aliyun.com/anaconda/cloud
  pytorch: http://mirrors.aliyun.com/anaconda/cloud
  simpleitk: http://mirrors.aliyun.com/anaconda/cloud
```

# CUDA

## cuda下载

> wget https://developer.download.nvidia.com/compute/cuda/12.2.2/local_installers/cuda_12.2.2_535.104.05_linux.run
> sudo sh cuda_12.2.2_535.104.05_linux.run
>
> export PATH=/usr/local/cuda-12.2/bin${PATH:+:${PATH}}
> export LD_LIBRARY_PATH=/usr/local/cuda-11.8/lib64${LD_LIBRARY_PATH:+:${LD_LIBRARY_PATH}}

安装缺少的库
cuda有用户协议，重新拉下窗口出现可以输入的命令行，输入accept,enter,空格不选driver,选择install，enter

## 显卡状态检查

在docker中可以使用这个命令检查是否能访问gpu

> nvidia-smi 

