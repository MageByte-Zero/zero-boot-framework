package org.zeroframework.boot.sequence.leaf.facade;

import org.zeroframework.boot.sequence.SequenceGen;
import org.zeroframework.boot.sequence.leaf.exception.InitException;
import org.zeroframework.boot.sequence.leaf.service.SnowflakeService;

/**
 * 基于美团 Leaf 雪花算法生成 id
 */
public class SnowflakeSequenceGen implements SequenceGen {

    private final SnowflakeService snowflakeService;

    public SnowflakeSequenceGen(String zkpath, int port) throws InitException {
        this.snowflakeService = new SnowflakeService(zkpath, port);
    }

    @Override
    public long get() {
        return snowflakeService.getId(DEFAULT_KEY).getId();
    }

    @Override
    public long get(String key) {
        return snowflakeService.getId(key).getId();
    }
}
