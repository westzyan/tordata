
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
3.国家代码转换异常：Can't find resource for bundle sun.util.resources.LocaleNames, key XK
```
//将国家代码转换为国家名称
ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.CHINA);
String countryCode = "CN"
String countryName = resource.getString(countryCode);
这里遇到XK代码，无法转换
直接捕获异常，不再转换
```

```
编译时报错：程序包sun.security.tools.keytool不存在，在pom.xml中加入如下插件
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <configuration>
        <source>1.8</source>
        <target>1.8</target>
        <encoding>utf-8</encoding>
        <!-- 加入下面编译参数的配置及可以找到rt.jar -->
        <compilerArguments>
            <verbose />
            <bootclasspath>${JAVA_HOME}/jre/lib/rt.jar${path.separator}${JAVA_HOME}/jre/lib/jce.jar</bootclasspath>
        </compilerArguments>
    </configuration>
</plugin>
```


```$xslt

```