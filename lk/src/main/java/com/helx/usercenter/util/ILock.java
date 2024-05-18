package com.helx.usercenter.util;

public interface ILock {

    boolean tryLock(long timeoutSec);

    void unlock();

}
