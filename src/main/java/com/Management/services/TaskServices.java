package com.Management.services;

import com.Management.entity.Employee;
import com.Management.entity.Task;
import com.Management.repository.EmployeeRepo;
import com.Management.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServices {

    private final TaskRepo taskRepo;

    private final EmployeeRepo employeeRepo;

    public Task create(Task task) {
        return taskRepo.save(task);
    }

    public Task update(Integer id, Task task) {
        var dept = taskRepo.existsById(id);
        if (dept) {
            return taskRepo.save(task);
        }
        return null;
    }

    public Page<Task> getAll(Pageable pageable) {
        return taskRepo.findAll(pageable);
    }

    public Task getById(Integer id){
        Optional<Task> department = taskRepo.findById(id);
        return department.orElse(null);
    }

    public Task delete(Integer id){
        var dept = taskRepo.existsById(id);
        if (dept){
            taskRepo.deleteById(id);
        }
        return null;
    }

    public Page<Employee> getByPage(Pageable pageable, String empName) {
        return employeeRepo.findAllTaskBYEmpName(pageable, empName);
    }
}
