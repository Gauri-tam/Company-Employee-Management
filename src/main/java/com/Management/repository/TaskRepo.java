package com.Management.repository;

import com.Management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task, Integer> {

    // find a task to help to get all employee in EmployeeServer.
    @Query("select t from Task t where t.taskName = :taskName")
    List<Task> findTask(@Param("taskName") String taskName);
}
