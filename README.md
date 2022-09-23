# 树莓派小车
> NAS地址：192.168.32.48

> 树莓派地址：192.168.32.50

### 1.在NAS上启动mqtt
```bash
# 启动redis
docker run -d --name redis --restart=always -p 6379:6379 wjf8882300/redis:latest

# 启动带有redis鉴权的mqtt
docker run -d --restart=always --name emqx -p 18083:18083 -p 1883:1883 -p 8083:8083 -p 8084:8084 \
    -e EMQX_LISTENER__TCP__EXTERNAL=1883 \
    -e EMQX_LISTENER__WS__EXTERNAL=8083 \
    -e EMQX_LISTENER__WSS__EXTERNAL=8084 \
    -e EMQX_ALLOW_ANONYMOUS=false \
    -e EMQX_LOADED_PLUGINS="emqx_auth_redis,emqx_recon,emqx_retainer,emqx_management,emqx_dashboard" \
    -e EMQX_AUTH__REDIS__SERVER="192.168.32.48:6379" \
    -e EMQX_AUTH__REDIS__PASSWORD="kCOs142GAhrVR3cw" \
    -e EMQX_AUTH__REDIS__PASSWORD_HASH="sha256" \
    -e EMQX_AUTH__REDIS__AUTH_CMD="HMGET mqtt_user:%u:%c password" \
    emqx/emqx:latest   
```

### 2.在redis中插入mqtt用户
mqtt用户由用户名、客户端id、密码三个组成，密码使用的sha256加密，为了方便向redis中插入mqtt用户，可以执行下面的python脚本。
1) 把resources/python目录下面的文件拷贝到树莓派
2) 执行脚本安装python的库
```bash
pip3 install  -i  https://pypi.tuna.tsinghua.edu.cn/simple  --no-cache-dir -r requirements.txt
```
3) 执行脚本插入mqtt用户
```bash
# 插入服务端的mqtt用户
python3 redis_mqtt.py  -u raspbian_server -p AgeDHL8ucqp5Tz9R -c raspbian_server

# 插入树莓派端的mqtt用户
python3 redis_mqtt.py  -u pi_car -p RBzicGlr9fXhyx2b -c pi_car
```


### 3.在NAS上部署当前程序
```bash
# 打包
mvn clean package

# 把target/password-1.0.0.jar拷贝到NAS

# 启动jar包
java -jar password-1.0.0.jar
```

### 4.在树莓派上启动python程序
在第2步的时候已经把程序拷贝过了就直接运行，否则先拷贝文件。
```bash
python3 mqtt.py    
```

> 上面是我目前的一个结构，因为我有个NAS，而且redis和mqtt都是现成的，所以就这么弄了。
> 
> 其实可以简化下（不考虑安全问题）：
> 
> 1）可以把程序都部署在树莓派上面，也是没问题的，这里面java程序有点重了，可以只保留html和js，通过js直接链接mqtt。
> 有兴趣的可以尝试下mqtt.html

> 
> 2）mqtt可以不用redis，直接启动就好了。
```
# 不带鉴权的mqtt
docker run -d --name emqx -p 18083:18083 -p 1883:1883 emqx:latest
```