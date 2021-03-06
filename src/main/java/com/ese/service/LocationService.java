package com.ese.service;

import com.ese.model.dao.LocationDAO;
import com.ese.model.dao.WarehouseDAO;
import com.ese.model.db.MSLocationModel;
import com.ese.model.db.MSWarehouseModel;
import com.ese.model.view.LocationView;
import com.ese.transform.LocationTransform;
import com.ese.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component
@Transactional
public class LocationService extends Service{
    private static final long serialVersionUID = 4112578634029784840L;
    @Resource private LocationDAO locationDAO;
    @Resource private LocationTransform locationTransform;
    @Resource private WarehouseDAO warehouseDAO;


    public List<MSLocationModel> getLocationByWarehouse(int warehouseId){
        log.debug("getLocationList()");
        List<MSLocationModel> msLocationModelList = Utils.getEmptyList();
        try{
            msLocationModelList = locationDAO.findByWarehouseId(warehouseId);
        } catch (Exception e){
            log.debug("Exception getLocationList.", e);
        }
        return msLocationModelList;
    }

    public List<MSLocationModel> getLocationAll(){
        log.debug("getLocationAll()");
        List<MSLocationModel> msLocationModels = Utils.getEmptyList();
        try {
            msLocationModels = locationDAO.getLocationOrderByUpdateDate();
        } catch (Exception e) {
            log.debug("Exception error locational : ", e);
        }

        return msLocationModels;
    }

    public LocationView clickToWarehouseView(MSLocationModel msLocationModel){
        LocationView locationView = new LocationView();

        if (!Utils.isNull(msLocationModel)){
            locationView = locationTransform.transformToView(msLocationModel);
        }

        return locationView;
    }

    public MSWarehouseModel checkWarehouse(int warehouseId){
        log.debug("checkWarehouse : {}", warehouseId);
        return warehouseDAO.findCheckDelete(warehouseId);
    }

    public void onSaveOrUpdateLocationToDB(LocationView locationView){
        log.debug("onSaveToNew().");

        if (!Utils.isNull(locationView)){
            try {
                if (Utils.isZero(locationView.getId())){
                    locationDAO.persist(locationTransform.transformToModel(locationView));
                } else if (!Utils.isZero(locationView.getId())){
                    locationDAO.update(locationTransform.transformToModel(locationView));
                }
            } catch (Exception e) {
                log.debug("Exception persist : ", e);
            }
        }
    }

    public void delete(MSLocationModel model){
        log.debug("-- delete(id : {})", model.getId());
        try {
            if (Utils.isNull(checkWarehouse(model.getMsWarehouseModel().getId()))){
                List<MSWarehouseModel> warehouseModel = warehouseDAO.findByIsValidEnable();

                if (Utils.isSafetyList(warehouseModel)){
                    model.setMsWarehouseModel(warehouseModel.get(0));
                }
            }
            locationDAO.deleteByUpdate(model);
        } catch (Exception e) {
            log.error("{}",e);
        }
    }

    public List<MSLocationModel> searchOrderByCodeOrName(String key){
        log.debug("searchOrderByCodeOrName(). {}", key);
        List<MSLocationModel> msLocationModels = Utils.getEmptyList();
        if (!Utils.isNull(key) && !Utils.isZero(key.length())){
            msLocationModels = locationDAO.findOrderByLocationCodeOrLocationName(key);
        } else {
            try {
                msLocationModels = locationDAO.getLocationOrderByUpdateDate();
            } catch (Exception e) {
                log.debug("Exception error searchOrderByCodeOrName : ", e);
            }
        }

        return msLocationModels;
    }

    public boolean isDuplicate(int warehouseId, String locationCode, int id){
        List<MSLocationModel> msLocationModels = locationDAO.findByWarehouseIdAndLocationCode(warehouseId, locationCode);
        return !Utils.isSafetyList(msLocationModels) || msLocationModels.size() == 1 && !Utils.isZero(id)?true:false;
    }
}
