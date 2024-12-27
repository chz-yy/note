# Vue

## vue-cli创建

**ui页面创建**

- 打开命令行输入

  > vue ui

- 选择vue项目管理器

- 自定义，选择ts和组合式api

**命令行创建**

> vue create my-vue3-project

## vue3创建

**命令行**

> npm create vue@latest

## 安装依赖

1. npm下载依赖

   > npm install

2. yarn下载

   > npm install --global yarn

3. yarn下载依赖

   > yarn install

## 可能遇到问题

npm下载慢，可以换国内镜像源，百度一下

用npm要先安装nodejs,百度一下

# npm

> npm config set registry https://registry.npmmirror.com
>
> npm config get registry

# 深拷贝

```vue
showEdit(id) {
      const selectedItem = this.allData.find(e => e.id == id);
      this.form2 = JSON.parse(JSON.stringify(selectedItem));  //深拷贝，避免直接引用导致原数据被更改
      this.dialogFormVisible2 = true;
    },
```

