//package org.dows.log.rest;
//
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.dows.framework.api.Response;
//import org.dows.log.api.annotation.AuditLog;
//import org.dows.log.api.dto.LogQueryParam;
//import org.dows.log.api.dto.LogSmallDTO;
//import org.dows.log.service.LogService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// *
// */
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/logs")
//@Api(tags = "系统：日志管理")
//public class LogRest {
//
//    private final LogService logService;
//
//    @AuditLog("导出数据")
//    @ApiOperation("导出数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check()")
//    public void download(HttpServletResponse response, LogQueryParam criteria) throws IOException {
//        criteria.setLogType("INFO");
//        logService.download(logService.queryAll(criteria), response);
//    }
//
//    @Log("导出错误数据")
//    @ApiOperation("导出错误数据")
//    @GetMapping(value = "/error/download")
//    @PreAuthorize("@el.check()")
//    public void downloadErrorLog(HttpServletResponse response, LogQueryParam criteria) throws IOException {
//        criteria.setLogType("ERROR");
//        logService.download(logService.queryAll(criteria), response);
//    }
//
//    @GetMapping
//    @ApiOperation("日志查询")
//    @PreAuthorize("@el.check()")
//    public Response<IPage> query(LogQueryParam criteria, Page pageable) {
//        criteria.setLogType("INFO");
//        return Response.ok(logService.queryAll(criteria, pageable));
//    }
//
//    @GetMapping(value = "/user")
//    @ApiOperation("用户日志查询")
//    public Response<IPage<LogSmallDTO>> queryUserLog(LogQueryParam criteria, Page pageable) {
//        criteria.setLogType("INFO");
//        criteria.setBlurry(getCurrentUsername());
//        return Response.ok(logService.queryAllByUser(criteria, pageable));
//    }
//
//    @GetMapping(value = "/error")
//    @ApiOperation("错误日志查询")
//    @PreAuthorize("@el.check()")
//    public Response<IPage> queryErrorLog(LogQueryParam criteria, Page pageable) {
//        criteria.setLogType("ERROR");
//        return Response.ok(logService.queryAll(criteria, pageable));
//    }
//
//    @GetMapping(value = "/error/{id}")
//    @ApiOperation("日志异常详情查询")
//    @PreAuthorize("@el.check()")
//    public Response<Object> queryErrorLogs(@PathVariable Long id) {
//        return Response.ok(logService.findByErrDetail(id));
//    }
//
//    @DeleteMapping(value = "/del/error")
//    @Log("删除所有ERROR日志")
//    @ApiOperation("删除所有ERROR日志")
//    @PreAuthorize("@el.check()")
//    public Response<Integer> delAllErrorLog() {
//        return Response.ok(logService.delAllByError() ? 1 : 0);
//    }
//
//    @DeleteMapping(value = "/del/info")
//    @Log("删除所有INFO日志")
//    @ApiOperation("删除所有INFO日志")
//    @PreAuthorize("@el.check()")
//    public Response<Integer> delAllInfoLog() {
//        return Response.ok(logService.delAllByInfo() ? 1 : 0);
//    }
//
//
//    /**
//     * 获取系统用户名称
//     *
//     * @return 系统用户名称
//     */
//    private String getCurrentUsername() {
//        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            //throw new RuntimeException(HttpStatus.UNAUTHORIZED, "当前登录状态过期");
//        }
//        if (authentication.getPrincipal() instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return userDetails.getUsername();
//        }
//        return null;
//        //throw new RuntimeException(HttpStatus.UNAUTHORIZED, "找不到当前登录的信息");
//    }
//}
