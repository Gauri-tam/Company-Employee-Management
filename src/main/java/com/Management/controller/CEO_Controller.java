package com.Management.controller;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.jwtrequest.UserRegisterRequest;
import com.Management.jwtresponse.UserRegisterResponse;
import com.Management.services.CEO_Services;
import com.Management.services.DepartmentServices;
import com.Management.services.EmployeeServices;
import com.Management.services.TaskServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ceo")
@Tag(name = "CEO Controller", description = "it will control all the operation by using only CEO's Token or Username And Password")
@RequiredArgsConstructor
public class CEO_Controller {

    private final DepartmentServices departmentServices;

    private final EmployeeServices employeeServices;

    private final TaskServices taskServices;

    private final CEO_Services ceo_services;

    /**
     * creating Manager And Team Lead by CEO
     * */
    //http://localhost:8080/api/v1/ceo/manager

    @PostMapping("/register/manager")
    @Operation(summary = "register the CEO")

    public ResponseEntity<UserRegisterResponse> managerRegister(@RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        var user = ceo_services.createManager(request, req);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/ceo/team-lead

    @PostMapping("/register/team-lead")
    @Operation(summary = "register the Team Leader")
    public ResponseEntity<UserRegisterResponse> teamLeadRegister(@RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        var user  = ceo_services.createTeamLead(request, req);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Accessing all Operation of Department
     * */
    @PostMapping("/dept")
    @Operation(summary = "Post the Department")
    public ResponseEntity<Department> create(@RequestBody Department department){
        return ResponseEntity.ok(departmentServices.create(department));
    }

    @PutMapping("/dept/{id}")
    @Operation(summary = "update the dept by id")
    public ResponseEntity<Department> update(@PathVariable("id") Integer id,@RequestBody Department department){
        return ResponseEntity.ok(departmentServices.update(id, department));
    }

    @GetMapping("/dept/get")
    @Operation(summary = "get The dept Value")
    public ResponseEntity<Page<Department>> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                   @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAll(pageable));
    }

    @GetMapping("/dept/role")
    @Operation(summary = "get the dept value by role")
    public ResponseEntity<Page<Department>> getDeptByRole(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                          @RequestParam(value = "role", defaultValue = "", required = true) String role ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getAllDeptByRole(pageable, role));
    }

    @GetMapping("/dept/page")
    @Operation(summary = "get the dept by employee name")
    public ResponseEntity<Page<Employee>> getByPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByPage(pageable, empName));
    }

    // http://localhost:8080/api/v1/comp/dept/task
    @GetMapping("/dept/task")
    @Operation(summary = "get the dept by task name")
    public ResponseEntity<Page<Department>> getByDeptByTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                            @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                            @RequestParam(value = "taskName", defaultValue = "", required = true) String taskName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(departmentServices.getByDeptByTask(pageable, taskName));
    }


    @GetMapping("/dept/{id}")
    @Operation(summary = "get the dept by id")
    public ResponseEntity<Department> getById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentServices.getById(id));
    }

    @DeleteMapping("/dept{id}")
    @Operation(summary = "delete the dept by id")
    public ResponseEntity<Department> delete(@PathVariable("id") Integer id){
        return ResponseEntity.ok(departmentServices.delete(id));
    }

    /**
     * Accessing all Operation of Employee
     * */
    @PostMapping("/emp")
    @Operation(summary = "Creating Employee by Ceo Token only")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.create(employee));
    }

    @PutMapping("/emp/{id}")
    @Operation(summary = "update Employee by id by Ceo Token only ")
    public ResponseEntity<Employee> update(@PathVariable("id")Integer id,@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.update(id, employee));
    }

    @GetMapping("/emp/get")
    @Operation(summary = "get All Employee by Ceo Token only ")
    public ResponseEntity<Page<Employee>> getEmpAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getAll(pageable));
    }

    @GetMapping("/emp/page")
    @Operation(summary = "get All Employee by deptName by Ceo Token only ")
    public ResponseEntity<Page<Department>> getByEmpPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                      @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                      @RequestParam(value = "deptName", defaultValue = "Tester", required = true) String deptName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByPage(pageable , deptName));
    }

    @GetMapping("/emp/task")
    @Operation(summary = "get All Employee by taskName by Ceo Token only ")
    public ResponseEntity<Page<Employee>> getByTaskPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                        @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize ,
                                                        @RequestParam(value = "taskName", defaultValue = "project", required = true) String taskName ){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getByTaskPage(pageable , taskName));
    }

    @GetMapping("/emp/{id}")
    @Operation(summary = "get All Employee by id by Ceo Token only ")
    public ResponseEntity<Employee> getByEmpId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeServices.getById(id));
    }

    @DeleteMapping("/emp/{id}")
    @Operation(summary = "Delete Employee by id by Ceo Token only ")
    public ResponseEntity<Employee> deleteEmp(@PathVariable("id")Integer id){
        return ResponseEntity.ok(employeeServices.delete(id));
    }

    /**
     * Accessing all Operation of Task
     * */

    @PostMapping("/task")
    @Operation(summary = "Create Task  by Ceo Token only ")
    public ResponseEntity<Task> create(@RequestBody Task task){
        return ResponseEntity.ok(taskServices.create(task));
    }

    @PutMapping("/task/{id}")
    @Operation(summary = "update task by id by Ceo Token only ")
    public ResponseEntity<Task> update(@PathVariable("id") Integer id,@RequestBody Task task){ //uploading
        return ResponseEntity.ok(taskServices.update(id, task));
    }

    @GetMapping("/task/get")
    @Operation(summary = "get all task by id by Ceo Token only ")
    public ResponseEntity<Page<Task>> getTaskAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getAll(pageable));
    }

    @GetMapping("/task/{id}")
    @Operation(summary = "get task by id by Ceo Token only ")
    public ResponseEntity<Task> getByIdTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.getById(id));
    }

    @GetMapping("/task/page")
    @Operation(summary = "get task by employee name by Ceo Token only ")
    public ResponseEntity<Page<Employee>> getByPageTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getByPage(pageable, empName));
    }

    @DeleteMapping("/task/{id}")
    @Operation(summary = "delete task by id by Ceo Token only ")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.delete(id));
    }
}
