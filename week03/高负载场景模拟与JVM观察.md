# 高负载场景模拟与JVM观察

压力测试工具：JMeter-内网；JVM观察工具：Grafana、GCEasy

低延时接口：/spu/goods/10000023827800

高延时接口：/spu/goods/slow/10000023827800

## 1. 吞吐量优先

```sh
# 吞吐量优先策略： 
JAVA_OPT="${JAVA_OPT} -Xms256m -Xmx256m -Xmn125m -XX:MetaspaceSize=128m - Xss512k" 
JAVA_OPT="${JAVA_OPT} -XX:+UseParallelGC -XX:+UseParallelOldGC"
JAVA_OPT="${JAVA_OPT} -XX:+PrintGCDetails -XX:+PrintGCTimeStamps - XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:${BASE_DIR}/logs/gc-ps-po.log" 
```

### 低延迟

测试开始时间：12:27 测试结束时间：12:32

吞吐量

![image-20230808123700458](./高负载场景模拟与JVM观察.assets/image-20230808123700458.png)

RT

![image-20230808123738641](./高负载场景模拟与JVM观察.assets/image-20230808123738641.png)

TPS

![image-20230808123805235](./高负载场景模拟与JVM观察.assets/image-20230808123805235.png)

GC 的统计信息

![image-20230808140312227](./高负载场景模拟与JVM观察.assets/image-20230808140312227.png)

![image-20230808124421429](./高负载场景模拟与JVM观察.assets/image-20230808124421429.png)

![image-20230808124439003](./高负载场景模拟与JVM观察.assets/image-20230808124439003.png)

![image-20230808124456387](./高负载场景模拟与JVM观察.assets/image-20230808124456387.png)

堆内存统计信息

![image-20230808140219626](./高负载场景模拟与JVM观察.assets/image-20230808140219626.png)

![image-20230808140259006](./高负载场景模拟与JVM观察.assets/image-20230808140259006.png)

![image-20230808124513905](./高负载场景模拟与JVM观察.assets/image-20230808124513905.png)

### 高延迟

测试开始时间：12:48 测试结束时间：12:59

吞吐量

![image-20230808125836266](./高负载场景模拟与JVM观察.assets/image-20230808125836266.png)

RT

![image-20230808130002750](./高负载场景模拟与JVM观察.assets/image-20230808130002750.png)

TPS

![image-20230808130030590](./高负载场景模拟与JVM观察.assets/image-20230808130030590.png)

GC 的统计信息

![image-20230808140421418](./高负载场景模拟与JVM观察.assets/image-20230808140421418.png)

![image-20230808130255746](./高负载场景模拟与JVM观察.assets/image-20230808130255746.png)

![image-20230808130316081](./高负载场景模拟与JVM观察.assets/image-20230808130316081.png)

![image-20230808130342147](./高负载场景模拟与JVM观察.assets/image-20230808130342147.png)

堆内存统计信息

![image-20230808140405940](./高负载场景模拟与JVM观察.assets/image-20230808140405940.png)

![image-20230808130228118](./高负载场景模拟与JVM观察.assets/image-20230808130228118.png)

## 2. 响应时间优先

```sh
# 响应时间优先策略 
JAVA_OPT="${JAVA_OPT} -Xms256m -Xmx256m -Xmn125m -XX:MetaspaceSize=128m - Xss512k" 
JAVA_OPT="${JAVA_OPT} -XX:+UseParNewGC -XX:+UseConcMarkSweepGC "
JAVA_OPT="${JAVA_OPT} -XX:+PrintGCDetails -XX:+PrintGCTimeStamps - XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:${BASE_DIR}/logs/gc-parnew- cms.log" 
```

### 低延迟

测试开始时间：13:08 测试结束时间：13:15

吞吐量

![image-20230808131259772](./高负载场景模拟与JVM观察.assets/image-20230808131259772.png)

RT

![image-20230808131324101](./高负载场景模拟与JVM观察.assets/image-20230808131324101.png)

TPS

![image-20230808131345213](./高负载场景模拟与JVM观察.assets/image-20230808131345213.png)

GC 的统计信息

![image-20230808140517896](./高负载场景模拟与JVM观察.assets/image-20230808140517896.png)

![image-20230808131531720](./高负载场景模拟与JVM观察.assets/image-20230808131531720.png)

![image-20230808131600422](./高负载场景模拟与JVM观察.assets/image-20230808131600422.png)

