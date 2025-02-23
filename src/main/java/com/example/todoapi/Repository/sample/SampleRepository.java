package com.example.todoapi.Repository.sample;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.example.todoapi.Repository.SampleRecord;

@Mapper
public interface SampleRepository {
    
    @Select("SELECT content FROM samples ORDER BY id LIMIT 1")
    SampleRecord select();
}
