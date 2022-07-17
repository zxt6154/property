package com.ziroom.suzaku.main.common;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Maps;
import com.ziroom.suzaku.main.dao.SuzakuImportAssertsMapper;
import com.ziroom.suzaku.main.dao.SuzakuSkuMapper;
import com.ziroom.suzaku.main.listeners.BorrowOrRecipientListener;
import com.ziroom.suzaku.main.listeners.ExcelImportListener;
import com.ziroom.suzaku.main.model.dto.req.BorrowRecipientExcelReq;
import com.ziroom.suzaku.main.model.dto.req.ExcelImportAssertReq;
import com.ziroom.suzaku.main.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ExportUploadUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportUploadUtil.class);

    @Autowired
    private BorrowOrRecipientListener borrowOrRecipientListener;

    //上传excel
    public static Map<String, List<ExcelImportAssertReq>> excelUpload(MultipartFile multipartFile, SuzakuImportAssertsMapper importAssertsMapper, SuzakuSkuMapper skuMapper) throws IOException {      //listner数据

        Map<String, List<ExcelImportAssertReq>> maps = new HashMap<>();
        ExcelImportListener listener = new ExcelImportListener(importAssertsMapper, skuMapper);
        EasyExcel.read(multipartFile.getInputStream(), ExcelImportAssertReq.class, listener).sheet().doRead();
        List<ExcelImportAssertReq> assets = listener.getList();

        maps.put("all", assets);
        maps.put("success", assets.stream().filter(asserts -> StringUtils.isBlank(asserts.getSuccessFlag())).collect(Collectors.toList()));
        maps.put("fail", assets.stream().filter(asserts -> StringUtils.isNotBlank(asserts.getSuccessFlag())).collect(Collectors.toList()));

        return maps;
    }

    /**
     * 领借单上传操作
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public Map<String, List<BorrowRecipientExcelReq>> excelUpload2(MultipartFile multipartFile) {      //listner数据

        Map<String, List<BorrowRecipientExcelReq>> maps = Maps.newHashMap();
        //置空集合
        borrowOrRecipientListener.init();

        try {
            EasyExcel.read(multipartFile.getInputStream(), BorrowRecipientExcelReq.class, borrowOrRecipientListener).sheet().doRead();
            List<BorrowRecipientExcelReq> listFail = borrowOrRecipientListener.getListFail();
            List<BorrowRecipientExcelReq> listTrue = borrowOrRecipientListener.getListTrue();

             maps.put("fail", listFail);
             maps.put("true", listTrue);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return maps;
    }

}
