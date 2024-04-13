package com.vienna.jaray.service;

import com.vienna.jaray.dao.entity.PaySalaryEntity;
import com.vienna.jaray.dao.mapper.PaySalaryMapper;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public interface AsyncService {

    public void executeAsync(List<PaySalaryEntity> list, PaySalaryMapper paySalaryMapper, CountDownLatch countDownLatch);
}
