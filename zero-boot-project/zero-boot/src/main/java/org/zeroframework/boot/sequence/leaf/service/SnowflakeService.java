package org.zeroframework.boot.sequence.leaf.service;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroframework.boot.sequence.leaf.IDGen;
import org.zeroframework.boot.sequence.leaf.common.Result;
import org.zeroframework.boot.sequence.leaf.exception.InitException;
import org.zeroframework.boot.sequence.leaf.snowflake.SnowflakeIDGenImpl;

public class SnowflakeService {
    private final Logger logger = LoggerFactory.getLogger(SnowflakeService.class);

    private final IDGen idGen;
    public SnowflakeService(String zkpath,int port) throws InitException {
            Preconditions.checkNotNull(zkpath,"zookeeper path can not be null");
            Preconditions.checkNotNull(port,"zookeeper port  can not be null");
            idGen = new SnowflakeIDGenImpl(zkpath, port);
            if(idGen.init()) {
                logger.info("Snowflake Service Init Successfully");
            } else {
                throw new InitException("Snowflake Service Init Fail");
            }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }
}
