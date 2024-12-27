# Jupyter

**安装**

```shell
conda install jupyter
```

**配置**

```python
jupyter notebook --generate-config
'/root/.jupyter/jupyter_notebook_config.py'
c.ServerApp.ip = '0.0.0.0'
c.ServerApp.port = 7860
c.ServerApp.allow_remote_access = True
c.ServerApp.open_browser = False
c.ServerApp.password = ''
c.ServerApp.token = ''  # 禁用token
c.ServerApp.disable_check_xsrf = True  # 不推荐关闭 XSRF 检查
c.ServerApp.notebook_dir = '/app/xuexi'
```

**配置代码自动补全**

```shell
pip install jupyter-lsp
pip install python-lsp-server[all]
```

在JupyterLab中Settings的Settings Editor中，找到Code Completion设置，勾选"Enable autocompletion."选项以开启自动提示 。

**启动**

```shell
jupyter notebook --allow-root
jupyter lab --allow-root
```

