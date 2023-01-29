package org.dows.log.rest;

import org.dows.framework.api.Response;
import org.dows.log.api.DomainContextHolder;
import org.dows.log.api.DomainMeta;
import org.dows.log.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class LogController {

    protected LogService service;

    /**
     * 获取所有的domains
     *
     * @return
     */
    @GetMapping(value = "v1/domains")
    public Response getDomains() {
        return null;
    }

    @GetMapping(value = "v1/{domain}/{id}")
    public Response getById(@PathVariable(value = "domain") String domain, @PathVariable("id") Long id) {
        DomainMeta domainMeta = DomainContextHolder.get(domain);
        service.selectById(domain, id);
        return null;
    }

    @GetMapping(value = "v1/{domain}/{ids}")
    public Response getByIds(@PathVariable(value = "domain") String domain, @PathVariable("ids") String ids) {
        service.selectByIds(domain, ids);
        return null;
    }
    @DeleteMapping("v1/{domain}/{ids}")
    public Response remove(@PathVariable Integer[] ids) {
        return null;
    }

    @GetMapping("v1/{domain}/list")
    public Response list(@PathVariable(value = "domain") String domain, HttpServletRequest httpServletRequest) {

        service.selectListByMap(null, null);
        return null;
    }


    @GetMapping("v1/{domain}/page")
    public Response page(@PathVariable(value = "domain") String domain, HttpServletRequest httpServletRequest) {

        service.selectListByMap(null, null);
        return null;
    }


    @PostMapping("v1/{domain}")
    public Response add(@RequestBody Map<String, String> data) {

        return null;
    }

    @PutMapping("v1/{domain}")
    public Response edit(@RequestBody Map<String, String> data) {
        return null;
    }




}
