package com.example.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充 部分字段的值
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) { //插入操作时，对应的需要填充的字段的赋值
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);//更新时间
        this.setFieldValByName("version",0, metaObject); //设置乐观锁字段 version 的默认值为 0
    }

    @Override
    public void updateFill(MetaObject metaObject) {//更新操作时，对应的需要填充的字段的赋值
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    }

}
