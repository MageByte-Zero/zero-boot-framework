package org.zeroframework.boot.sequence.leaf;


import org.zeroframework.boot.sequence.leaf.common.Result;

public interface IDGen {
    Result get(String key);
    boolean init();
}
