# 动手学pytorch

```python
import torch

x=torch.arange(12)
x=x.reshape(3,4)
y=torch.arange(12)
y=y.reshape(3,4)
z=torch.cat((x,y),dim=1)
print(z)
print(x==y)
print(x.sum())

a=torch.arange(3).reshape(3,1)
b=torch.arange(2).reshape(1,2)
print(a,b)
print(a+b)

print(x[-1])
print(x[1:3])
print(x[0:2,:])

before=id(x)
x+=y
print(id(x)==before)

before=id(x)
x=x+y
print(id(x)==before)

before=id(x)
x[:]=x+y
print(id(x)==before)

print(x)
print(x.T)

before=id(x)
y=x
print(id(y)==before)
y=x.clone()
print(id(y)==before)
print(x,y)
print(x*y)

a=torch.arange(20*2).reshape(2,5,4)
print(a)

print(a.sum(axis=0))

b=torch.arange(4)
c=torch.arange(4)
print(b.dot(c))
print(sum(b*c))
b=torch.arange(12).reshape(3,4)
print(b)
print(c)
d=torch.mv(b,c)
print(d)
print(torch.abs(b).sum())
d=torch.tensor([1.0,2.0,3])
print(torch.norm(d))
a=torch.ones(2,5,4)
print(a)

```

# 线性回归实现

```python
import random
import torch
from d2l import torch as d2l
import matplotlib.pyplot as plt


def synthetic_data(w,b,num_examples):
    x=torch.normal(0,1,(num_examples,len(w)))
    y=torch.matmul(x,w)+b
    y+=torch.normal(0,0.01,y.shape)
    return x,y.reshape((-1,1))

true_w=torch.tensor([2,-3.4])
true_b=4.2
features,labels=synthetic_data(true_w,true_b,1000)

print(features[0])
print(labels[0])
d2l.set_figsize()
plt.scatter(features[:,1].detach().numpy(),labels.detach().numpy(),1)
plt.show()

def data_iter(batch_size,features,labels):
    num_examples=len(features)
    indices=list(range(num_examples))
    random.shuffle(indices)
    for i in range(0,num_examples,batch_size):
        batch_indices=torch.tensor(
            indices[i:min(i+batch_size,num_examples)]
        )
        yield features[batch_indices],labels[batch_indices]


batch_size=10
for x,y in data_iter(batch_size,features,labels):
    print(x,'\n',y)
    break

w=torch.normal(0,0.01,size=(2,1),requires_grad=True)
b=torch.zeros(1,requires_grad=True)

def linreg(X,w,b):
    return torch.matmul(X,w)+b

def squared_loss(y_hat,y):
    return (y_hat-y.reshape(y_hat.shape))**2/2

def sgd(params,lr,batch_size):
    with torch.no_grad():
        for param in params:
            param-=lr*param.grad/batch_size
            param.grad.zero_()

lr=0.03
num_epochs=3
net=linreg
loss=squared_loss

for epoch in range(num_epochs):
    for X,y in data_iter(batch_size, features, labels):
        l=loss(net(X,w,b),y)
        l.sum().backward()
        sgd([w,b],lr,batch_size)
    with torch.no_grad():
        train_l=loss(net(features,w,b),labels)
        print(f'epoch {epoch+1},loss {float(train_l.mean()):f}')

print(w,b)
```

