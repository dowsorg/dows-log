package org.dows.log.api;

public interface BinlogListener<T> {
    void onUpdate(T from, T to);
    void onInsert(T data);
    void onDelete(T data);
}
