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
import com.ruoyi.SysControl.domain.PetInfo;
import com.ruoyi.SysControl.service.IPetInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 宠物信息管理Controller
 * 
 * @author zhu
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/SysControl/PetInfo")
public class PetInfoController extends BaseController
{
    @Autowired
    private IPetInfoService petInfoService;

    /**
     * 查询宠物信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(PetInfo petInfo)
    {
        startPage();
        List<PetInfo> list = petInfoService.selectPetInfoList(petInfo);
        return getDataTable(list);
    }

    /**
     * 导出宠物信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:export')")
    @Log(title = "宠物信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetInfo petInfo)
    {
        List<PetInfo> list = petInfoService.selectPetInfoList(petInfo);
        ExcelUtil<PetInfo> util = new ExcelUtil<PetInfo>(PetInfo.class);
        util.exportExcel(response, list, "宠物信息管理数据");
    }

    /**
     * 获取宠物信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:query')")
    @GetMapping(value = "/{petId}")
    public AjaxResult getInfo(@PathVariable("petId") Long petId)
    {
        return success(petInfoService.selectPetInfoByPetId(petId));
    }

    /**
     * 新增宠物信息管理
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:add')")
    @Log(title = "宠物信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetInfo petInfo)
    {
        return toAjax(petInfoService.insertPetInfo(petInfo));
    }

    /**
     * 修改宠物信息管理
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:edit')")
    @Log(title = "宠物信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetInfo petInfo)
    {
        return toAjax(petInfoService.updatePetInfo(petInfo));
    }

    /**
     * 删除宠物信息管理
     */
    @PreAuthorize("@ss.hasPermi('SysControl:PetInfo:remove')")
    @Log(title = "宠物信息管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{petIds}")
    public AjaxResult remove(@PathVariable Long[] petIds)
    {
        return toAjax(petInfoService.deletePetInfoByPetIds(petIds));
    }
}
