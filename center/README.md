# Us

# linux命令：
## 1.基本命令
整机:top, CPU:vmstat, 内存:free,硬盘:df,磁盘IO:iostat,网络IO:ifstat

top 简写：uptime      -- load average: 参数  高于60%系统压力大  
vmstat -n 2 3  查看 cup 2s查看一次，总共3次
free -m  内存根据兆
df -h  查看硬盘
iostat -xdk 2 3

## 2.CPU过高命令和内存溢出命令
cup 过高怎么查？
1.先用top命令找出CPU占比最高的
2.ps-ef或者jps进一步定位，得知是一个怎么样的一个后台程序给我们惹事
3.定位到具体线程或者代码
4.将需要的线程ID转换为16进制格式(英文小写格式)
5.jstack进程ID|grep tid(16进制线程ID小写英文)-A60

## 3.具体实现
1.top观看一下cpu （看下cup和内存） --  找到对应pid = 3627

2.通过如下命令定位进程id,找到进程号  --  找到对应pid = 3627
ps -ef|grep java|grep -v grep （jps -l）

3.通过进程id找到对应的线程id
ps -mp 进程 -o THREAD，tid,time   (或者top -Hp 3627)
示例：ps -mp 3627 -o THREAD,tid,time

4.将线程id转换为16进制小写值
printf "%x\n" tid
示例：printf "%x\n" 2963

5.看运行轨迹：
jstack 3641 | grep printf "%x\n" 3641 -A60 ，显示60行直接观看
jstack 3641 | grep printf "%x\n" 3641 -A60 > thread_dump.txt，显示60行写道文件thread_dump.txt


6.生成堆快照（hprof 文件，OOM 后必做！）比如pid = 3627
语法：jmap ‐dump:format=b,file=dumpFileName <pid>
示例：jmap -dump:format=b,file=heap_dump_$(date +%Y%m%d_%H%M%S).hprof 3627 ##(生成在当前目录)
示例：jmap ‐dump:format=b,file=/tmp/dump.dat 3627 ##（生成在tmp目录下，要保证目录存在）


#死锁：
jps -l
jstack 进程id

# 1. 查看堆内存使用概况（替换 PID）
jmap -heap <PID>
# 2. 查看大对象分布（按实例数排序）
jmap -histo <PID> | head -20  # 前 20 个占用最多的类



## 4.github总结
github
watch:会持续收到该项目的动态
fork，复制某个项目到自己的Github仓库中
star，可以理解为点赞
clone，将项目下载至本地
folow，关注你感兴趣的作者，会收到他们的动态


常用词含义
1.in关键词限制搜索范围
xxx关键词 in:name或description或readme
示例：seckill in:name,readme, description

2.stars或fork数量关键词去查找

3.关键词 stars 通配符公式 :>或者 :>=
查找stars数大于等于5000的springboot项目    springboot stars:>=5000
4.关键词 forks 通配符公式 :>或者 :>=
查找forks数大于500的springcloud项目 springcloud forks:>500
5.区间范围数字数字1..数字2   
示例：springboot forks:100..200
组合使用 springboot forks:100..200 stars:80..100

6.awesome加强搜索（收集学习等，学习看这个）
示例：awesome redis

7.高亮显示某一行代码
地址+#L13

8.项目内搜索
按下英文字母 t

搜索某个地区内的大佬
location:beijing language:java

## 5.开启端口
重要：
防火墙打开3306端口（可以打开9999端口）
/sbin/iptables -I INPUT -p tcp --dport 3306 -j ACCEPT
/etc/rc.d/init.d/iptables save
/etc/init.d/iptables status

或者：
1、打开防火墙配置文件
vi  /etc/sysconfig/iptables
2、增加下面一行
-A INPUT -m state --state NEW -m tcp -p tcp --dport 3306 -j ACCEPT
注意：增加的开放3306端口的语句一定要在icmp-host-prohibited之前）
3.重启防火墙
service  iptables restart


## 备注
linux密码
wangdi【wangdi】
root【root123】

mysql:
root 123456
root root

xx
abc





























