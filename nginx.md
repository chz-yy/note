# nginx

## 反向代理

/etc/nginx/nginx.conf

```nginx
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;

    access_log  /var/log/nginx/host.access.log  main;
    location /api {     //带有/api的请求转发到后端 不暴露后端端口
        rewrite ^/api(.*)$ $1 break;  //去掉api前缀
        proxy_pass http://172.20.0.3:8080;  # 转发到后端地址
        proxy_set_header Host $host;  # 设置请求头
        proxy_set_header X-Real-IP $remote_addr;  # 转发客户端真实IP
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;  # 转发IP链
        proxy_set_header X-Forwarded-Proto $scheme;  # 转发协议
    }

    location / {
        root   /usr/share/nginx/html;
        index  index.html index.htm;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    # proxy the PHP scripts to Apache listening on 127.0.0.1:80
    #
    #location ~ \.php$ {
    #    proxy_pass   http://127.0.0.1;
    #}

    # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
    #
    #location ~ \.php$ {
    #    root           html;
    #    fastcgi_pass   127.0.0.1:9000;
    #    fastcgi_index  index.php;
    #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
    #    include        fastcgi_params;
    #}

    # deny access to .htaccess files, if Apache's document root
    # concurs with nginx's one
    #
    #location ~ /\.ht {
    #    deny  all;
    #}
}
```

## 命令

```bash
nginx -s reload 重新加载配置
nginx -t 检查配置文件语法
```