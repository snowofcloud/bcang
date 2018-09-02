package com.hthj.data.service;

import com.hthj.data.dao.RequestDao;
import com.hthj.data.domain.DockWorkInformation;

import java.sql.SQLException;

public class RequestService {
    public void saveData(DockWorkInformation dwi) throws SQLException {
        RequestDao requestDao = new RequestDao();
        requestDao.saveData(dwi);
    }

}
