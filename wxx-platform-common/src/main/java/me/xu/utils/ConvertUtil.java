package me.xu.utils;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description 对象转换工具
 * Date 2022/3/25 16:19
 * Version 1.0.1
 *
 * @author Wen
 */
public class ConvertUtil {

    /**
     * 对象转换
     */
    public static <T, S> T convert(final S s, Class<T> clz) {
        return s == null ? null : BeanUtil.copyProperties(s, clz);
    }

    /**
     * 集合转换
     */
    public static <T, S> List<T> convertList(List<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> BeanUtil.copyProperties(vs, clz)).collect(Collectors.toList());
    }

    /**
     * 集合转换（set）
     */
    public static <T, S> Set<T> convertSet(Set<S> s, Class<T> clz) {
        return s == null ? null : s.stream().map(vs -> BeanUtil.copyProperties(vs, clz)).collect(Collectors.toSet());
    }

    /**
     * 分页对象转换
     */
    public static <T, S> Page<T> convertPage(IPage<S> page, Class<T> clz) {
        if (page == null) {
            return null;
        }
        Page<T> pageInfo = new Page<>();
        pageInfo.setTotal(page.getTotal());
        pageInfo.setRecords(convertList(page.getRecords(), clz));
        return pageInfo;
    }
}
