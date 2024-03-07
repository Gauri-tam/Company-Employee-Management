package com.Management.controller;

import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.services.TaskServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name ="Task Controller", description = "it will Use for task operations")
@RequestMapping("/api/v1/comp")
public class TaskController {

    private final TaskServices  taskServices;

    // http://localhost:8080/api/v1/comp/task

    @PostMapping("/task")
    @Operation(summary = "Create Task")
    public ResponseEntity<Task> create(@RequestBody Task task){
        return ResponseEntity.ok(taskServices.create(task));
    }

    @PutMapping("/task/{id}")
    @Operation(summary = "Update Task by id")
    public ResponseEntity<Task> update(@PathVariable("id") Integer id,@RequestBody Task task){
        return ResponseEntity.ok(taskServices.update(id, task));
    }

    @GetMapping("/task/get")
    @Operation(summary = "get All employee by pageNo and pageSize")
    public ResponseEntity<Page<Task>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getAll(pageable));
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "get All Task By id")
    public ResponseEntity<Task> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.getById(id));
    }

    @GetMapping("/task/page")
    @Operation(summary = "get All task by pageNo, pageSize and department Name")
    public ResponseEntity<Page<Employee>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getByPage(pageable, empName));
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "Delete Task By Id")
    public ResponseEntity<Task> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.delete(id));
    }
}
