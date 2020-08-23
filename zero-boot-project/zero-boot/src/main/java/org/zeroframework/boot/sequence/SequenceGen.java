package org.zeroframework.boot.sequence;

/**
 * ID 生成接口
 */
public interface SequenceGen {

    String DEFAULT_KEY = "defaultKey";

    /**
     * 获取趋势递增的唯一 ID
     * @return 唯一 ID
     */
    long get();

    /**
     * 获取趋势递增的唯一 ID
     * @param key 指定 key
     * @return 唯一 ID
     */
    long get(String key);
}
