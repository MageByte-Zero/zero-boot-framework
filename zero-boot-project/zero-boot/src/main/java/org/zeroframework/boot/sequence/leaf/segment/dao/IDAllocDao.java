package org.zeroframework.boot.sequence.leaf.segment.dao;


import org.zeroframework.boot.sequence.leaf.segment.model.LeafAlloc;

import java.util.List;

public interface IDAllocDao {
    List<LeafAlloc> getAllLeafAllocs();

    LeafAlloc updateMaxIdAndGetLeafAlloc(String tag);

    LeafAlloc updateMaxIdByCustomStepAndGetLeafAlloc(LeafAlloc leafAlloc);

    List<String> getAllTags();
}
