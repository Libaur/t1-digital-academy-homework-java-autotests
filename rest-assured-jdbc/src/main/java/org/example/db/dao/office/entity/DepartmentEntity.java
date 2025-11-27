package org.example.db.dao.office.entity;

import lombok.*;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class DepartmentEntity {

    @ColumnName("ID")
    private Integer id;

    @ColumnName("Name")
    private String name;
}
