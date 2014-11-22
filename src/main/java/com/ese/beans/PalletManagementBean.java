package com.ese.beans;

import com.ese.model.db.ConveyorLineModel;
import com.ese.model.db.WarehouseModel;
import com.ese.model.db.WorkingAreaModel;
import com.ese.model.view.PalletManagementView;
import com.ese.service.PalletService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ManagedBean(name = "palletManegement")
@ViewScoped
public class PalletManagementBean extends Bean implements Serializable {
    @ManagedProperty("#{palletService}") private PalletService palletService;
    private List<WarehouseModel> warehouseModelList;
    private WarehouseModel warehouseMode;
    private List<ConveyorLineModel> conveyorLineModelList;
    private ConveyorLineModel conveyorLineModel;
    private List<PalletManagementView> palletManegamentViewList;
    private PalletManagementView palletMeanegementView;
    private WorkingAreaModel workingAreaModel;
    private List<WorkingAreaModel> workingAreaModelList;
    private int statusOnShow;
    private String textTest;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
        palletMeanegementView = new PalletManagementView();
        warehouseMode = new WarehouseModel();
        conveyorLineModel = new ConveyorLineModel();
        workingAreaModel = new WorkingAreaModel();
        init();
    }

    private void init(){
        log.debug("init().");
        warehouseModelList = warehouseService.getWarehouseList();
        workingAreaModelList = workingAreaService.getWorkingAreaList();
//        conveyorLineModelList = conveyorLineService.getConveyorLineList();
        statusOnShow = 0;
//        onloadPallet();
    }

    private void onLoadPallet(){
        log.debug("onLoadPallet(). ");
        palletManegamentViewList = palletService.findPalletJoinLocation();
    }

    public void onFind(){
        log.debug("changeOn : {}", statusOnShow);
        palletManegamentViewList = palletService.findByChang(statusOnShow, warehouseMode.getId(), workingAreaModel.getId());
    }


    public void test(){
        log.debug("palletManagementView : {}", palletMeanegementView.toString());
    }
}
