# 生成 SSH 密钥并将公钥添加到GitHub的步骤

### 1. 生成 SSH 密钥

1. **打开终端**（Terminal）。

2. **生成新的 SSH 密钥对**：

   使用 `ssh-keygen` 命令生成一个新的 SSH 密钥对。通常推荐使用 `ed25519` 算法，它比 `rsa` 更安全。你可以用以下命令生成密钥：

   ```
   ssh-keygen -t ed25519 -C "your_email@example.com"
   ```

   这里的 `"your_email@example.com"` 是你的邮箱地址，它将作为密钥的标签。

3. **按照提示操作**：

   - **选择保存路径**：系统会提示你选择保存密钥的路径。默认路径是 `~/.ssh/id_ed25519`，你可以按回车接受默认路径。
   - **设置密钥密码**：系统会要求你输入一个密码来保护私钥。你可以选择设置密码（增加安全性），也可以直接按回车跳过（这会使密钥没有密码保护）。

   完成后，你会看到类似如下的输出：

   ```
   Your identification has been saved in /home/yourusername/.ssh/id_ed25519.
   Your public key has been saved in /home/yourusername/.ssh/id_ed25519.pub.
   ```

### 2. 添加公钥到 GitHub

1. **复制公钥内容**：

   使用以下命令查看并复制公钥内容：

   ```
   cat ~/.ssh/id_ed25519.pub
   ```

   这条命令会显示公钥的内容，你需要将这些内容复制到剪贴板中。

2. **登录到 GitHub**：

   打开 [GitHub 登录页面](https://github.com/) 并使用你的账户信息登录。

3. **进入 SSH 和 GPG 密钥设置页面**：

   - 点击右上角的头像，选择 **Settings**。
   - 在左侧菜单中选择 **SSH and GPG keys**。

4. **添加新密钥**：

   - 点击 **New SSH key** 按钮。
   - 在 **Title** 字段中输入一个描述性名称（例如，`My Laptop`）。
   - 将刚才复制的公钥粘贴到 **Key** 字段中。
   - 点击 **Add SSH key** 按钮保存。

### 3. 测试 SSH 连接

确保你已经正确配置了 SSH 密钥，使用以下命令测试连接：

```
ssh -T git@github.com
```

如果一切配置正确，你会看到类似以下的信息：

```
Hi username! You've successfully authenticated, but GitHub does not provide shell access.
```

如果出现其他信息或错误，请检查 SSH 密钥是否正确生成，并确保 GitHub 上的公钥正确无误。

通过上述步骤，你应该能够成功生成 SSH 密钥并将公钥添加到 GitHub 账户中。