package com.vienna.jaray.service;

import com.vienna.jaray.dao.entity.PaySalaryEntity;
import com.vienna.jaray.dao.mapper.PaySalaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 创建异步线程 业务类
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    @Override
    @Async("asyncServiceExecutor")
    public void executeAsync(List<PaySalaryEntity> list, PaySalaryMapper paySalaryMapper, CountDownLatch countDownLatch) {
        try{
            log.warn("start executeAsync");
            //异步线程要做的事情
            paySalaryMapper.saveBatch(list);
            log.warn("end executeAsync");
        }finally {
            countDownLatch.countDown();// 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
        }
    }
}
