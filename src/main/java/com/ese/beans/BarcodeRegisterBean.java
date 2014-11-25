package com.ese.beans;



import com.ese.model.db.BarcodeRegisterModel;
import com.ese.model.db.MSItemModel;
import com.ese.model.view.BarcodeRegisterView;
import com.ese.service.BarcodeRegisterService;
import com.ese.utils.FacesUtil;
import com.ese.utils.MessageDialog;
import com.ese.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "barcodeRegisterBean")
public class BarcodeRegisterBean extends Bean{
    @ManagedProperty("#{barcodeRegisterService}") private BarcodeRegisterService barcodeRegisterService;

    private final String DIALOG_NAME = "msgBoxSystemMessageDlg";

    private BarcodeRegisterView barcodeRegisterView;
    private List<MSItemModel> msItemModelList;
    private List<BarcodeRegisterModel> barcodeRegisterModelList;
    private BarcodeRegisterModel barcodeRegisterModel;
    private String selectType;
    private String productSearch;
    private boolean flagBtnSelect;
    private boolean flagBtnDelete;
    private boolean flagBtnSave;
    private boolean flagBtnPrint;
    private boolean flagBtnEdit;
    private String messageHeader;
    private String message;

    private boolean flagItem;
    private boolean flagQty;
    private boolean flagStartBarcode;

    @PostConstruct
    private void init(){
        barcodeRegisterView = new BarcodeRegisterView();
        msItemModelList = Collections.EMPTY_LIST;
        barcodeRegisterModelList = Collections.EMPTY_LIST;
        initBtn();
        initField();
        onLoadDataTable();
    }

    private void initField(){
        flagItem = false;
        flagQty = false;
        flagStartBarcode = false;
    }

    private void initBtn(){
        flagBtnSelect = true;
        flagBtnDelete = true;
        flagBtnSave = true;
        flagBtnEdit = true;
        flagBtnPrint = true;
    }

    private void onLoadDataTable(){
        barcodeRegisterModelList = barcodeRegisterService.getByIsValid();
        log.debug("-- onLoadDataTable() returned size = {}", barcodeRegisterModelList.size());
    }

    public void onClickButtonNew(){
        log.debug("-- onClickButtonNew()");
        barcodeRegisterView = new BarcodeRegisterView();
        flagBtnDelete = true;
        flagBtnSave = false;
        flagBtnEdit = true;
    }

    public void calculator(){
        log.debug("-- calculator()");
        final int qty = barcodeRegisterView.getQty();
        final int start = Utils.parseInt(barcodeRegisterView.getStartBarcode(), 0);
        final int finish = (qty + start) - 1;
        final String result = finish > 999999999 ? "999999999" : String.format("%09d", finish);
        barcodeRegisterView.setFinishBarcode(result);
        barcodeRegisterView.setFinishBarcodeText("T" + result);
        barcodeRegisterView.setStartBarcodeText("T" + String.format("%09d", start));
    }

    private boolean mandate(){
        System.out.println("mandate()");

        if(!mandateQty() && !mandateItem() && !mandateStartBarcode()){
            return true;
        } else {
            message = "";
            if(mandateQty()){
                this.message += "Qtr should be greater than 0.\n";
            }
            if(mandateItem()){
                this.message += "Item should not be empty.\n";
            }
            if(mandateStartBarcode()){
                this.message +=  "StartBarcode should be 9 characters.";
            }
            return false;
        }
    }

    private boolean mandateQty(){
        flagQty = (Utils.isZero(Utils.parseString(barcodeRegisterView.getQty(), "")) || barcodeRegisterView.getQty() < 1 )? true : false ;
        return flagQty;
    }

    private boolean mandateStartBarcode(){
        final int FIX_LENGTH = 9;
        flagStartBarcode = barcodeRegisterView.getStartBarcode().length() != FIX_LENGTH ? true : false ;
        return flagStartBarcode;
    }

    private boolean mandateItem(){
        flagItem = Utils.isZero(barcodeRegisterView.getMsItemModel().getId()) ? true : false ;
        return flagItem;
    }

    public void onInitSearch(){
        log.debug("-- onInitSearch()");
        selectType = "3";
        productSearch = "";
    }

    public void onSubmitSearch(){
        log.debug("-- onSubmitSearch()");
        if(!Utils.isZero(productSearch.length())){
            msItemModelList = barcodeRegisterService.findByCondition(selectType, productSearch);
        }
    }

    public void onClickTableDialog(){
        log.debug("-- onClickTable()");
        flagBtnSelect = false;
    }

    public void onClickTable(){
        log.debug("-- onClickTable() {}", barcodeRegisterModel);
        flagBtnSave = true;
        flagBtnDelete = false;
        flagBtnEdit = false;
        flagBtnPrint = false;
        barcodeRegisterView = barcodeRegisterService.convertToView(barcodeRegisterModel);
    }

    public void onClickSelectOnDialog(){
        log.debug("-- onClickSelectOnDialog()");
        flagBtnSelect = true;
    }

    public void onDelete(){
        log.debug("-- onDelete()");
        try {
            barcodeRegisterService.delete(barcodeRegisterModel);
            showDialog(MessageDialog.DELETE.getMessageHeader(), MessageDialog.DELETE.getMessage());
            init();
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }
    }

    public void onSave(){
        log.debug("-- onSave()");
        try {
            if(mandate()){
                barcodeRegisterService.save(barcodeRegisterView);
                showDialog(MessageDialog.SAVE.getMessageHeader(), MessageDialog.SAVE.getMessage());
                init();
            } else {
                showDialog(MessageDialog.WARNING.getMessageHeader(), message);
            }
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }
    }

    public void onEdit(){
        log.debug("-- onEdit()");
        try {
            if(mandate()){
                barcodeRegisterService.edit(barcodeRegisterView);
                showDialog(MessageDialog.EDIT.getMessageHeader(), MessageDialog.EDIT.getMessage());
                init();
            } else {
                showDialog(MessageDialog.WARNING.getMessageHeader(), message);
            }
        } catch (Exception e) {
            log.error("{}",e);
            showDialogError(e.getMessage());
        }

    }

    private void showDialogError(String message){
        showDialog(MessageDialog.ERROR.getMessageHeader(), message);
        init();
    }

    private void showDialog(String messageHeader, String message){
        this.messageHeader = messageHeader;
        this.message = message;
        FacesUtil.showDialog(DIALOG_NAME);
    }

    public void onPrint(){
        barcodeRegisterService.onPrintBarcode(barcodeRegisterModel.getId());
    }
}
