package com.ruoyi.SysControl.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.SysControl.domain.SensorData;
import com.ruoyi.SysControl.service.ISensorDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


/**
 * 传感器数据Controller
 * 
 * @author zhu
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/SysControl/data")
public class SensorDataController extends BaseController {
    @Autowired
    private ISensorDataService sensorDataService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(SensorData sensorData) {
        startPage();
        List<SensorData> list = sensorDataService.selectSensorDataList(sensorData);
        return getDataTable(list);
    }

    /**
     * 导出传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:export')")
    @Log(title = "传感器数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SensorData sensorData) {
        List<SensorData> list = sensorDataService.selectSensorDataList(sensorData);
        ExcelUtil<SensorData> util = new ExcelUtil<SensorData>(SensorData.class);
        util.exportExcel(response, list, "传感器数据数据");
    }

    /**
     * 获取传感器数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sensorDataService.selectSensorDataById(id));
    }

    /**
     * 新增传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:add')")
    @Log(title = "传感器数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SensorData sensorData) {
        return toAjax(sensorDataService.insertSensorData(sensorData));
    }

    /**
     * 修改传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:edit')")
    @Log(title = "传感器数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SensorData sensorData) {
        return toAjax(sensorDataService.updateSensorData(sensorData));
    }

    /**
     * 删除传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:remove')")
    @Log(title = "传感器数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sensorDataService.deleteSensorDataByIds(ids));
    }

    /**
     * 获取传感器数据
     *
     * @param json
     * @return
     */
    @PostMapping("/test/get")
    public AjaxResult getData(@RequestBody JSONObject json) {
        List<SensorData> list = new ArrayList<>();
        String sensorType = json.getString("sensorType");
        if (sensorType.equals("servo")) {
            SensorData servoData = new SensorData();
            servoData.setDeviceId("HX711");
            servoData.setSensorType(sensorType);
            BigDecimal data = json.getBigDecimal("data");
            // 若大于50则标记异常
            if (data.compareTo(new BigDecimal(50)) > 0) {
                servoData.setSensorStatus("异常");
            } else {
                servoData.setSensorStatus("正常");
            }
            servoData.setDataValue(data);
            list.add(servoData);
            redisCache.setCacheObject("servo", servoData.getDataValue(), 5, TimeUnit.MINUTES);
        } else {
            SensorData wdData = new SensorData();
            SensorData sdData = new SensorData();
            BigDecimal temperature = json.getBigDecimal("temperature");
            BigDecimal humidity = json.getBigDecimal("humidity");
            wdData.setDeviceId("DHT11");
            sdData.setDeviceId("DHT11");
            wdData.setSensorType("temperature");
            sdData.setSensorType("humidity");
            wdData.setDataValue(temperature);
            sdData.setDataValue(humidity);
            list.add(wdData);
            list.add(sdData);
            if (temperature.compareTo(new BigDecimal(100)) > 0) {
                wdData.setSensorStatus("异常");
            } else {
                wdData.setSensorStatus("正常");
            }
            if (humidity.compareTo(new BigDecimal(100)) > 0) {
                sdData.setSensorStatus("异常");
            } else {
                sdData.setSensorStatus("正常");
            }
            redisCache.setCacheObject("temperature", wdData, 5, TimeUnit.MINUTES);
            redisCache.setCacheObject("humidity", sdData, 5, TimeUnit.MINUTES);
        }
        // 判断是否为整点数据或异常数据
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();
        if (minute == 0) { // 整点数据
            sensorDataService.saveSensorDataToDatabase(list);
        } else {
            list = sensorDataService.isSensorDataException(list);
            sensorDataService.saveSensorDataToDatabase(list);
            return AjaxResult.warn("数据异常!请及时处理!");
        }

        return AjaxResult.success();
    }

    @GetMapping("/test/list")
    public AjaxResult chartList() {
        List<SensorData> sensorDataList = sensorDataService.getListFromDatabase(); // 从数据库中获取数据

        List<Map<String, Object>> chartData = new ArrayList<>();

        for (SensorData data : sensorDataList) {
            Map<String, Object> chartItem = new HashMap<>();
            chartItem.put("time", data.getDataTime()); // 假设数据中有时间字段
            chartItem.put(data.getSensorType(), data.getDataValue());
            chartData.add(chartItem);
        }

        return AjaxResult.success(chartData);
    }

    @GetMapping("/test/switchLED/{status}")
    public AjaxResult switchLED(@PathVariable("status") int status) {
        sensorDataService.switchLED(status);
        return AjaxResult.success("test");
    }

    @GetMapping("/test/servo")
    public AjaxResult servo(@RequestParam(name = "num") Integer num) {
        redisCache.setCacheObject("servo",num.toString());
        return AjaxResult.success(num);
    }

    @GetMapping("/test/duoji/{num}")
    public AjaxResult duoji(@PathVariable(name = "num") Integer num) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://192.168.137.61/rotate?angle=" + num);
            System.out.println("num=" + num);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
//                System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//                String responseBody = EntityUtils.toString(response.getEntity());
//                System.out.println("Response Body : " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

    @GetMapping("/test/duoji")
    public AjaxResult resetDuoji() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://192.168.137.61/reset");
            httpClient.execute(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }

}
