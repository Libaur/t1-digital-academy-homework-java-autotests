package org.example.db.dao.office.entity;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EmployeeEntity {

    @ColumnName("ID")
    private int id;

    @ColumnName("Name")
    private String name;

    @ColumnName("DepartmentID")
    private int departmentId;
}
