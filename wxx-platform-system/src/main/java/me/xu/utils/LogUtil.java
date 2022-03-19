package me.xu.utils;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import me.xu.common.ApiConst;
import me.xu.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description 日志工具类
 * Date 2021/12/24 16:42
 * Version 1.0.1
 *
 * @author Wen
 */
@Slf4j
@Component
public class LogUtil {

    private static final String UNKNOWN = "unknown";

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // 获取本机真正的ip地址
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                log.error(e.getMessage(), e);
            }
        }
        return ip;
    }

    /**
     * 获得游览器
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getBrowser(HttpServletRequest request) {
        // 得到UserAgent
        UserAgent userAgent = getUserAgent(request);
        return userAgent.getBrowser().toString().concat(" " + userAgent.getVersion());
    }

    /**
     * 获得操作平台
     *
     * @param request HttpServletRequest
     * @return String
     */
    public static String getPlatform(HttpServletRequest request) {
        // 得到UserAgent
        UserAgent userAgent = getUserAgent(request);
        return userAgent.getPlatform().toString();
    }

    /**
     * 获得UserAgent
     *
     * @param request HttpServletRequest
     * @return UserAgent
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }

    public static String getUsername(Method method, Object[] args) {
        // 登录前相关操作
        if (StrUtil.isEmpty(StpUtil.getTokenValue())) {
            String parameter = getParameter(method, args);
            JSONObject jsonObject = JSONUtil.parseObj(parameter);
            return jsonObject.get("username", String.class);
        }
        // 登录后操作
        SysUser sysUser = (SysUser) StpUtil.getSession().get("userInfo");
        return sysUser.getUsername();
    }

    /**
     * 根据方法和传入的参数获取请求参数
     *
     * @param method 方法
     * @param args   参数
     * @return String
     */
    public static String getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>();
                String key = parameters[i].getName();
                if (!StrUtil.isEmpty(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return "";
        }
        return argList.size() == 1 ? JSONUtil.toJsonStr(argList.get(0)) : JSONUtil.toJsonStr(argList);
    }

    /**
     * 根据IP获得
     *
     * @return String
     */
    public static String getCityInfo(String ip) {
        try {
            // 优先调用高德API
            JSONObject jsonObject = JSONUtil.parseObj(HttpUtil.get(String.format(ApiConst.GaoDe.IP_URL, ip)));
            if ("[]".equals(jsonObject.get("city", String.class))) {
                if ("[]".equals(jsonObject.get("province", String.class))) {
                    return "";
                }
                return jsonObject.get("province", String.class);
            }
            return jsonObject.get("province", String.class) + jsonObject.get("city", String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            // 异常,默认设置为空
            return "";
        }
    }
}
