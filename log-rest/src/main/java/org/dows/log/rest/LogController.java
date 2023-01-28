package org.dows.log.rest;

import org.dows.framework.api.Response;
import org.dows.log.service.LogService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LogController {

    protected LogService service;


    @GetMapping("/list")
    public Response list(@RequestParam Map<String, String> data) {
        return null;
    }

    @PostMapping("/export")
    public void export(HttpServletResponse response, Map<String, String> data) {
    }

    @GetMapping(value = "/{id}")
    public Response getInfo(@PathVariable("id") Integer id) {
        return null;
    }

    @PostMapping
    public Response add(@RequestBody Map<String, String> data) {
        return null;
    }

    @PutMapping
    public Response edit(@RequestBody Map<String, String> data) {
        return null;
    }

    @DeleteMapping("/{ids}")
    public Response remove(@PathVariable Integer[] ids) {
        return null;
    }


}