![image-20230808131632297](./高负载场景模拟与JVM观察.assets/image-20230808131632297.png)

![image-20230808131617651](./高负载场景模拟与JVM观察.assets/image-20230808131617651.png)

堆内存统计信息

![image-20230808140503053](./高负载场景模拟与JVM观察.assets/image-20230808140503053.png)

![image-20230808131515731](./高负载场景模拟与JVM观察.assets/image-20230808131515731.png)

### 高延迟

测试开始时间：13:17 测试结束时间：13:26

吞吐量

![image-20230808132737257](./高负载场景模拟与JVM观察.assets/image-20230808132737257.png)

RT

![image-20230808132808685](./高负载场景模拟与JVM观察.assets/image-20230808132808685.png)

TPS

![image-20230808132825289](./高负载场景模拟与JVM观察.assets/image-20230808132825289.png)

GC 的统计信息

![image-20230808140623374](./高负载场景模拟与JVM观察.assets/image-20230808140623374.png)

![image-20230808133036915](./高负载场景模拟与JVM观察.assets/image-20230808133036915.png)

![image-20230808133057169](./高负载场景模拟与JVM观察.assets/image-20230808133057169.png)

![image-20230808133112063](./高负载场景模拟与JVM观察.assets/image-20230808133112063.png)

![image-20230808133127621](./高负载场景模拟与JVM观察.assets/image-20230808133127621.png)

堆内存统计信息

![image-20230808140551989](./高负载场景模拟与JVM观察.assets/image-20230808140551989.png)

![image-20230808133022292](./高负载场景模拟与JVM观察.assets/image-20230808133022292.png)

## 3. G1

```sh
# 全功能垃圾收集器 
JAVA_OPT="${JAVA_OPT} -Xms256m -Xmx256m -XX:MetaspaceSize=128m -Xss512k"
JAVA_OPT="${JAVA_OPT} -XX:+UseG1GC -XX:MaxGCPauseMillis=100"
JAVA_OPT="${JAVA_OPT} -XX:+PrintGCDetails -XX:+PrintGCTimeStamps - XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Xloggc:${BASE_DIR}/logs/gc-g- one.log"
```

### 低延迟

测试开始时间：13:35 测试结束时间：13:39

吞吐量

![image-20230808134002718](./高负载场景模拟与JVM观察.assets/image-20230808134002718.png)

RT

![image-20230808134021829](./高负载场景模拟与JVM观察.assets/image-20230808134021829.png)

TPS

![image-20230808134040827](./高负载场景模拟与JVM观察.assets/image-20230808134040827.png)

GC 的统计信息

![image-20230808140750569](./高负载场景模拟与JVM观察.assets/image-20230808140750569.png)

![image-20230808134224764](./高负载场景模拟与JVM观察.assets/image-20230808134224764.png)

![image-20230808134238849](./高负载场景模拟与JVM观察.assets/image-20230808134238849.png)

![image-20230808134256184](./高负载场景模拟与JVM观察.assets/image-20230808134256184.png)

![image-20230808134315964](./高负载场景模拟与JVM观察.assets/image-20230808134315964.png)

堆内存统计信息

![image-20230808140715043](./高负载场景模拟与JVM观察.assets/image-20230808140715043.png)

![image-20230808134210924](./高负载场景模拟与JVM观察.assets/image-20230808134210924.png)

### 高延迟

测试开始时间：13:44 测试结束时间：13:54

吞吐量

![image-20230808135611922](./高负载场景模拟与JVM观察.assets/image-20230808135611922.png)

RT

![image-20230808135650379](./高负载场景模拟与JVM观察.assets/image-20230808135650379.png)

TPS

![image-20230808135805433](./高负载场景模拟与JVM观察.assets/image-20230808135805433.png)

GC 的统计信息

![image-20230808140932574](./高负载场景模拟与JVM观察.assets/image-20230808140932574.png)

![image-20230808135934463](./高负载场景模拟与JVM观察.assets/image-20230808135934463.png)

![image-20230808135947835](./高负载场景模拟与JVM观察.assets/image-20230808135947835.png)

![image-20230808140000422](./高负载场景模拟与JVM观察.assets/image-20230808140000422.png)

![image-20230808140015121](./高负载场景模拟与JVM观察.assets/image-20230808140015121.png)

堆内存统计信息

![image-20230808140915750](./高负载场景模拟与JVM观察.assets/image-20230808140915750.png)

![image-20230808135914240](./高负载场景模拟与JVM观察.assets/image-20230808135914240.png)

