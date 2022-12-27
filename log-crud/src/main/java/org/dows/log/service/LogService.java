package org.dows.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dows.framework.crud.mybatis.MybatisCrudService;
import org.dows.log.api.dto.LogQueryParam;
import org.dows.log.api.dto.LogSmallDTO;
import org.dows.log.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 */
public interface LogService extends MybatisCrudService<SysLog> {

    static final String CACHE_KEY = "log";

    /**
     * 查询数据分页
     *
     * @param query    条件
     * @param pageable 分页参数
     * @return PageInfo<Log>
     */
    IPage queryAll(LogQueryParam query, Page pageable);

    /**
     * 查询所有数据不分页
     *
     * @param query 条件参数
     * @return List<Log>
     */
    List<SysLog> queryAll(LogQueryParam query);

    SysLog findById(Long id);

    /**
     * 查询用户日志
     *
     * @param criteria 查询条件
     * @param pageable 分页参数
     * @return -
     */
    IPage<LogSmallDTO> queryAllByUser(LogQueryParam criteria, Page pageable);

    /**
     * 保存日志数据
     *
     * @param username  用户
     * @param browser   浏览器
     * @param ip        请求IP
     * @param joinPoint /
     * @param log       日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log);

    /**
     * 查询异常详情
     *
     * @param id 日志ID
     * @return Object
     */
    Object findByErrDetail(Long id);

    /**
     * 导出日志
     *
     * @param logs     待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<SysLog> logs, HttpServletResponse response) throws IOException;

    boolean removeByLogType(String logType);

    /**
     * 删除所有错误日志
     */
    boolean delAllByError();

    /**
     * 删除所有INFO日志
     */
    boolean delAllByInfo();
}
