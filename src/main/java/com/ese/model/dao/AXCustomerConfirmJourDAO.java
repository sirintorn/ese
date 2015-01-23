package com.ese.model.dao;

import com.ese.model.db.AXCustomerConfirmJourModel;
import com.ese.model.view.DataSyncConfirmOrderView;
import com.ese.model.view.report.StiketWorkLoadViewReport;
import com.ese.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AXCustomerConfirmJourDAO extends GenericDAO<AXCustomerConfirmJourModel, String>{

    public List<DataSyncConfirmOrderView> genSQLSyncData(){
        List<DataSyncConfirmOrderView> syncDataList = new ArrayList<DataSyncConfirmOrderView>();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.AccountNum AS CUST_CODE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.Name AS CUST_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.CustGroup AS CUST_GROUP,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.confirmId AS CONFIRM_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.confirmdate AS CONFIRM_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDocNum AS CONFIRM_DOCNUM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId AS SALE_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.purchaseorder AS PURCHASE_ORDER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.deliveryname AS DELIVERY_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.deliveryaddress AS DELIVERY_ADDRESS,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.customerref AS CUST_REF,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.containers AS CONTAINER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.dlvterm AS DLVTERM,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.paymentcondition AS PAY_CONDITION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.remark AS REMARK,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.SalesAdministrator AS SALES_ADMIN,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.modedlv AS MODEDLV,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.quotationid AS QUOTATION_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.quotationcreatedate AS QUOTATION_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_ShippingDateRequested AS SHIPPING_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_EDDDate AS EDD_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_AvailableDate AS AVAILABLE_DATE,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustConfirmJour.DSG_remark AS DSG_REMARK");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".ax_CustConfirmJour");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustTable");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".ax_CustConfirmJour.InvoiceAccount = ").append(getPrefix()).append(".ax_CustTable.AccountNum");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustConfirmTrans");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmId = ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmId");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.SalesId  = ").append(getPrefix()).append(".ax_CustConfirmJour.SalesId");
        sqlBuilder.append(" AND ").append(getPrefix()).append(".ax_CustConfirmTrans.ConfirmDate = ").append(getPrefix()).append(".ax_CustConfirmJour.ConfirmDate");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.status = 1" );

        log.debug(sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("CUST_CODE", StringType.INSTANCE)
                    .addScalar("CUST_NAME", StringType.INSTANCE)
                    .addScalar("CUST_GROUP", StringType.INSTANCE)
                    .addScalar("CONFIRM_ID", StringType.INSTANCE)
                    .addScalar("CONFIRM_DATE", TimestampType.INSTANCE)
                    .addScalar("CONFIRM_DOCNUM", StringType.INSTANCE)
                    .addScalar("SALE_ID", StringType.INSTANCE)
                    .addScalar("PURCHASE_ORDER", StringType.INSTANCE)
                    .addScalar("DELIVERY_NAME", StringType.INSTANCE)
                    .addScalar("DELIVERY_ADDRESS", StringType.INSTANCE)
                    .addScalar("CUST_REF", StringType.INSTANCE)
                    .addScalar("CONTAINER", StringType.INSTANCE)
                    .addScalar("DLVTERM", StringType.INSTANCE)
                    .addScalar("PAY_CONDITION", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE)
                    .addScalar("SALES_ADMIN", StringType.INSTANCE)
                    .addScalar("MODEDLV", StringType.INSTANCE)
                    .addScalar("QUOTATION_ID", StringType.INSTANCE)
                    .addScalar("QUOTATION_DATE", TimestampType.INSTANCE)
                    .addScalar("SHIPPING_DATE", TimestampType.INSTANCE)
                    .addScalar("EDD_DATE", TimestampType.INSTANCE)
                    .addScalar("AVAILABLE_DATE", TimestampType.INSTANCE)
                    .addScalar("DSG_REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                DataSyncConfirmOrderView sync = new DataSyncConfirmOrderView();
                sync.setCustomerCode(Utils.parseString(entity[0], ""));
                sync.setCustomerName(Utils.parseString(entity[1], ""));
                sync.setCustomerGroup(Utils.parseString(entity[2], ""));
                sync.setConfirmId(Utils.parseString(entity[3], ""));
                sync.setConfirmDate(Utils.parseDate(entity[4], null));
                sync.setConfirmDocNo(Utils.parseString(entity[5], ""));
                sync.setSaleId(Utils.parseString(entity[6], ""));
                sync.setPurchaseOrder(Utils.parseString(entity[7], ""));
                sync.setDeliveryName(Utils.parseString(entity[8], ""));
                sync.setDeliveryAddress(Utils.parseString(entity[9], ""));
                sync.setCustomerRef(Utils.parseString(entity[10], ""));
                sync.setContainer(Utils.parseString(entity[11], ""));
                sync.setDlvTerm(Utils.parseString(entity[12], ""));
                sync.setPaymentCondition(Utils.parseString(entity[13], ""));
                sync.setRemark(Utils.parseString(entity[14], ""));
                sync.setSaleAdmin(Utils.parseString(entity[15], ""));
                sync.setModeDlv(Utils.parseString(entity[16], ""));
                sync.setQuotationId(Utils.parseString(entity[17], ""));
                sync.setQuotationDate(Utils.parseDate(entity[18], null));
                sync.setShippingDate(Utils.parseDate(entity[19], null));
                sync.setEddDate(Utils.parseDate(entity[20], null));
                sync.setAvailableDate(Utils.parseDate(entity[21], null));
                sync.setDsgRemark(Utils.parseString(entity[22], ""));
                syncDataList.add(sync);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return syncDataList;
    }

    public void updateStatusRunning(String custCode){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 2 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.invoiceAccount = ").append("'").append(custCode).append("'");

        log.debug("SQL ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void rollbackStatus(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 1 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.status = 2");

        log.debug("SQL Roll Back ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public void updateStatusFinish(String custCode){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" UPDATE ").append(getPrefix()).append(".ax_CustConfirmJour SET ").append(getPrefix()).append(".ax_CustConfirmJour.status = 3 ");
        stringBuilder.append(" WHERE ").append(getPrefix()).append(".ax_CustConfirmJour.invoiceAccount = ").append("'").append(custCode).append("'");

        log.debug("SQL ax_CustConfirmJour : {}", stringBuilder.toString());

        try {
            SQLQuery q = getSession().createSQLQuery(stringBuilder.toString());
            q.executeUpdate();
        } catch (Exception e) {
            log.debug("Exception : ", e);
        }
    }

    public List<StiketWorkLoadViewReport> genStikerWorkLoadReport(int pickingId){
        List<StiketWorkLoadViewReport> viewReports = new ArrayList<StiketWorkLoadViewReport>();

        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.sales_order AS ORDER_ID,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.docno AS DOC_NO,");
        sqlBuilder.append(" ").append(getPrefix()).append(".ax_CustTable.name AS CUSTOMER_NAME,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.ItemId AS ITEM_NUMBER,");
        sqlBuilder.append(" ").append(getPrefix()).append(".item_master.DSGThaiItemDescription AS THAI_ITEM_DESCRIPTION,");
        sqlBuilder.append(" ").append(getPrefix()).append(".reserved_order.picked_qty AS QUANTITY,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order_line.salesunit AS ORDER_UNIT,");
        sqlBuilder.append(" ").append(getPrefix()).append(".picking_order.dsg_remark AS REMARK");
        sqlBuilder.append(" FROM ").append(getPrefix()).append(".picking_order");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".picking_order_line");
        sqlBuilder.append(" ON  ").append(getPrefix()).append(".picking_order.id = ").append(getPrefix()).append(".picking_order_line.picking_order_id");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".item_master");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.ItemId = ").append(getPrefix()).append(".item_master.ItemId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_CustTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.customer_code = ").append(getPrefix()).append(".ax_CustTable.AccountNum");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".ax_SalesTable");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order.sales_admin = ").append(getPrefix()).append(".ax_SalesTable.SalesId");
        sqlBuilder.append(" LEFT JOIN ").append(getPrefix()).append(".reserved_order");
        sqlBuilder.append(" ON ").append(getPrefix()).append(".picking_order_line.id = ").append(getPrefix()).append(".reserved_order.picking_order_line_id");
        sqlBuilder.append(" WHERE ").append(getPrefix()).append(".picking_order.id = " ).append(pickingId);

        log.debug("--SQL {}",sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("ORDER_ID", StringType.INSTANCE)
                    .addScalar("DOC_NO", StringType.INSTANCE)
                    .addScalar("CUSTOMER_NAME", StringType.INSTANCE)
                    .addScalar("ITEM_NUMBER", StringType.INSTANCE)
                    .addScalar("THAI_ITEM_DESCRIPTION", StringType.INSTANCE)
                    .addScalar("QUANTITY", BigDecimalType.INSTANCE)
                    .addScalar("ORDER_UNIT", StringType.INSTANCE)
                    .addScalar("REMARK", StringType.INSTANCE);
            List<Object[]> objects = query.list();
            log.debug("----------- {}", objects.size());

            for (Object[] entity : objects) {
                StiketWorkLoadViewReport report = new StiketWorkLoadViewReport();
//                log.debug("{}",Utils.parseString(entity[0], ""));
//                log.debug("{}",Utils.parseString(entity[1], ""));
//                log.debug("{}",Utils.parseString(entity[2], ""));
//                log.debug("{}", Utils.parseString(entity[3], ""));
//                log.debug("{}", Utils.parseBigDecimal(entity[4], BigDecimal.ZERO));
//                log.debug("{}", Utils.parseString(entity[5], ""));
//                log.debug("{}", Utils.parseString(entity[6], ""));
                report.setSalesId(Utils.parseString(entity[0], ""));
                report.setDocNo(Utils.parseString(entity[1], ""));
                report.setCustomerName(Utils.parseString(entity[2], ""));
                report.setItemId(Utils.parseString(entity[3], ""));
                report.setThaiItemDes(Utils.parseString(entity[4], ""));
                report.setQuantity(Utils.parseBigDecimal(entity[5], BigDecimal.ZERO));
                report.setOrderUnit(Utils.parseString(entity[6], ""));
                report.setRemark(Utils.parseString(entity[7], ""));
                viewReports.add(report);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return viewReports;
    }
}
