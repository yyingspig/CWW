package com.ruoyi.SysControl.mapper;

import java.util.List;
import com.ruoyi.SysControl.domain.PetBoarding;

/**
 * 入住信息Mapper接口
 * 
 * @author zhu
 * @date 2024-04-10
 */
public interface PetBoardingMapper 
{
    /**
     * 查询入住信息
     * 
     * @param boardingId 入住信息主键
     * @return 入住信息
     */
    public PetBoarding selectPetBoardingByBoardingId(Long boardingId);

    /**
     * 查询入住信息列表
     * 
     * @param petBoarding 入住信息
     * @return 入住信息集合
     */
    public List<PetBoarding> selectPetBoardingList(PetBoarding petBoarding);

    /**
     * 新增入住信息
     * 
     * @param petBoarding 入住信息
     * @return 结果
     */
    public int insertPetBoarding(PetBoarding petBoarding);

    /**
     * 修改入住信息
     * 
     * @param petBoarding 入住信息
     * @return 结果
     */
    public int updatePetBoarding(PetBoarding petBoarding);

    public int updatePetBoardingById(Long boardingId);
    public int updatePetBoardingById2(Long boardingId);

    /**
     * 删除入住信息
     * 
     * @param boardingId 入住信息主键
     * @return 结果
     */
    public int deletePetBoardingByBoardingId(Long boardingId);

    /**
     * 批量删除入住信息
     * 
     * @param boardingIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetBoardingByBoardingIds(Long[] boardingIds);
}
