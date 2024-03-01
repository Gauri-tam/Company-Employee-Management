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
    public ResponseEntity<UserRegisterResponse> managerRegister(@RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        var user = ceo_services.createManager(request, req);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/v1/ceo/team-lead

    @PostMapping("/register/team-lead")
    public ResponseEntity<UserRegisterResponse> teamLeadRegister(@RequestBody UserRegisterRequest request, HttpServletRequest req) throws Exception {
        var user  = ceo_services.createTeamLead(request, req);
       return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    /**
     * Accessing all Operation of Department
     * */
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
                                                          @RequestParam(value = "role", defaultValue = "", required = true) String role ){
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

    /**
     * Accessing all Operation of Employee
     * */
    @PostMapping("/emp")
    public ResponseEntity<Employee> create(@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.create(employee));
    }

    @PutMapping("/emp/{id}")
    public ResponseEntity<Employee> update(@PathVariable("id")Integer id,@RequestBody Employee employee){
        return ResponseEntity.ok(employeeServices.update(id, employee));
    }

    @GetMapping("/emp/get")
    public ResponseEntity<Page<Employee>> getEmpAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                 @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(employeeServices.getAll(pageable));
    }

    @GetMapping("/emp/page")
    public ResponseEntity<Page<Department>> getByEmpPage(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
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
    public ResponseEntity<Employee> getByEmpId(@PathVariable("id") Integer id){
        return ResponseEntity.ok(employeeServices.getById(id));
    }

    @DeleteMapping("/emp/{id}")
    public ResponseEntity<Employee> deleteEmp(@PathVariable("id")Integer id){
        return ResponseEntity.ok(employeeServices.delete(id));
    }

    /**
     * Accessing all Operation of Task
     * */

    @PostMapping("/task")
    public ResponseEntity<Task> create(@RequestBody Task task){
        return ResponseEntity.ok(taskServices.create(task));
    }

    @PutMapping("/task/{id}")
    public ResponseEntity<Task> update(@PathVariable("id") Integer id,@RequestBody Task task){ //uploading
        return ResponseEntity.ok(taskServices.update(id, task));
    }

    @GetMapping("/task/get")
    public ResponseEntity<Page<Task>> getTaskAll(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                             @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getAll(pageable));
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Task> getByIdTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.getById(id));
    }

    @GetMapping("/task/page")
    public ResponseEntity<Page<Employee>> getByPageTask(@RequestParam(value = "pageNo", defaultValue = "0", required = true) Integer pageNo,
                                                    @RequestParam(value = "pageSize", defaultValue = "5", required = true) Integer pageSize,
                                                    @RequestParam(value = "empName", defaultValue = "Grower", required = true) String empName){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(taskServices.getByPage(pageable, empName));
    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(taskServices.delete(id));
    }
}