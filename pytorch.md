# pytorch

```python
model = SPAN(3, 3, upscale=2, feature_channels=48).cuda()
 # 加载预训练权重
state_dict = torch.load('/data/x248.pth')
model.load_state_dict(state_dict['params'])
model.eval()
```

```python
def load_image(image_path):
    # 使用PIL加载图像
    image = Image.open(image_path)
    # image = image.resize((256, 256))
    # 转换图像到PyTorch张量
    transform = transforms.Compose([
        transforms.ToTensor(),
        # transforms.Normalize(mean=[0.4488, 0.4371, 0.4040], std=[1.0, 1.0, 1.0])  # 将数据标准化
    ])
    image = transform(image).unsqueeze(0)  # 添加批次维度
    return image
```

