# git操作

1. 配置用户名和邮箱，配置自动保存密码

2. 初始化本地仓库

   > git init

3. 配置gitignore

4. 创建远程仓库

5. 配置ssh的密钥公钥，复制到git账户，22端口不能用，config改成443端口，第一次连会报错，输入yes即可

6. 初始化和远程仓库连接需要先提交一些文件到本地仓库

7. > git status 查看本地未提交文件

8. > git add .   将所有文件添加到暂存区

9. > git commit -m "留言 "  将暂存区文件提交到本地仓库

10. >git remote -v 查看远程仓库地址
    >
    >git push origin 本地chz:chz1远程
    >
    >git pull origin 远程chz:chz1本地
    >
    >git branch -a
    >
    >git merge origin/master 当git pull拉取代码失败时，需要git merge合并代码

