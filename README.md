
## 异常解决
1. com.mysql.jdbc.PacketTooBigException: Packet for query is too large (11707239 > 1048576)
```
原因是查询数据包太大，需要手动修改mysql的配置，找到mysql安装根目录下的my.ini文件
找到元素[mysqld] 在下方增加一行
max_allowed_packet=64M
修改完毕重启mysql服务。
```
2.java.lang.NumberFormatException: For input string: "1e+05"
```
对于有的数字用科学计数法表示的，转化一下
例如：
BigDecimal bd = new BigDecimal("3.40256010353E11");
然后转换成字符串：
String str = bd.toPlainString();
如果这个数字的长度是道在int的范围内的话，是可以转专换成int类型：
int a = Integer.parsInt(str);
```
