package org.dows.demo;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import org.dows.log.core.LogContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestCrud {

    Map<String,? extends Model> logModel= new HashMap<>();

    public List<JSONObject> queryLog(String queryTable, Map<String,Object> queryParams){
        Model model = logModel.get(queryTable);

        if(model != null){
            QueryWrapper<?> wrapper = new QueryWrapper<>();
            //wrapper.eq();
           return model.selectList(wrapper);

        }
        return Collections.emptyList();
    }

    @Test
    public void  userEntityLogQuery(){


    }
}
