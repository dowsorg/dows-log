package org.dows.log.mapper;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.dows.log.SaveWrapper;

import java.util.List;

public interface LogMapper extends BaseMapper<Object> {
    /**
     * 查询
     *
     * @param id 主键
     * @return 测试
     */
    @Select("select * from ${table} where id = `#{id}`")
    JSONObject selectById(@Param("table") String table, @Param("id") Long id);

    @Select("select * from ${table} where id in (${ids})")
    JSONObject selectByIds(@Param("table") String table, @Param("id") String ids);

    @Select("select * from ${table} where domainId = #{domainId}")
    JSONObject selectByDomainId(@Param("table") String table, @Param("domainId") String domainId);

    @Select("select * from ${table} where domainId in (${domainIds})")
    JSONObject selectByDomainIds(@Param("table") String table, @Param("domainIds") String domainIds);


    /**
     * 查询列表
     *
     * @return 测试集合
     */
    @Select("select * from ${table} ${ew.customSqlSegment}")
    List<JSONObject> selectList(@Param("table") String table, @Param("ew") QueryWrapper data);


    @Select("select count(*) from ${table} ${ew.customSqlSegment}")
    int count(@Param("table") String table, @Param("ew") QueryWrapper data);

    /**
     * 新增
     *
     * @return 结果
     */
    @Insert("insert into ${table} ${ew.getSql}")
    int insert(@Param("table") String table, @Param("ew") SaveWrapper data);

    /**
     * 修改
     *
     * @return 结果
     */
    @Update("update ${table} set ${ew.getSqlSet} where ${ew.getSqlSegment}")
    int updateCondition(@Param("table") String table, @Param("ew") UpdateWrapper data);

    /**
     * 删除
     *
     * @param id 测试主键
     * @return 结果
     */
    @Delete("delete from ${table} where id = #{id}")
    int deleteById(@Param("table") String table, @Param("id") Integer id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    default int deleteByIds(String table, Integer[] ids) {
        int flag = 0;
        for (Integer id : ids) {
            flag += deleteById(table, id);
        }
        return flag;
    }

}
