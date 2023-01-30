package org.dows.log.rest;

import org.dows.framework.api.Response;
import org.dows.log.api.annotation.DomainMapping;
import org.dows.log.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class DomainController {

    protected LogService service;

    /**
     * 获取所有的domains
     *
     * @return
     */
    @DomainMapping
    @GetMapping(value = "v1/domains")
    public Response getDomains() {
        return null;
    }

    @DomainMapping
    @GetMapping(value = "v1/{domain}/{id}")
    public Response getById(@PathVariable(value = "domain") String domain, @PathVariable("id") Long id) {
        service.selectById(domain, id);
        return null;
    }
    @DomainMapping
    @GetMapping(value = "v1/{domain}/{ids}")
    public Response getByIds(@PathVariable(value = "domain") String domain, @PathVariable("ids") String ids) {
        service.selectByIds(domain, ids);
        return null;
    }
    @DomainMapping
    @DeleteMapping("v1/{domain}/{ids}")
    public Response remove(@PathVariable Integer[] ids) {
        return null;
    }
    @DomainMapping
    @GetMapping("v1/{domain}/list")
    public Response list(@PathVariable(value = "domain") String domain, HttpServletRequest httpServletRequest) {
        service.selectListByMap(null, null);
        return null;
    }

    @DomainMapping
    @GetMapping("v1/{domain}/page")
    public Response page(@PathVariable(value = "domain") String domain, HttpServletRequest httpServletRequest) {

        service.selectListByMap(null, null);
        return null;
    }

    @DomainMapping
    @PostMapping("v1/{domain}")
    public Response add(@RequestBody Map<String, String> data) {

        return null;
    }
    @DomainMapping
    @PutMapping("v1/{domain}")
    public Response edit(@RequestBody Map<String, String> data) {
        return null;
    }




}
