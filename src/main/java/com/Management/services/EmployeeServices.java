package com.Management.services;

import com.Management.entity.Department;
import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.repository.DepartmentRepo;
import com.Management.repository.EmployeeRepo;
import com.Management.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServices {

    private final EmployeeRepo employeeRepo;

    private final DepartmentRepo departmentRepo;

    private final TaskRepo taskRepo;

    public Employee create(Employee employee) {
        return employeeRepo.save(employee);
    }

    public Employee update(Integer id, Employee employee) {
        var dept = employeeRepo.existsById(id);
        if (dept) {
            return employeeRepo.save(employee);
        }
        return null;
    }

    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    public Employee getById(Integer id){
        Optional<Employee> department = employeeRepo.findById(id);
        return department.orElse(null);
    }

    public Employee delete(Integer id){
        var dept = employeeRepo.existsById(id);
        if (dept){
            employeeRepo.deleteById(id);
        }
        return null;
    }

    public Page<Department> getByPage(Pageable pageable, String deptName) {
        return departmentRepo.findAllEmployeeByDeptName(pageable, deptName);
    }

    public Page<Employee> getByTaskPage(Pageable pageable, String taskName) {

        List<Task> tasks = taskRepo.findTask(taskName);

        for (Task task : tasks) {

            String singleTask = task.getTaskName();
            return employeeRepo.findAllEmpByTask(pageable, singleTask);
        }
        return Page.empty();
    }
}
