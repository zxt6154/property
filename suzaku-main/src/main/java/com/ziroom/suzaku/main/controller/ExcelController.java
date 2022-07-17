package com.ziroom.suzaku.main.controller;

import com.alibaba.excel.EasyExcel;
import com.ziroom.suzaku.main.constant.ErrorCode;
import com.ziroom.suzaku.main.entity.excelModel.ExcelImportAssert;
import com.ziroom.suzaku.main.exception.SuzakuException;
import com.ziroom.suzaku.main.model.JsonResult;
import com.ziroom.suzaku.main.model.dto.req.BorrowRecipientExcelReq;
import com.ziroom.suzaku.main.model.dto.resp.BorrowRecipientExcelResp;
import com.ziroom.suzaku.main.model.dto.resp.ExcelImportDetail;
import com.ziroom.suzaku.main.service.impl.ExcelDataServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


/**
 * @author libingsi
 * @date 2021/10/12 16:04
 */
@Api(value = "Excel相关接口", tags = "Excel相关接口")
@RestController
@RequestMapping("/api/v1/excel")
@Slf4j
public class ExcelController {

    @Resource
    private ExcelDataServiceImpl excelDataService;


    @GetMapping("/warehouseDownload")
    @ApiOperation(value = "入库模板下载", notes = "入库模板下载")
    public JsonResult excelDownload(HttpServletResponse response) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/入库模板下载.xls");
            InputStream inputStream = classPathResource.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("入库模板下载.xls", "UTF-8"));
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("入库模板下载异常：" + e.getMessage());
            throw new SuzakuException(ErrorCode.ERROR_002);
        }
        return JsonResult.ok();
    }
    @GetMapping("/failDataDownload")
    @ApiOperation(value = "入库数据下载", notes = "入库数据下载")
    public void failDataDownload(HttpServletResponse response){

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            String fileName = URLEncoder.encode("入库失败数据记录", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), ExcelImportAssert.class).sheet("入库失败数据记录").doWrite(excelDataService.getFaildList());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @PostMapping("/upload")
    @ApiOperation(value = "上传明细,返回上传成功、失败、全部数量以及上传成功与sku连接明细和失败数据", notes = "上传明细")
    public JsonResult excelUpload(@RequestParam("filename") MultipartFile file){
        return JsonResult.ok(excelDataService.listImport(file));
    }


    @PostMapping("/upload2")
    @ApiOperation(value = "批量维护使用人模板上传", notes = "上传明细")
    public JsonResult excelUpload2(@RequestParam("filename") MultipartFile file){
        return JsonResult.ok(excelDataService.listBorrowOrRecipient(file));
    }

    @GetMapping("/failDataDownload2")
    @ApiOperation(value = "领借数据下载", notes = "领借数据下载")
    public void failDataDownload2(HttpServletResponse response){

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");

        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        try {
            String fileName = URLEncoder.encode("领借失败数据记录", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            EasyExcel.write(response.getOutputStream(), BorrowRecipientExcelResp.class).sheet("失败数据记录").doWrite(excelDataService.getFaildList2());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @GetMapping("/applyFormDownload")
    @ApiOperation(value = "批量维护使用人模板下载", notes = "批量维护使用人模板下载")
    public JsonResult applyFormDownload(HttpServletResponse response) {
        try {
            ClassPathResource classPathResource = new ClassPathResource("templates/批量维护使用人.xls");
            InputStream inputStream = classPathResource.getInputStream();
            Workbook workbook = new HSSFWorkbook(inputStream);
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("批量维护使用人.xls", "UTF-8"));
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("批量维护使用人模板下载异常：" + e.getMessage());
            throw new SuzakuException(ErrorCode.ERROR_002);
        }
        return JsonResult.ok();
    }

}
