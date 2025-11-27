package org.example;

import org.example.db.dao._base.DatabaseManager;
import org.example.db.dao.office.OfficeDao;
import org.example.db.dao.office.entity.DepartmentEntity;
import org.example.db.dao.office.entity.EmployeeEntity;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

public class OfficeServiceTests {

    private OfficeDao officeDao;

    @BeforeAll
    static void init() {
        DatabaseManager.initializeDatabase();
    }

    @AfterAll
    static void tearDown() {
        DatabaseManager.shutdownDatabase();
    }

    @BeforeEach
    void setUp() {
        officeDao = new OfficeDao();
    }

    @Test
    void checkUpdateEmployeeDepartment() {

        DepartmentEntity department = officeDao.findDepartmentByName("HR");

        String employerName = "Ann";
        int departmentId = department.getId();

        List<EmployeeEntity> employers = officeDao.findEmployersByName(employerName);

        if (employers.size() == 1) {
            officeDao.updateEmployerDepartment(employers.get(0).getId(), departmentId);
        }

        List<EmployeeEntity> employersAfterUpdate = officeDao.findEmployersByName(employerName);

        Assertions.assertEquals(employersAfterUpdate.get(0).getDepartmentId(), departmentId);
    }

    @Test
    void checkUpdateEmployersNames() {

        List<EmployeeEntity> employers = officeDao.findAllEmployers();

        int updatedNamesCount = 0;

        for (EmployeeEntity employee : employers) {

            String employerName = employee.getName();
            int employerId = employee.getId();

            if (Character.isLowerCase(employerName.charAt(0))) {

                String correctName = employerName.substring(0,1).toUpperCase() + employerName.substring(1);
                officeDao.updateEmployerName(employerId, correctName);

                updatedNamesCount++;
            }
        }

        System.out.println("Количество исправленных имён: " + updatedNamesCount);

    }

    @Test
    void checkEmployersCountFromIT() {

        DepartmentEntity departmentWithNameIT = officeDao.findDepartmentByName("IT");
        List<EmployeeEntity> employers = officeDao.findAllEmployers();
        List<EmployeeEntity> employersFromIt = employers.stream()
                .filter(employee -> employee.getDepartmentId() == departmentWithNameIT.getId())
                .collect(Collectors.toList());

        System.out.println(employersFromIt.size());
    }

    @Test
    void checkCascadeDeleteEmployersFromDeletedDepartment() {

        DepartmentEntity departmentWithNameIT = officeDao.findDepartmentByName("IT");
        officeDao.deleteDepartmentByName("IT");
        List<EmployeeEntity> employers = officeDao.findAllEmployers();

        assertThat(employers)
                .noneMatch(employee -> employee.getDepartmentId() == departmentWithNameIT.getId());
    }
}