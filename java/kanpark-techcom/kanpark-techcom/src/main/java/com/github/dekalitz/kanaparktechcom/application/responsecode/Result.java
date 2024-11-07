package com.github.dekalitz.kanaparktechcom.application.responsecode;

import com.github.dekalitz.kanaparktechcom.application.records.ResultRecord;

import java.util.Collections;

public class Result<T> {
    public ResultRecord<T> ok(T data) {
        return new ResultRecord<>("success", data, Collections.emptyList());
    }
}
