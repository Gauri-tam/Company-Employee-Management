package com.Management.controller;

import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.services.TaskServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comp")
public class TaskController {

    private final TaskServices  taskServices;

    // http://localhost:8080/api/v1/comp/task/page

    @PostMapping("/task")
    public ResponseEntity<Task> create(@RequestBody Task task){
        return ResponseEntity.ok(taskServices.create(task));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> update(@PathVariable("id") Integer id,@RequestBody Task task){
        return ResponseEntity.ok(taskServices.update(id, task));
    }

    @GetMapping("/task/get")
    public ResponseEntity<Page<Task>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                             @RequestParam(value = "pagesize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getAll(pageable));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.getById(id));
    }

    @GetMapping("/task/page")
    public ResponseEntity<Page<Employee>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getByPage(pageable, empName));
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Task> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.delete(id));
    }
}
