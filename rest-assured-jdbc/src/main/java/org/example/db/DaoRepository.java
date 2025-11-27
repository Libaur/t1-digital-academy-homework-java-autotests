package org.example.db;

import lombok.experimental.UtilityClass;
import org.example.db.dao.office.OfficeDao;

@UtilityClass
public class DaoRepository {

    public final OfficeDao officeDao = new OfficeDao();
}
