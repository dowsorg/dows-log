//package org.dows.log.service.impl;
//
//import cn.hutool.core.lang.Dict;
//import cn.hutool.core.util.ObjectUtil;
//import cn.hutool.json.JSONObject;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
//import org.dows.framework.crud.mybatis.utils.QueryWrapperUtils;
//import org.dows.log.api.annotation.AuditLog;
//import org.dows.log.api.dto.LogQueryParam;
//import org.dows.log.api.dto.LogSmallDTO;
//import org.dows.log.api.util.FileUtil;
//import org.dows.log.api.util.ValidationUtil;
//import org.dows.log.entity.SysLog;
//import org.dows.log.mapper.LogMapper;
//import org.dows.log.service.LogService;
//import lombok.AllArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.lang.reflect.Method;
//import java.util.*;
//
///**
// *
// */
//@Slf4j
//@Service
//@AllArgsConstructor
//// @CacheConfig(cacheNames = LogService.CACHE_KEY)
//@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
//public class LogServiceImpl extends MybatisCrudServiceImpl<LogMapper, SysLog> implements LogService {
//
//    // private final RedisUtils redisUtils;
//    private final LogMapper logMapper;
//
//    @Override
//    public IPage queryAll(LogQueryParam query, Page page) {
//        IPage<SysLog> pageList = logMapper.selectPage(page, QueryWrapperUtils.getPredicate(query));
//        String status = "ERROR";
//        if (status.equals(query.getLogType())) {
//            //return ConvertUtil.convertPage(pageList, LogErrorDTO.class);
//        }
//        return null;
//        //return ConvertUtil.convertPage(pageList, AccountLog.class);
//    }
//
//    @Override
//    public List<SysLog> queryAll(LogQueryParam query) {
//        return logMapper.selectList(QueryWrapperUtils.getPredicate(query));
//    }
//
//    @Override
//    public IPage<LogSmallDTO> queryAllByUser(LogQueryParam query, Page page) {
//        IPage<SysLog> pageList = logMapper.selectPage(page, QueryWrapperUtils.getPredicate(query));
//        //return ConvertUtil.convertPage(pageList, LogSmallDTO.class);
//        return null;
//    }
//
//    @Override
//    // @Cacheable(key = "'id:' + #p0")
//    public SysLog findById(Long id) {
//        return logMapper.selectById(id);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean removeByLogType(String logType) {
//        UpdateWrapper<SysLog> wrapper = new UpdateWrapper<>();
//        wrapper.lambda().eq(SysLog::getLogType, logType);
//        return logMapper.delete(wrapper) > 0;
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log) {
//
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//        AuditLog aopAuditLog = method.getAnnotation(AuditLog.class);
//
//        // 方法路径
//        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";
//
//        StringBuilder params = new StringBuilder("{");
//        //参数值
//        List<Object> argValues = new ArrayList<>(Arrays.asList(joinPoint.getArgs()));
//        //参数名称
//        for (Object argValue : argValues) {
//            params.append(argValue).append(" ");
//        }
//        // 描述
//        if (log != null) {
//            log.setDescr(aopAuditLog.value());
//        }
//        assert log != null;
//        log.setRequestIp(ip);
//
//        String loginPath = "login";
//        if (loginPath.equals(signature.getName())) {
//            try {
//                username = new JSONObject(argValues.get(0)).get("username").toString();
//            } catch (Exception e) {
//                LogServiceImpl.log.error(e.getMessage(), e);
//            }
//        }
//        //log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
//        log.setMethod(methodName);
//        log.setUsername(username);
//        log.setParams(params.toString() + " }");
//        log.setBrowser(browser);
//        if (log.getId() == null) {
//            logMapper.insert(log);
//        } else {
//            logMapper.updateById(log);
//        }
//    }
//
//    @Override
//    public Object findByErrDetail(Long id) {
//        SysLog log = findById(id);
//        ValidationUtil.isNull(log.getId(), "Log", "id", id);
//        byte[] details = log.getExceptionDetail();
//        return Dict.create().set("exception", new String(ObjectUtil.isNotNull(details) ? details : "".getBytes()));
//    }
//
//    @Override
//    public void download(List<SysLog> logs, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (SysLog log : logs) {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("用户名", log.getUsername());
//            map.put("IP", log.getRequestIp());
//            map.put("IP来源", log.getAddress());
//            map.put("描述", log.getDescr());
//            map.put("浏览器", log.getBrowser());
//            map.put("请求耗时/毫秒", log.getTime());
//            map.put("异常详情", new String(ObjectUtil.isNotNull(log.getExceptionDetail()) ? log.getExceptionDetail() : "".getBytes()));
//            map.put("创建日期", log.getDt());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean delAllByError() {
//        return this.removeByLogType("ERROR");
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public boolean delAllByInfo() {
//        return this.removeByLogType("INFO");
//    }
//}
