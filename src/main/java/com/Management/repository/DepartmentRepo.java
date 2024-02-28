package com.Management.repository;

import com.Management.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepo  extends JpaRepository<Department, Integer> {

    @Query(value = "select d from Department d left join Employee e on d.deptId = e.empId where d.deptName like %:deptName%")
    Page<Department> findAllEmployeeByDeptName(Pageable pageable, @Param("deptName") String deptName);

    @Query(value = "select d.dept_id, d.dept_name, d.dept_no,e.emp_id, e.emp_name, e.emp_name, e.emp_role from Department d left join Employee e on e.dept_id = d.dept_id where e.emp_role like %:role%", nativeQuery = true)
    Page<Department> findAllDeptByRole(Pageable pageable,@Param("role") String role);

    @Query(value = "select d.dept_id, dept_name, dept_no, e.emp_id, e.emp_email, e.emp_name, e.emp_role, t.task_name from Department d left join Employee e on e.dept_id = d.dept_id left  join emp_task et on e.emp_id = et.emps_id left join Task t on t.task_id = et.tasks_id where t.task_name like %:taskName%", nativeQuery = true)
    Page<Department> findAllDeptByTask(@Param("taskName") String taskName, Pageable pageable);
}