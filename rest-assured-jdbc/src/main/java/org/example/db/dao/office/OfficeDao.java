package org.example.db.dao.office;

import org.example.db.dao._base._BaseMainDao;
import org.example.db.dao.office.entity.DepartmentEntity;
import org.example.db.dao.office.entity.EmployeeEntity;

import java.util.List;

public class OfficeDao extends _BaseMainDao {

    public OfficeDao() {
        super();
    }

    public List<EmployeeEntity> findAllEmployers() {
        final String query = "SELECT * FROM Employee";

        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(query)
                            .mapToBean(EmployeeEntity.class)
                            .list()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса findAllEmployers", e);
        }
    }

    public List<EmployeeEntity> findEmployersByName(String name) {
        final String query = "SELECT * FROM Employee WHERE Name = :name";

        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(query)
                            .bind("name", name)
                            .mapToBean(EmployeeEntity.class)
                            .list()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса findEmployersByName", e);
        }
    }

    public DepartmentEntity findDepartmentByName(String name) {
        final String query = "SELECT * FROM Department WHERE Name = :name";

        try {
            return jdbi.withHandle(handle ->
                    handle.createQuery(query)
                            .bind("name", name)
                            .mapToBean(DepartmentEntity.class)
                            .one()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса findDepartmentByName", e);
        }
    }

    public void updateEmployerDepartment(int employerId, int departmentId) {
        final String query = "UPDATE Employee SET DepartmentID = :departmentId WHERE ID = :employeeId";

        try {
            jdbi.useHandle(handle ->
                    handle.createUpdate(query)
                            .bind("employeeId", employerId)
                            .bind("departmentId", departmentId)
                            .execute()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса updateEmployerDepartment", e);
        }
    }

    public void updateEmployerName(int employerId, String employerName) {
        final String query = "UPDATE Employee SET Name = :employerName WHERE ID = :employeeId";

        try {
            jdbi.useHandle(handle ->
                    handle.createUpdate(query)
                            .bind("employeeId", employerId)
                            .bind("employerName", employerName)
                            .execute()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса updateEmployerName", e);
        }
    }

    public void deleteDepartmentByName(String name) {
        final String query = "DELETE FROM Department WHERE Name = :name";

        try {
            jdbi.useHandle(handle ->
                    handle.createUpdate(query)
                            .bind("name", name)
                            .execute()
            );
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении запроса deleteDepartmentByName", e);
        }
    }
}