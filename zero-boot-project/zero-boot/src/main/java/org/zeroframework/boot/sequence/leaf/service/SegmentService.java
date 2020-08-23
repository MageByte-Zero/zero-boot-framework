package org.zeroframework.boot.sequence.leaf.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeroframework.boot.sequence.leaf.IDGen;
import org.zeroframework.boot.sequence.leaf.common.Result;
import org.zeroframework.boot.sequence.leaf.exception.InitException;
import org.zeroframework.boot.sequence.leaf.segment.SegmentIDGenImpl;
import org.zeroframework.boot.sequence.leaf.segment.dao.IDAllocDao;
import org.zeroframework.boot.sequence.leaf.segment.dao.impl.IDAllocDaoImpl;

import java.sql.SQLException;

public class SegmentService {
    private final Logger logger = LoggerFactory.getLogger(SegmentService.class);

    private final IDGen idGen;
    private final DruidDataSource dataSource;

    public SegmentService(String url, String username, String pwd) throws SQLException, InitException {
        Preconditions.checkNotNull(url,"database url can not be null");
        Preconditions.checkNotNull(username,"username can not be null");
        Preconditions.checkNotNull(pwd,"password can not be null");
        // Config dataSource
        dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(pwd);
        dataSource.init();
        // Config Dao
        IDAllocDao dao = new IDAllocDaoImpl(dataSource);
        // Config ID Gen
        idGen = new SegmentIDGenImpl();
        ((SegmentIDGenImpl) idGen).setDao(dao);
        if (idGen.init()) {
            logger.info("Segment Service Init Successfully");
        } else {
            throw new InitException("Segment Service Init Fail");
        }
    }

    public Result getId(String key) {
        return idGen.get(key);
    }

    public SegmentIDGenImpl getIdGen() {
        if (idGen instanceof SegmentIDGenImpl) {
            return (SegmentIDGenImpl) idGen;
        }
        return null;
    }
}
