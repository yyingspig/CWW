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
import com.ruoyi.SysControl.domain.PetBoarding;
import com.ruoyi.SysControl.service.IPetBoardingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 入住信息Controller
 * 
 * @author zhu
 * @date 2024-04-10
 */
@RestController
@RequestMapping("/SysControl/boarding")
public class PetBoardingController extends BaseController
{
    @Autowired
    private IPetBoardingService petBoardingService;

    /**
     * 查询入住信息列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:list')")
    @GetMapping("/list")
    public TableDataInfo list(PetBoarding petBoarding)
    {
        startPage();
        List<PetBoarding> list = petBoardingService.selectPetBoardingList(petBoarding);
        return getDataTable(list);
    }

    /**
     * 导出入住信息列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:export')")
    @Log(title = "入住信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PetBoarding petBoarding)
    {
        List<PetBoarding> list = petBoardingService.selectPetBoardingList(petBoarding);
        ExcelUtil<PetBoarding> util = new ExcelUtil<PetBoarding>(PetBoarding.class);
        util.exportExcel(response, list, "入住信息数据");
    }

    /**
     * 获取入住信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:query')")
    @GetMapping(value = "/{boardingId}")
    public AjaxResult getInfo(@PathVariable("boardingId") Long boardingId)
    {
        return success(petBoardingService.selectPetBoardingByBoardingId(boardingId));
    }

    /**
     * 新增入住信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:add')")
    @Log(title = "入住信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PetBoarding petBoarding)
    {
        return toAjax(petBoardingService.insertPetBoarding(petBoarding));
    }

    /**
     * 修改入住信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:edit')")
    @Log(title = "入住信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PetBoarding petBoarding)
    {
        return toAjax(petBoardingService.updatePetBoarding(petBoarding));
    }

    /**
     * 删除入住信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:boarding:remove')")
    @Log(title = "入住信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{boardingIds}")
    public AjaxResult remove(@PathVariable Long[] boardingIds)
    {
        return toAjax(petBoardingService.deletePetBoardingByBoardingIds(boardingIds));
    }
}
