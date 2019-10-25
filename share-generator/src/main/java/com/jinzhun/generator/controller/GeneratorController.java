package com.jinzhun.generator.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.jinzhun.common.model.PageResult;
import com.jinzhun.generator.service.GeneratorService;

import io.swagger.annotations.Api;


@RestController
@Api(tags = "代码生成器")
@RequestMapping("/generator")
public class GeneratorController {
	
	@Autowired private GeneratorService generatorService;

    @ResponseBody
    @GetMapping("/list")
    public PageResult<Map<String, Object>> getTableList(@RequestParam Map<String, Object> params) {
        return generatorService.queryList(params);
    }
    
    @GetMapping("/code")
    public void makeCode(String tables, HttpServletResponse response) throws IOException {
        byte[] data = generatorService.generatorCode(tables.split(","));
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generator.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}
