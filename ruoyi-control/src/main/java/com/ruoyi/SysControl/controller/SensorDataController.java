package com.ruoyi.SysControl.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.SysControl.domain.SensorData;
import com.ruoyi.SysControl.service.ISensorDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 传感器数据Controller
 * 
 * @author ruoyi
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/SysControl/data")
public class SensorDataController extends BaseController
{
    @Autowired
    private ISensorDataService sensorDataService;

    /**
     * 查询传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(SensorData sensorData)
    {
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
    public void export(HttpServletResponse response, SensorData sensorData)
    {
        List<SensorData> list = sensorDataService.selectSensorDataList(sensorData);
        ExcelUtil<SensorData> util = new ExcelUtil<SensorData>(SensorData.class);
        util.exportExcel(response, list, "传感器数据数据");
    }

    /**
     * 获取传感器数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sensorDataService.selectSensorDataById(id));
    }

    /**
     * 新增传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:add')")
    @Log(title = "传感器数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SensorData sensorData)
    {
        return toAjax(sensorDataService.insertSensorData(sensorData));
    }

    /**
     * 修改传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:edit')")
    @Log(title = "传感器数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SensorData sensorData)
    {
        return toAjax(sensorDataService.updateSensorData(sensorData));
    }

    /**
     * 删除传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:remove')")
    @Log(title = "传感器数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sensorDataService.deleteSensorDataByIds(ids));
    }
}
