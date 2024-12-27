# 代码同时同步到github和gitee

# 初始化仓库
git init

# 添加远程仓库
git remote add gitee git@gitee.com:bealei/test.git

git remote add github git@github.com:bealei/test.git


# 查看仓库
git remote -v


# 删除远程仓库
git remote rm gitee
git remote rm github

# 拉取代码到本地
git pull gitee-typora-theme-bealei master

# 查看文件状态
git status


# 工作区所有新增或修改的文件全部提交到暂存区。
git add .


# 提交暂存区到本地仓库
git commit -m "Initial commit"

# 本地仓库推送到远程仓库
git push gitee 
git push github