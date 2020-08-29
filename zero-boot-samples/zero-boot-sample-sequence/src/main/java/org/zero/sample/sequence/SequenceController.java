package org.zero.sample.sequence;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zeroframework.boot.message.ResultDTO;
import org.zeroframework.boot.sequence.leaf.facade.SegmentSequenceGen;
import org.zeroframework.boot.sequence.leaf.facade.SnowflakeSequenceGen;

import java.util.ArrayList;

@RestController
@RequestMapping("/sequence")
public class SequenceController {

    @Autowired
    private SnowflakeSequenceGen snowflakeSequenceGen;

    @Autowired
    private SegmentSequenceGen segmentSequenceGen;

    @GetMapping("/snowflake")
    public ResultDTO<ArrayList<Long>> snowflake(@RequestParam String key) {
        ArrayList<Long> list = Lists.newArrayList();
        for (int i = 1; i < 1000; ++i) {
            long id = snowflakeSequenceGen.get(key);
            list.add(id);
        }
        return ResultDTO.success(list);
    }

    @GetMapping("/segment")
    public ResultDTO<ArrayList<Long>> segment() {
        ArrayList<Long> list = Lists.newArrayList();
        for (int i = 1; i < 1000; ++i) {
            long id = segmentSequenceGen.get("leaf-segment-test");
            list.add(id);
        }
        return ResultDTO.success(list);
    }
}