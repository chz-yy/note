# map部署

- cmd->  ssh root@202.120.114.176

**将配置文件和网页移动到nginx**

- scp -r E:\vuefile\shu-map\dist root@202.120.114.176:/usr/share/nginx/html

- scp -r E:\vuefile\shu-map\nginx root@202.120.114.176:/usr/share/nginx/html

-  cd /etc/nginx/

- vi nginx.conf

- ```nginx
      server {
          listen       443 ssl http2;
          listen       [::]:443 ssl http2;
          server_name  202.120.114.176;
          root         /usr/share/nginx/html/dist;
  
          ssl_certificate "/usr/share/nginx/html/Nginx/shu.edu.cn_cert_chain.pem";
          ssl_certificate_key "/usr/share/nginx/html/Nginx/shu.edu.cn_key.key";
          ssl_session_cache shared:SSL:1m;
          ssl_session_timeout  10m;
  #       ssl_ciphers PROFILE=SYSTEM;
          ssl_prefer_server_ciphers on;
  
          # Load configuration files for the default server block.
          include /etc/nginx/default.d/*.conf;
  
          error_page 404 /404.html;
              location = /40x.html {
          }
  
          error_page 500 502 503 504 /50x.html;
              location = /50x.html {
          }
      }
          
       server {
      	listen 90;  # 监听 HTTP 端口
      	server_name 202.120.114.176;  # 您的网站域名
  
      	# 重定向所有 HTTP 请求到 HTTPS
      	return 301 https://$host$request_uri;
  	}
  ```

- 检查 sudo nginx -t

- 重启 sudo service nginx restart

