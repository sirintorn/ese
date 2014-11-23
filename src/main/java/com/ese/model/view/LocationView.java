package com.ese.model.view;

import com.ese.model.db.MSWarehouseModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class LocationView {
    private int id;
    private String locationBarcode;
    private String locationName;
    private MSWarehouseModel warehouseModel;
    private int capacity;
    private String remark;
    private int qty;
    private int status;
    private int createBy;
    private Date createDate;
    private int updateBy;
    private Date updateDate;
    private int isvalid;
    private int version;
    private int reservedQty;
    private int isMix;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("locationBarcode", locationBarcode)
                .append("locationName", locationName)
                .append("warehouseModel", warehouseModel)
                .append("capacity", capacity)
                .append("remark", remark)
                .append("qty", qty)
                .append("status", status)
                .append("createBy", createBy)
                .append("createDate", createDate)
                .append("updateBy", updateBy)
                .append("updateDate", updateDate)
                .append("isvalid", isvalid)
                .append("version", version)
                .append("reservedQty", reservedQty)
                .append("isMix", isMix)
                .toString();
    }
}
