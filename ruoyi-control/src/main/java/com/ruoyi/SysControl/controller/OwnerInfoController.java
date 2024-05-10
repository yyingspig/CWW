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
import com.ruoyi.SysControl.domain.OwnerInfo;
import com.ruoyi.SysControl.service.IOwnerInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 主人信息Controller
 * 
 * @author zhu
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/SysControl/OwnerInfo")
public class OwnerInfoController extends BaseController
{
    @Autowired
    private IOwnerInfoService ownerInfoService;

    /**
     * 查询主人信息列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(OwnerInfo ownerInfo)
    {
        startPage();
        List<OwnerInfo> list = ownerInfoService.selectOwnerInfoList(ownerInfo);
        return getDataTable(list);
    }

    /**
     * 导出主人信息列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:export')")
    @Log(title = "主人信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OwnerInfo ownerInfo)
    {
        List<OwnerInfo> list = ownerInfoService.selectOwnerInfoList(ownerInfo);
        ExcelUtil<OwnerInfo> util = new ExcelUtil<OwnerInfo>(OwnerInfo.class);
        util.exportExcel(response, list, "主人信息数据");
    }

    /**
     * 获取主人信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:query')")
    @GetMapping(value = "/{ownerId}")
    public AjaxResult getInfo(@PathVariable("ownerId") Long ownerId)
    {
        return success(ownerInfoService.selectOwnerInfoByOwnerId(ownerId));
    }

    /**
     * 新增主人信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:add')")
    @Log(title = "主人信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OwnerInfo ownerInfo)
    {
        return toAjax(ownerInfoService.insertOwnerInfo(ownerInfo));
    }

    /**
     * 修改主人信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:edit')")
    @Log(title = "主人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OwnerInfo ownerInfo)
    {
        return toAjax(ownerInfoService.updateOwnerInfo(ownerInfo));
    }

    /**
     * 删除主人信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:OwnerInfo:remove')")
    @Log(title = "主人信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ownerIds}")
    public AjaxResult remove(@PathVariable Long[] ownerIds)
    {
        return toAjax(ownerInfoService.deleteOwnerInfoByOwnerIds(ownerIds));
    }
}
