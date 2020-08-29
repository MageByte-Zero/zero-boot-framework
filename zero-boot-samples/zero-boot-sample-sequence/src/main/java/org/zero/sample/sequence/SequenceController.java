package org.zero.sample.sequence;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zeroframework.boot.message.ResultDTO;
import org.zeroframework.boot.sequence.SequenceGen;

import java.util.ArrayList;

@RestController
@RequestMapping("/sequence")
public class SequenceController {

    @Autowired
    @Qualifier(value = "snowflakeSequenceGen")
    private SequenceGen snowflakeSequenceGen;

    @GetMapping("/snowflake")
    public ResultDTO<ArrayList<Long>> getId(@RequestParam String key) {
        ArrayList<Long> list = Lists.newArrayList();
        for (int i = 1; i < 1000; ++i) {
            long id = snowflakeSequenceGen.get(key);
            list.add(id);
        }
        return ResultDTO.success(list);
    }
}