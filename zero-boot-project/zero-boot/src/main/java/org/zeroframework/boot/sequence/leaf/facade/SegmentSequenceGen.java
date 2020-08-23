package org.zeroframework.boot.sequence.leaf.facade;

import org.zeroframework.boot.sequence.SequenceGen;
import org.zeroframework.boot.sequence.leaf.common.Result;
import org.zeroframework.boot.sequence.leaf.exception.InitException;
import org.zeroframework.boot.sequence.leaf.service.SegmentService;

import java.sql.SQLException;

/**
 * 基于美团 Leaf 号段方式生成 id
 */
public class SegmentSequenceGen implements SequenceGen {

    private final SegmentService segmentService;

    public SegmentSequenceGen(String url, String username, String pwd) throws InitException, SQLException {
        this.segmentService = new SegmentService(url, username, pwd);
    }

    @Override
    public long get() {
        Result result = segmentService.getId(DEFAULT_KEY);
        return result.getId();
    }

    @Override
    public long get(String key) {
        return segmentService.getId(key).getId();
    }
}
