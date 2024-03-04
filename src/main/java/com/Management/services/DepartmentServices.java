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
public class DepartmentServices {

    private final DepartmentRepo departmentRepo;

    private final TaskRepo taskRepo;

    private final EmployeeRepo employeeRepo;

    public Department create(Department department) {
        return departmentRepo.save(department);
    }

    public Department update(Integer id, Department department) {
        var dept = departmentRepo.existsById(id);
        if (dept) {
            return departmentRepo.save(department);
        }
        return null;
    }

    public Page<Department> getAll(Pageable pageable) {
        return departmentRepo.findAll(pageable);
    }

    public Department getById(Integer id){
        Optional<Department> department = departmentRepo.findById(id);
       return department.orElse(null);
    }

    public Department delete(Integer id){
        var dept = departmentRepo.existsById(id);
        if (dept){
            departmentRepo.deleteById(id);
        }
        return null;
    }

    public Page<Employee> getByPage(Pageable pageable, String empName ) {
        return employeeRepo.findAllByDeptName(pageable, empName);
    }

    public Page<Department> getAllDeptByRole(Pageable pageable, String empRole) {
        return departmentRepo.findAllDeptByRole(pageable, empRole);
    }

    public Page<Department> getByDeptByTask(Pageable pageable, String taskName) {
        List<Task> tasks = taskRepo.findTask(taskName);
        for (Task task: tasks) {
            String singleTask = task.getTaskName();
            return departmentRepo.findAllDeptByTask(singleTask, pageable);
        }

        return Page.empty();
    }
}
