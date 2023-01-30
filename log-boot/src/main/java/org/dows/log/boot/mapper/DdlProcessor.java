//package org.dows.log.boot.mapper;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.dows.log.api.sql.MysqlServer;
//import org.mybatis.spring.SqlSessionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.ResourceUtils;
//
//import javax.sql.DataSource;
//import java.io.File;
//import java.io.IOException;
//
//public class DdlProcessor {
//    @Value("")
//    private String ddlPath;
//
//    private DataSource dataSource;
//
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    public void startStreamTask() {
//        try {
//            File file = ResourceUtils.getFile(ddlPath);
//
//            MysqlServer mysqlServer = objectMapper.readValue(file, MysqlServer.class);
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
