YOLOV10部署问题

tensorrt转换会报INT64问题和不能识别的节点（mod)。目前未找到方法解决

服务器docker环境

- cuda 12.2
- cudnn 8.9.7
- onnxruntime-gpu 1.17
- pyTorch 2.3.0

推理使用onnx格式可以用gpu加速

还可以使用tensorRT加速，但是tensorRT格式转换问题还未解决

## docker中运行yolov10失败解决

缺少libGL.so.1

> apt-get update && apt-get install libgl1

缺少libgthread-2.0.so.0

> apt-get install libglib2.0-0



