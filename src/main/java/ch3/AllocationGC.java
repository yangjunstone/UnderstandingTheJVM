package ch3;

/**
 * 虚拟机内存分配及GC测试
 * @author yangjun
 */

public class AllocationGC {
    private static final int _1MB=1024*1024;

    /**
     * 新生代 Minor GC
     *VM参数：-verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
     **/
    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;

        allocation1=new byte[2*_1MB];
        allocation2=new byte[2*_1MB];
        allocation3=new byte[2*_1MB];
        allocation4=new byte[4*_1MB]; //出现一次Minor GC
    }

    /**
     * 大对象直接进入老年代
     *VM参数：-verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
     */
    public static void testPretenureSizeThreshold() {
        byte[]allocation;
        allocation=new byte[4*_1MB]; //直接分配在老年代中
    }

    /**
     *长期存活的对象进入老年代
     *VM参数：-verbose:gc -XX:+UseSerialGC -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
     */
    @SuppressWarnings("unused")
    public static void testTenuringThreshold() {
        byte[] allocation1, allocation2, allocation3;

        allocation1=new byte[_1MB/4];
        //什么时候进入老年代取决于XX:MaxTenuringThreshold设置
        allocation2=new byte[4*_1MB];
        allocation3=new byte[4*_1MB];
        allocation3=null;
        allocation3=new byte[4*_1MB];
    }

    public static void main(String[] args){
        //testAllocation();
        //testPretenureSizeThreshold();
        testTenuringThreshold();
    }
}
