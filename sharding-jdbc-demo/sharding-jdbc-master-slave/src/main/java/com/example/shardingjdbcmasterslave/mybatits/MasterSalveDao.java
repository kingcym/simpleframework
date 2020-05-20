package com.example.shardingjdbcmasterslave.mybatits;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface MasterSalveDao {
    List<String> getList();
}
