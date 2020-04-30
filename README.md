
## 异常解决
1. com.mysql.jdbc.PacketTooBigException: Packet for query is too large (11707239 > 1048576)
```
原因是查询数据包太大，需要手动修改mysql的配置，找到mysql安装根目录下的my.ini文件
找到元素[mysqld] 在下方增加一行
max_allowed_packet=64M
修改完毕重启mysql服务。
```
