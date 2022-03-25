package me.xu.modules.security.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import me.xu.modules.security.service.vo.CodeVo;
import me.xu.modules.security.service.vo.UserInfoVo;
import me.xu.modules.security.utils.CaptchaUtil;
import me.xu.modules.system.mapper.SysUserMapper;
import me.xu.modules.system.pojo.SysMenu;
import me.xu.modules.system.pojo.SysRole;
import me.xu.modules.system.pojo.SysUser;
import me.xu.modules.system.service.SysMenuService;
import me.xu.modules.system.service.SysRoleService;
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
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private CaptchaProperties captchaProperties;

    @Autowired
    private CaptchaUtil captchaUtil;

    @Autowired
    private RedisUtil redisUtil;

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
        SysUser checkUser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", userDto.getUsername()));
        if (checkUser == null) {
            throw new ResultException(ResultCode.LOGIN_ERROR);
        }
        if (StrUtil.equals(checkUser.getPassword(), userDto.getPassword())) {
            StpUtil.login(checkUser.getUserId());
            // 记录登录的时间（无论成功或者失败）
            checkUser.setLastLogin(LocalDateTime.parse(DateUtil.now(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            sysUserMapper.updateById(checkUser);
            // 把当前用户存入session
            StpUtil.getSession().set("userInfo", checkUser);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return Result.success(tokenInfo, ResultMsg.LOGIN_SUCCESS_MSG);
        } else {
            return Result.failed(ResultCode.LOGIN_ERROR);
        }

    }

    @Override
    public void logout() {
        StpUtil.logout();
    }

    @Override
    public CodeVo getCode() {
        CodeVo codeVo = new CodeVo();
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
    public UserInfoVo userInfo(String token) {
        // 根据token得到当前的登录id
        Object loginIdByToken = StpUtil.getLoginIdByToken(token);
        // 根据登录id获取用户
        SysUser sysUser = sysUserMapper.selectById(Long.valueOf(String.valueOf(loginIdByToken)));
        // 该用户的角色字符串
        String role = "";
        // 查询该用户所拥有的角色
        List<SysRole> sysRoleList = sysRoleService.getSysRoleListByUserId(sysUser.getUserId());
        if (sysRoleList.size() > 0) {
            role = sysRoleList.stream().map(SysRole::getCode).collect(Collectors.joining(","));
        }
        // 获取对应用户的树形菜单
        List<SysMenu> nav = sysMenuService.getCurrentUserNav(sysUser.getUserId());
        return new UserInfoVo(sysUser.getUsername(), sysUser.getAvatar(), role, nav, DateUtil.format(sysUser.getCreated(), "yyyy-MM-dd HH:mm:ss"));
    }
}
