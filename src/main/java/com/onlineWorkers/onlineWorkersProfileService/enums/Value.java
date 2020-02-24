package com.onlineWorkers.onlineWorkersProfileService.enums;

public enum Value {
    ZERO(0);
    public long value;

    Value(long value){
        this.value=value;
    }

    public long getValue() {
        return value;
    }
}
