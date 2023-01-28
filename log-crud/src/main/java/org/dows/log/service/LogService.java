package org.dows.log.service;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.dows.log.SaveWrapper;
import org.dows.log.mapper.LogMapper;

import java.util.List;
import java.util.Map;

/**
 * 动态服务
 */
public class LogService {

    /**
     * 动态mapper
     */
    private final LogMapper logMapper;

    /**
     * 表名
     */
    private final String table;

    public LogService(String table) {
        this.table = table;
        this.logMapper = SpringUtil.getBean(LogMapper.class);
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Object selectById(Integer id) {
        return logMapper.selectById(table, id);
    }

    /**
     * 查询列表
     *
     * @param wrapper
     * @return
     */
    public List<JSONObject> selectList(QueryWrapper wrapper) {
        return logMapper.selectList(table, wrapper);
    }

    /**
     * 根据Map查询列表
     *
     * @param data
     * @return
     */
    public List<JSONObject> selectListByMap(Map<String, String> data) {
        QueryWrapper queryWrapper = new QueryWrapper();
        for (String key : data.keySet()) {
            if (data.get(key) != null && !data.get(key).equals("")) {
                queryWrapper.eq(key, data.get(key));
            }
        }
        return selectList(queryWrapper);
    }

    /**
     * 插入
     *
     * @param wrapper
     * @return
     */
    public int insert(SaveWrapper wrapper) {
        return logMapper.insert(table, wrapper);
    }

    /**
     * 根据Map插入
     *
     * @param data
     * @return
     */
    public int insertByMap(Map<String, String> data) {
        SaveWrapper wrapper = new SaveWrapper();
        wrapper.setMap(data);
        return insert(wrapper);
    }

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    public int updateCondition(UpdateWrapper wrapper) {
        return logMapper.update(table, wrapper);
    }

    /**
     * 根据Map修改
     *
     * @param data
     * @return
     */
    public int updateByMap(Map<String, String> data) {
        UpdateWrapper updateWrapper = new UpdateWrapper();
        if (data.containsKey("id")) {
            updateWrapper.eq("id", data.get("id"));
        } else {
            throw new RuntimeException("不能没有id字段");
        }
        for (String key : data.keySet()) {
            if (data.get(key) != null && !data.get(key).equals("") && !"id".equals(data.get(key))) {
                updateWrapper.set(key, data.get(key));
            }
        }
        return updateCondition(updateWrapper);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public int deleteByIds(Integer[] ids) {
        return logMapper.deleteByIds(table, ids);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int deleteById(Integer id) {
        return logMapper.deleteById(table, id);
    }
}
