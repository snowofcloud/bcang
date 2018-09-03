package com.hthj.data.service;

import com.hthj.data.dao.RequestDao;
import com.hthj.data.domain.PortEnterpriseData;

import java.sql.SQLException;

public class RequestService {
    public void saveData(PortEnterpriseData dwi) throws SQLException {
        RequestDao requestDao = new RequestDao();
        requestDao.saveData(dwi);
    }

}
