package com.ruoyi.SysControl.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 宠物信息管理对象 pet_info
 * 
 * @author zhu
 * @date 2024-04-10
 */
public class PetInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 宠物id */
    private Long petId;

    /** 宠物名字 */
    @Excel(name = "宠物名字")
    private String petName;

    /** 种类 */
    @Excel(name = "种类")
    private String species;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 体重 */
    @Excel(name = "体重")
    private BigDecimal weight;

    /** 疫苗接种情况 */
    @Excel(name = "疫苗接种情况")
    private Integer vaccinationStatus;

    /** 主人id */
    @Excel(name = "主人id")
    private Long ownerId;

    public void setPetId(Long petId) 
    {
        this.petId = petId;
    }

    public Long getPetId() 
    {
        return petId;
    }
    public void setPetName(String petName) 
    {
        this.petName = petName;
    }

    public String getPetName() 
    {
        return petName;
    }
    public void setSpecies(String species) 
    {
        this.species = species;
    }

    public String getSpecies() 
    {
        return species;
    }
    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setWeight(BigDecimal weight) 
    {
        this.weight = weight;
    }

    public BigDecimal getWeight() 
    {
        return weight;
    }
    public void setVaccinationStatus(Integer vaccinationStatus) 
    {
        this.vaccinationStatus = vaccinationStatus;
    }

    public Integer getVaccinationStatus() 
    {
        return vaccinationStatus;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("petId", getPetId())
            .append("petName", getPetName())
            .append("species", getSpecies())
            .append("age", getAge())
            .append("gender", getGender())
            .append("weight", getWeight())
            .append("vaccinationStatus", getVaccinationStatus())
            .append("ownerId", getOwnerId())
            .toString();
    }
}
