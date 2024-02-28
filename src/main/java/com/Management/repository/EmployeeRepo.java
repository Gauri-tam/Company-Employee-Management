package com.Management.repository;

import com.Management.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    //  finding employee name to get dept
    @Query(value = "select e from Employee e left join Department d on e.empId = d.deptId where e.empName like %:empName%")
    Page<Employee> findAllByDeptName(Pageable pageable,@Param("empName") String empName);

    // finding employee name with all task
    @Query(value = "select e from Employee e left join Task t on e.empId = t.taskId where e.empName like %:empName%")
    Page<Employee> findAllTaskBYEmpName(Pageable pageable,@Param("empName") String empName);

    // finding all employee by there task
    @Query(value = "select * from employee e left join  emp_task et on e.emp_id = et.emps_id left join task t on t.task_id = et.tasks_id where t.task_name like %:taskName% order by e.emp_id DESC", nativeQuery = true)
    Page<Employee> findAllEmpByTask(Pageable pageable, String taskName);
}
