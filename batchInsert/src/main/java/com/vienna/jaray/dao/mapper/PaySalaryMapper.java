package com.vienna.jaray.dao.mapper;

import com.vienna.jaray.dao.entity.PaySalaryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PaySalaryMapper {
    Integer saveBatch(List<PaySalaryEntity> list);
}
