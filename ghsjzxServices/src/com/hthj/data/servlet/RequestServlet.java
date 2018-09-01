package com.hthj.data.servlet;

import com.hthj.data.domain.DockWorkInformation;
import com.hthj.data.setvice.RequestService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class RequestServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);

        Object dwi = null;
        Map<String, String[]> parameterMap = req.getParameterMap();

        RequestService requestService = new RequestService();
        //requestService.saveData(DockWorkInformation dwi);
        //requestService.saveData(dwi);
        //提示插入成功





    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
