package com.Management.controller;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.services.DepartmentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comp")
public class DepartmentController {

    private final DepartmentServices departmentServices;

    @PostMapping("/dept")
    public ResponseEntity<Department> create(@RequestBody Department department){
        return ResponseEntity.ok(departmentServices.create(department));
    }

    @PutMapping("/dept/{id}")
    public ResponseEntity<Department> update(@PathVariable("id") Integer id,@RequestBody Department department){
        return ResponseEntity.ok(departmentServices.update(id, department));
    }

    @GetMapping("/dept/get")
    public ResponseEntity<Page<Department>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAll(pageable));
    }

    @GetMapping("/dept/role")
    public ResponseEntity<Page<Department>> getDeptByRole(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                          @RequestParam(value = "role", defaultValue = "Dovloper", required = true) String role ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAllDeptByRole(pageable, role));
    }

    @GetMapping("/dept/page")
    public ResponseEntity<Page<Employee>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByPage(pageable, empName));
    }

    // http://localhost:8080/api/v1/comp/dept/task
    @GetMapping("/dept/task")
    public ResponseEntity<Page<Department>> getByDeptByTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "taskName", defaultValue = "", required = true) String taskName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByDeptByTask(pageable, taskName));
    }


    @GetMapping("/dept/{id}")
    public ResponseEntity<Department> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentServices.getById(id));
    }

    @DeleteMapping("/dept{id}")
    public ResponseEntity<Department> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentServices.delete(id));
    }
}
