package org.dows.log.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.dows.log.api.InsertWrapper;
import org.dows.log.api.DomainContextHolder;
import org.dows.log.api.DomainMetadata;
import org.dows.log.api.InsertService;
import org.dows.log.mapper.LogMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 动态服务
 */
@Service
@RequiredArgsConstructor
public class LogService implements InsertService {

    /**
     * 动态mapper
     */
    private final LogMapper logMapper;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    public Object selectById(String table, Long id) {
        return logMapper.selectById(table, id);
    }

    public void selectByIds(String domain, String ids) {
        // 获取domain对应的table
        DomainMetadata domainMetadata = DomainContextHolder.get(domain);
        if (ids.isBlank()) {
            String join = "`" + String.join("`,`", ids.split(",")) + "`";
            logMapper.selectByIds(domainMetadata.getTableName(), join);
        }
    }

    /**
     * 查询列表
     *
     * @param wrapper
     * @return
     */
    public List<JSONObject> selectList(String table, QueryWrapper wrapper) {
        return logMapper.selectList(table, wrapper);
    }

    /**
     * 根据Map查询列表
     *
     * @param data
     * @return
     */
    public List<JSONObject> selectListByMap(String table, Map<String, String> data) {
        QueryWrapper queryWrapper = new QueryWrapper();
        for (String key : data.keySet()) {
            if (data.get(key) != null && !data.get(key).equals("")) {
                queryWrapper.eq(key, data.get(key));
            }
        }
        return selectList(table, queryWrapper);
    }

    @Override
    public void insert(DomainMetadata domainMetadata) {
        logMapper.insert(domainMetadata.getTableName(),domainMetadata.insertWrapper());
    }

    /**
     * 插入
     *
     * @param wrapper
     * @return
     */
    public int insert(String table, InsertWrapper wrapper) {
        return logMapper.insert(table, wrapper);
    }

    /**
     * 根据Map插入
     *
     * @param data
     * @return
     */
    public int insertByMap(String table, Map<String, String> data) {
        InsertWrapper wrapper = new InsertWrapper();
        wrapper.setMap(data);

        // 查询table 是否有通用字段


        return insert(table, wrapper);
    }

    /**
     * 修改
     *
     * @param wrapper
     * @return
     */
    public int updateCondition(String table, UpdateWrapper wrapper) {
        return logMapper.update(table, wrapper);
    }

    /**
     * 根据Map修改
     *
     * @param data
     * @return
     */
    public int updateByMap(String table, Map<String, String> data) {
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
        return updateCondition(table, updateWrapper);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    public int deleteByIds(String table, Integer[] ids) {
        return logMapper.deleteByIds(table, ids);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public int deleteById(String table, Integer id) {
        return logMapper.deleteById(table, id);
    }



}
