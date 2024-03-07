package com.Management.controller;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.services.DepartmentServices;
import com.Management.services.EmployeeServices;
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
@RequestMapping("/api/v1/tl")
@Tag(name ="Team Leader Controller", description = "it will use only by TEAM LEADER Token only")
@RequiredArgsConstructor
public class TL_Controller {

    // http://localhost:8080/api/v1/tl/task/1

    private final DepartmentServices departmentServices;

    private final EmployeeServices employeeServices;

    private final TaskServices taskServices;

    /**
     * Accessing only get Operation of Department
     * */

    @GetMapping("/dept/get")
    @Operation(summary = "get All Department by pageNo and pageSize ")
    public ResponseEntity<Page<Department>> getAllDept(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAll(pageable));
    }

    @GetMapping("/dept/role")
    @Operation(summary = "get All Department by pageNo, pageSize and empRole")
    public ResponseEntity<Page<Department>> getDeptByRole(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                          @RequestParam(value = "empRole", defaultValue = "Dovloper", required = true) String role ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAllDeptByRole(pageable, role));
    }

    @GetMapping("/dept/page")
    @Operation(summary = "get All Department by pageNo, pageSize empName")
    public ResponseEntity<Page<Employee>> getByPageDept(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByPage(pageable, empName));
    }

    // http://localhost:8080/api/v1/tl/dept/task
    @GetMapping("/dept/task")
    @Operation(summary = "get All Department by pageNo, pageSize Task Name")
    public ResponseEntity<Page<Department>> getByDeptByTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                            @RequestParam(value = "taskName", defaultValue = "", required = true) String taskName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByDeptByTask(pageable, taskName));
    }

    @GetMapping("/dept/{id}")
    @Operation(summary = "get All Department by id")
    public ResponseEntity<Department> getByIdDept(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentServices.getById(id));
    }

    /**
     * Accessing only get Operation of Employee
     * */

    @GetMapping("/emp/get")
    @Operation(summary = "get All Employee by pageNo and pageSize ")
    public ResponseEntity<Page<Employee>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getAll(pageable));
    }

    @GetMapping("/emp/page")
    @Operation(summary = "get All Employee by pageNo, pageSize deptName")
    public ResponseEntity<Page<Department>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                      @RequestParam(value = "deptName", defaultValue = "Tester", required = true) String deptName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByPage(pageable , deptName));
    }

    @GetMapping("/emp/task")
    @Operation(summary = "get All Employee by pageNo, pageSize Task Name")
    public ResponseEntity<Page<Employee>> getByTaskPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                        @RequestParam(value = "taskName", defaultValue = "project", required = true) String taskName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByTaskPage(pageable , taskName));
    }

    @GetMapping("/emp/{id}")
    @Operation(summary = "get All employee by id")
    public ResponseEntity<Employee> getByIdEmp(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeServices.getById(id));
    }

    /**
     * Accessing only get Operation of Task
     * */

    @GetMapping("/task/get")
    @Operation(summary = "get All Task by pageNo and pageSize ")
    public ResponseEntity<Page<Task>> getAllTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getAll(pageable));
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "get All Task by id")
    public ResponseEntity<Task> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.getById(id));
    }

    @GetMapping("/task/page")
    @Operation(summary = "get All Task by pageNo, pageSize empName")
    public ResponseEntity<Page<Employee>> getByPageTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getByPage(pageable, empName));
    }
}
