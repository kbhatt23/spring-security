package com.learning.weblogin.service;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import com.learning.weblogin.entity.UserDetail;

public interface ExcelGeneratorService {

    Workbook generateExcel(List<UserDetail> userDetails);
}
