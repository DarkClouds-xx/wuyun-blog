package com.wuyun.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.wuyun.enums.ZoneEnum.SHANGHAI;


/**
 * MyBatis Plus自动填充
 *
 * @author DarkClouds
 * @date 2023/05/09
 */
@Log4j2
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增：自动填充createTime:" + LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
    }

    //更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新：自动填充updateTime:" + LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())));
    }
}
