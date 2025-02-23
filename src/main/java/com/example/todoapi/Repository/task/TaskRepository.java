package com.example.todoapi.Repository.task;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TaskRepository {

    @Select("SELECT id, title FROM tasks WHERE id = #{taskId}") 
    Optional<TaskRecord> select(long taskId);

    @Options(useGeneratedKeys = true, keyProperty = "id") //②そのために　@Options　を付与（オートインクリメントされたidが TaskRecordのid にセットされる）
    @Insert("INSERT INTO tasks (title) VALUES (#{title})") //①MyBatisでは@Insertがついたメソッドの戻り値は void でなければならない。
    void insert(TaskRecord record);                         

}
