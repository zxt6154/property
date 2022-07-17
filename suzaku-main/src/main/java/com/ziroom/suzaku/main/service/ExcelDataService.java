package com.ziroom.suzaku.main.service;

import com.ziroom.suzaku.main.model.dto.resp.ExcelImportDetail;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelDataService {

    ExcelImportDetail listImport(MultipartFile file) throws IOException;

}
