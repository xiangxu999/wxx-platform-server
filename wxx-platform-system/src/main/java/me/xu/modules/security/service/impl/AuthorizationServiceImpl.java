package me.xu.modules.security.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import me.xu.common.Result;
import me.xu.common.ResultCode;
import me.xu.common.ResultMsg;
import me.xu.exception.ResultException;
import me.xu.modules.security.common.LoginCodeEnum;
import me.xu.modules.security.config.CaptchaProperties;
import me.xu.modules.security.service.AuthorizationService;
import me.xu.modules.security.service.dto.UserDto;
import me.xu.modules.security.service.vo.CodeVO;
import me.xu.modules.security.utils.CaptchaUtil;
import me.xu.modules.system.pojo.SysMenu;
import me.xu.modules.system.pojo.SysRole;
import me.xu.modules.system.pojo.SysUser;
import me.xu.modules.system.service.impl.SysMenuServiceImpl;
import me.xu.modules.system.service.impl.SysRoleServiceImpl;
import me.xu.modules.system.service.impl.SysUserServiceImpl;
import me.xu.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Description 登录、登出、注册接口实现
 * Date 2021/11/10 16:32
 * Version 1.0.1
 *
 * @author Wen
 */
@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private SysMenuServiceImpl sysMenuService;

    @Autowired
    private SysRoleServiceImpl sysRoleService;

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysUser register(SysUser sysUser) {
        return null;
    }

    @Override
    public Result login(UserDto userDto) {
        // 开启验证码
        if (captchaProperties.getEnabled()) {
            // 查询验证码
            String code = (String) redisUtil.get(userDto.getUuid());
            // 清除验证码
            redisUtil.del(userDto.getUuid());
            // redis缓存已经过期
            if (StrUtil.isBlank(code)) {
                throw new ResultException(ResultCode.CODE_EXPIRE);
            }
            if (StrUtil.isBlank(userDto.getCode()) || !userDto.getCode().equalsIgnoreCase(code)) {
                throw new ResultException(ResultCode.CODE_ERROR);
            }
        }
        SysUser checkUser = sysUserService.getByUsername(userDto.getUsername());
        if (checkUser == null) {
            return null;
        }
        if (StrUtil.equals(checkUser.getPassword(), userDto.getPassword())) {
            StpUtil.login(checkUser.getUserId());
            // 记录登录的时间（无论成功或者失败）
            checkUser.setLastLogin(LocalDateTime.parse(DateUtil.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            sysUserService.updateById(checkUser);
            // 把当前用户存入session
            StpUtil.getSession().set("userInfo", checkUser);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Result.success(tokenInfo, ResultMsg.LOGIN_SUCCESS_MSG);
        } else {
            return Result.failed(ResultCode.LOGIN_ERROR);
        }

    }

    @Override
    public Result logout() {
        if (StpUtil.isLogin()) {
            StpUtil.logout();
            return Result.success("退出成功");
        } else {
            return Result.failed("退出失败");
        }
    }

    @Override
    public CodeVO getCode() {
        CodeVO codeVo = new CodeVO();
        // 未开启验证码
        if (!captchaProperties.getEnabled()) {
            // 验证码信息
            codeVo.setEnabled(0);
            return codeVo;
        }
        // 获取运算的结果
        Captcha captcha = captchaUtil.getCaptcha();
        // 生成验证码uuid
        String uuid = captchaProperties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisUtil.set(uuid, captchaValue, captchaProperties.getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        codeVo.setImg(captcha.toBase64());
        codeVo.setUuid(uuid);
        codeVo.setEnabled(1);
        return codeVo;
    }

    @Override
    public Result userInfo(String token) {
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        SysUser sysUser = sysUserService.getByUserId(Long.valueOf(String.valueOf(loginIdByToken)));
        // 得到当前登录用户的角色,用户前端生成路由
        String role = "";
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(sysUser.getUserId());
        if (sysRoleList.size() > 0) {
            role = sysRoleList.stream().map(SysRole::getCode).collect(Collectors.joining(","));
        }
        // 获取对应的菜单用户前端生成路由
        List<SysMenu> nav = sysMenuService.getCurrentUserNav(sysUser);
        return Result.success(MapUtil.builder()
                .put("id", sysUser.getUserId())
                .put("username", sysUser.getUsername())
                .put("avatar", sysUser.getAvatar())
                .put("created", sysUser.getCreated())
                .put("role", role)
                .put("nav", nav)
                .map());
    }
}
