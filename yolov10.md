# yolov10

## 训练

```sh
yolo detect train data=/app/yolov10/ultralytics/cfg/datasets/hatcoco.yaml model=/app/yolov10/ultralytics/cfg/models/v10/yolov10s.yaml epochs=500 batch=256 imgsz=640 device=0

yolo export model=runs/detect/train8/weights/best.pt format=onnx opset=13 simplify

yolo predict model=runs/detect/train8/weights/best.onnx

```

## 格式转换

```python
//将数据转换为yolov10使用的文件夹格式
import xml.etree.ElementTree as ET
from tqdm import tqdm
from ultralytics.utils.downloads import download
from pathlib import Path
import os



def convert_label(path, lb_path, year, image_id):
    def convert_box(size, box):
        dw, dh = 1. / size[0], 1. / size[1]
        x, y, w, h = (box[0] + box[1]) / 2.0 - 1, (box[2] + box[3]) / 2.0 - 1, box[1] - box[0], box[3] - box[2]
        return x * dw, y * dh, w * dw, h * dh

    in_file = open(path / f'VOC{year}/Annotations/{image_id}.xml')
    out_file = open(lb_path, 'w')
    tree = ET.parse(in_file)
    root = tree.getroot()
    size = root.find('size')
    w = int(size.find('width').text)
    h = int(size.find('height').text)

    names = ['hat','person']  # names list
    for obj in root.iter('object'):
        cls = obj.find('name').text
        if cls in names and int(obj.find('difficult').text) != 1:
            xmlbox = obj.find('bndbox')
            bb = convert_box((w, h), [float(xmlbox.find(x).text) for x in ('xmin', 'xmax', 'ymin', 'ymax')])
            cls_id = names.index(cls)  # class id
            out_file.write(" ".join(str(a) for a in (cls_id, *bb)) + '\n')


# Download


path = Path('/data')
for year, image_set in ('2028', 'train'), ('2028', 'trainval'), ('2028', 'val'),('2028','test'):
    imgs_path = path / 'images' / f'{image_set}{year}'
    lbs_path = path / 'labels' / f'{image_set}{year}'
    imgs_path.mkdir(exist_ok=True, parents=True)
    lbs_path.mkdir(exist_ok=True, parents=True)

    with open(path / f'VOC{year}/ImageSets/Main/{image_set}.txt') as f:
        image_ids = f.read().strip().split()
    for id in tqdm(image_ids, desc=f'{image_set}{year}'):
        img_file = path / f'VOC{year}/JPEGImages/{id}.jpg'  # old img path
        lb_path = (lbs_path / f'{id}').with_suffix('.txt')  # new label path
        if img_file.exists():
            img_file.rename(imgs_path / img_file.name)  # move image
            convert_label(path, lb_path, year, id)  # convert labels to YOLO format
        else:
            print(f'File not found: {id}.jpg')
```

## yaml

```yaml
//yaml文件配置
path: /data # dataset root dir
train: images/train2028 # train images (relative to 'path') 128 images
val: images/trainval2028 # val images (relative to 'path') 128 images
test: images/test2028 # test images (optional)


# Classes
names:
  0: hat
  1: person

# Download script/URL (optional) ---------------------------------------------------------------------------------------
download: |
  import xml.etree.ElementTree as ET

  from tqdm import tqdm
  from ultralytics.utils.downloads import download
  from pathlib import Path

  def convert_label(path, lb_path, year, image_id):
      def convert_box(size, box):
          dw, dh = 1. / size[0], 1. / size[1]
          x, y, w, h = (box[0] + box[1]) / 2.0 - 1, (box[2] + box[3]) / 2.0 - 1, box[1] - box[0], box[3] - box[2]
          return x * dw, y * dh, w * dw, h * dh

      in_file = open(path / f'VOC{year}/Annotations/{image_id}.xml')
      out_file = open(lb_path, 'w')
      tree = ET.parse(in_file)
      root = tree.getroot()
      size = root.find('size')
      w = int(size.find('width').text)
      h = int(size.find('height').text)

      names = list(yaml['names'].values())  # names list
      for obj in root.iter('object'):
          cls = obj.find('name').text
          if cls in names and int(obj.find('difficult').text) != 1:
              xmlbox = obj.find('bndbox')
              bb = convert_box((w, h), [float(xmlbox.find(x).text) for x in ('xmin', 'xmax', 'ymin', 'ymax')])
              cls_id = names.index(cls)  # class id
              out_file.write(" ".join(str(a) for a in (cls_id, *bb)) + '\n')


  # Download
  dir = Path(yaml['path'])  # dataset root dir
  url = 'https://github.com/ultralytics/yolov5/releases/download/v1.0/'
  urls = [f'{url}VOCtrainval_06-Nov-2007.zip',  # 446MB, 5012 images
          f'{url}VOCtest_06-Nov-2007.zip',  # 438MB, 4953 images
          f'{url}VOCtrainval_11-May-2012.zip']  # 1.95GB, 17126 images
  download(urls, dir=dir / 'images', curl=True, threads=3, exist_ok=True)  # download and unzip over existing paths (required)

  # Convert
  path = dir / 'images/VOCdevkit'
  for year, image_set in ('2012', 'train'), ('2012', 'val'), ('2007', 'train'), ('2007', 'val'), ('2007', 'test'):
      imgs_path = dir / 'images' / f'{image_set}{year}'
      lbs_path = dir / 'labels' / f'{image_set}{year}'
      imgs_path.mkdir(exist_ok=True, parents=True)
      lbs_path.mkdir(exist_ok=True, parents=True)

      with open(path / f'VOC{year}/ImageSets/Main/{image_set}.txt') as f:
          image_ids = f.read().strip().split()
      for id in tqdm(image_ids, desc=f'{image_set}{year}'):
          f = path / f'VOC{year}/JPEGImages/{id}.jpg'  # old img path
          lb_path = (lbs_path / f.name).with_suffix('.txt')  # new label path
          f.rename(imgs_path / f.name)  # move image
          convert_label(path, lb_path, year, id)  # convert labels to YOLO format
```

# 问题

> 已经安装过cudnn，env内部不能再pip
