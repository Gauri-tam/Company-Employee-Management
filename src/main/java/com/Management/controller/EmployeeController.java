package com.Management.controller;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.services.EmployeeServices;
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
@Tag(name = "Employee Controller", description = "it will control all the operation by using only Employee's Token")
@RequestMapping("/api/v1/comp")
public class EmployeeController {

    // http://localhost:8080/api/v1/comp/emp/page

    private final EmployeeServices employeeServices;

    @PostMapping("/emp")
    @Operation(summary = "Create Employee")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
       return ResponseEntity.ok(employeeServices.create(employee));
    }

    @PutMapping("/emp/{id}")
    @Operation(summary = "Update Employee by id")
    public ResponseEntity<Employee> update(@PathVariable("id")Integer id,@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.update(id, employee));
    }

    @GetMapping("/emp/get")
    @Operation(summary = "get All employee by pageNo and pageSize")
    public ResponseEntity<Page<Employee>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getAll(pageable));
    }

    @GetMapping("/emp/page")
    @Operation(summary = "get All employee by pageNo, pageSize and department Name")
    public ResponseEntity<Page<Department>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                      @RequestParam(value = "deptName", defaultValue = "T-1", required = true) String deptName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByPage(pageable , deptName));
    }

    @GetMapping("/emp/task")
    @Operation(summary = "get All employee by pageNo, pageSize and Task name")
    public ResponseEntity<Page<Employee>> getByTaskPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                @RequestParam(value = "taskName", defaultValue = "project", required = true) String taskName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByTaskPage(pageable , taskName));
}

    @GetMapping("/emp/{id}")
    @Operation(summary = "get All employee by id")
    public ResponseEntity<Employee> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeServices.getById(id));
    }

    @DeleteMapping("/emp/{id}")
    @Operation(summary = "Delete Employee By id")
    public ResponseEntity<Employee> delete(@PathVariable("id")Integer id){
        return ResponseEntity.ok(employeeServices.delete(id));
    }
}
