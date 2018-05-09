package com.yi.common.util;

/**
 * 原理
 * SnowFlake算法产生的ID是一个64位的整型，结构如下（每一部分用“-”符号分隔）：
 *
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *
 * 1位标识部分，在java中由于long的最高位是符号位，正数是0，负数是1，一般生成的ID为正数，所以为0；
 * 41位时间戳部分，这个是毫秒级的时间，一般实现上不会存储当前的时间戳，而是时间戳的差值（当前时间-固定的开始时间），这样可以使产生
 * 的ID从更小值开始；41位的时间戳可以使用69年，(1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69年；
 * 10位节点部分，Twitter实现中使用前5位作为数据中心标识，后5位作为机器标识，可以部署1024个节点；
 * 12位序列号部分，支持同一毫秒内同一个节点可以生成4096个ID；
 *
 * SnowFlake算法生成的ID大致上是按照时间递增的，用在分布式系统中时，需要注意数据中心标识和机器标识必须唯一，这样就能保证每个节点生成
 * 的ID都是唯一的。或许我们不一定都需要像上面那样使用5位作为数据中心标识，5位作为机器标识，可以根据我们业务的需要，灵活分配节点部分，
 * 如：若不需要数据中心，完全可以使用全部10位作为机器标识；若数据中心不多，也可以只使用3位作为数据中心，7位作为机器标识。
 */
public class SnowFlake {
    //开始该类生成ID的时间截，1525858575727 (Wed May 09 17:36:15 CST 2018) 这一时刻到当前时间所经过的毫秒数，占 41 位（还有一位是符号位，永远为 0）。
    private static final long START_TIME = 1525858575727L;

    //机器标识所占的位数
    private static final long MACHINE_BIT = 5L;
    //数据中心标识所占的位数
    private static final long DATACENTER_BIT = 5L;
    //序列号所占的位数
    private static final long SEQUENCE_BIT = 12L;


    //支持的最大机器id，结果是31,这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数
    private static final long MAX_MACHINE = -1L ^ (-1L << MACHINE_BIT);
    //支持的最大数据中心标识
    private static final long MAX_DATACENTER = -1L ^ (-1L << DATACENTER_BIT);
    //生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
    private static final long SEQUENCE_MASK = -1L ^ (-1L << SEQUENCE_BIT);


    //机器id向左移12位
    private static final long MACHINE_LEFT = SEQUENCE_BIT;
    //数据标识id向左移17位
    private static final long DATACENTER_LEFT = MACHINE_BIT + SEQUENCE_BIT;
    //时间截向左移5+5+12=22位
    private static final long TIMESTAMP_LEFT = DATACENTER_BIT + MACHINE_BIT + DATACENTER_LEFT;


    private long machineId;
    private long datacenterId;

    //同一个时间截内生成的序列数，初始值是0，从0开始
    private long sequence = 0L;
    //上次生成id的时间截
    private long lastTimestamp = -1L;

    /**
     * SnowFlake，如果是分布式系统生成id，需要machineId和datacenterId组成唯一值
     * @param machineId 机器id，最大31
     * @param datacenterId 数据中心id，最大31
     */
    public SnowFlake(long machineId, long datacenterId){
        if(machineId < 0 || machineId > MAX_MACHINE){
            throw new IllegalArgumentException(
                    String.format("machineId[%d] is less than 0 or greater than maxMachineId[%d].", machineId, MAX_MACHINE));
        }
        if(datacenterId < 0 || datacenterId > MAX_DATACENTER){
            throw new IllegalArgumentException(
                    String.format("datacenterId[%d] is less than 0 or greater than maxDatacenterId[%d].", datacenterId, MAX_DATACENTER));
        }
        this.machineId = machineId;
        this.datacenterId = datacenterId;
    }

    //生成id
    public synchronized long nextId(){
        long timestamp = timeGen();
        if(timestamp < lastTimestamp){
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        //如果是同一时间生成的，则自增
        if(timestamp == lastTimestamp){
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if(sequence == 0){
                //生成下一个毫秒级的序列
                timestamp = tilNextMillis();
                //序列从0开始
                sequence = 0L;
            }
        }else{
            //如果发现是下一个时间单位，则自增序列回0，重新自增
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        //看本文第二部分的结构图，移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - START_TIME) << TIMESTAMP_LEFT)
                | (datacenterId << DATACENTER_LEFT)
                | (machineId << MACHINE_LEFT)
                | sequence;
    }

    private long tilNextMillis(){
        long timestamp = timeGen();
        if(timestamp <= lastTimestamp){
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }
}
