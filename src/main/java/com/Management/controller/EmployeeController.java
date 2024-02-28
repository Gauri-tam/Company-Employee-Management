package com.Management.controller;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.services.EmployeeServices;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comp")
public class EmployeeController {

    // http://localhost:8080/api/v1/comp/emp/task

    private final EmployeeServices employeeServices;

    @PostMapping("/emp")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
       return ResponseEntity.ok(employeeServices.create(employee));
    }

    @PutMapping("/emp/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id")Integer id,@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.update(id, employee));
    }

    @GetMapping("/emp/get")
    public ResponseEntity<Page<Employee>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getAll(pageable));
    }

    @GetMapping("/emp/page")
    public ResponseEntity<Page<Department>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                      @RequestParam(value = "deptName", defaultValue = "Tester", required = true) String deptName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByPage(pageable , deptName));
    }

    @GetMapping("/emp/task")
    public ResponseEntity<Page<Employee>> getByTaskPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                @RequestParam(value = "taskName", defaultValue = "project", required = true) String taskName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByTaskPage(pageable , taskName));
}

    @GetMapping("/emp/{id}")
    public ResponseEntity<Employee> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeServices.getById(id));
    }

    @DeleteMapping("/emp/{id}")
    public ResponseEntity<Employee> delete(@PathVariable("id")Integer id){
        return ResponseEntity.ok(employeeServices.delete(id));
    }
}
