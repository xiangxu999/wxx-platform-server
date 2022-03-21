package me.xu.modules.monitor.pojo;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Description 服务器相关信息实体
 * Date 2021/12/10 10:49
 * Version 1.0.1
 *
 * @author Wen
 */
@Data
@ApiModel(value = "Server", description = "服务器相关信息实体")
public class Server {

    /**
     * 等待时间
     */
    private static final int OSHI_WAIT_SECOND = 1000;

    /**
     * CPU相关信息
     */
    @ApiModelProperty(value = "CPU相关信息")
    private Cpu cpu = new Cpu();

    /**
     * 內存相关信息
     */
    @ApiModelProperty(value = "內存相关信息")
    private Mem mem = new Mem();

    /**
     * JVM相关信息
     */
    @ApiModelProperty(value = "JVM相关信息")
    private Jvm jvm = new Jvm();

    /**
     * 服务器相关信息
     */
    @ApiModelProperty(value = "服务器相关信息")
    private Sys sys = new Sys();

    /**
     * 磁盘相关信息
     */
    @ApiModelProperty(value = "磁盘相关信息")
    private List<SysFile> sysFiles = new LinkedList<>();

    public void copyTo() throws Exception {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        setCpuInfo(hal.getProcessor());

        setMemInfo(hal.getMemory());

        setSysInfo();

        setJvmInfo();

        setSysFiles(si.getOperatingSystem());
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
        long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
        long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
        long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
        long cSys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
        long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
        long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
        long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;
        cpu.setCpuName(processor.getProcessorIdentifier().getName());
        cpu.setCpuPhysicalNum(processor.getPhysicalProcessorCount());
        cpu.setCpuPackage(processor.getPhysicalPackageCount());
        cpu.setCpuLogicalNum(processor.getLogicalProcessorCount());
        cpu.setTotal(totalCpu);
        cpu.setSysRate(cSys);
        cpu.setUsedRate(user);
        cpu.setWait(iowait);
        cpu.setFree(idle);
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        mem.setTotal(memory.getTotal());
        mem.setUsed(memory.getTotal() - memory.getAvailable());
        mem.setFree(memory.getAvailable());
    }

    /**
     * 设置服务器信息
     */
    private void setSysInfo() {
        Properties props = System.getProperties();
        sys.setComputerName(NetUtil.getLocalhost().getHostName());
        sys.setComputerIp(NetUtil.getIpByHost(NetUtil.getLocalhost().getHostName()));
        sys.setOsName(props.getProperty("os.name"));
        sys.setOsArch(props.getProperty("os.arch"));
        sys.setUserDir(props.getProperty("user.dir"));
    }

    /**
     * 设置Java虚拟机
     */
    private void setJvmInfo() throws UnknownHostException {
        Properties props = System.getProperties();
        jvm.setTotal(Runtime.getRuntime().totalMemory());
        jvm.setMax(Runtime.getRuntime().maxMemory());
        jvm.setFree(Runtime.getRuntime().freeMemory());
        jvm.setVersion(props.getProperty("java.version"));
        jvm.setHome(props.getProperty("java.home"));
    }

    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            SysFile sysFile = new SysFile();
            sysFile.setDirName(fs.getMount());
            sysFile.setSysTypeName(fs.getType());
            sysFile.setTypeName(fs.getName());
            sysFile.setTotal(convertFileSize(total));
            sysFile.setFree(convertFileSize(free));
            sysFile.setUsed(convertFileSize(used));
            sysFile.setUsage(NumberUtil.mul(NumberUtil.div(used, total, 4), 100));
            sysFiles.add(sysFile);
        }
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public Double convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return NumberUtil.round(NumberUtil.div(size, gb), 2).doubleValue();
        } else if (size >= mb) {
            return NumberUtil.round(NumberUtil.div(size, mb), 2).doubleValue();
        } else if (size >= kb) {
            return NumberUtil.round(NumberUtil.div(size, kb), 2).doubleValue();
        } else {
            return NumberUtil.round(size, 2).doubleValue();
        }
    }
}
