package com.ruoyi.SysControl.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主人信息对象 owner_info
 * 
 * @author zhu
 * @date 2024-04-10
 */
public class OwnerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主人id */
    private Long ownerId;

    /** 主人姓名 */
    @Excel(name = "主人姓名")
    private String ownerName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phoneNumber;

    /** 宠物数量 */
    @Excel(name = "宠物数量")
    private Long petCount;

    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }
    public void setOwnerName(String ownerName) 
    {
        this.ownerName = ownerName;
    }

    public String getOwnerName() 
    {
        return ownerName;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    public void setPetCount(Long petCount) 
    {
        this.petCount = petCount;
    }

    public Long getPetCount() 
    {
        return petCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ownerId", getOwnerId())
            .append("ownerName", getOwnerName())
            .append("phoneNumber", getPhoneNumber())
            .append("petCount", getPetCount())
            .toString();
    }
}
