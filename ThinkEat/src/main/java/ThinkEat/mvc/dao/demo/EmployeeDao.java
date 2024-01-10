package ThinkEat.mvc.dao.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import ThinkEat.mvc.entity.demo.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer>{

}
