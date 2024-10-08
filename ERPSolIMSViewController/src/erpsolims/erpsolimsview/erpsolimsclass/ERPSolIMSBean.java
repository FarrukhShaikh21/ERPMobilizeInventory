package erpsolims.erpsolimsview.erpsolimsclass;

import erpsolglob.erpsolglobmodel.erpsolglobclasses.ERPSolGlobClassModel;

import erpsolglob.erpsolglobview.erpclass.ERPSolGlobalViewBean;

import java.sql.CallableStatement;

import java.sql.SQLException;
import java.sql.Types;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.AttributeBinding;

import oracle.binding.BindingContainer;
import oracle.adf.model.BindingContext;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCIteratorBindingDef;
import oracle.adf.share.ADFContext;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.server.DBTransaction;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ERPSolIMSBean {
    public ERPSolIMSBean() {
        super();
    }

    String ERPSolStrUserCode;
    String ERPSolStrUserLocationCode;
    String ERPSolStrUserRegionCode;
    String ERPSolStrUserStoreCode;
    String ERPSolScanType="B";
    String ERPSolProductId;
    String ERPSolSTNNO;
    String ERPSolGRNNO;
    RichPopup ERPSolImeiPopup;
//    RichInputText ERPSolImeiBoxText;
    String ERPSolImeiString;
    String ERPSolReportName;
    Integer ERPSolStnLineSeq;
    public void doSetERPSolIMSSessionGlobals() {
        System.out.println("glob user code"+getERPSolStrUserCode());
        if (getERPSolStrUserCode().length()==0) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users Defaults are not defined properly. Please Check"));
           throw new JboException("Users Defaults are not defined properly. Please Check");
       }
        try {
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_CODE", getERPSolStrUserCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_REGION", getERPSolStrUserCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_LOCATION",getERPSolStrUserLocationCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_STORE", getERPSolStrUserStoreCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_COMPANY_CODE", "M");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users Defaults are not defined properly. Please Check"));
        }
    }


    public void setERPSolStrUserCode(String ERPSolStrUserCode) {
        this.ERPSolStrUserCode = ERPSolStrUserCode;
    }

    public String getERPSolStrUserCode() {
        return ERPSolStrUserCode;
    }

    public void setERPSolStrUserLocationCode(String ERPSolStrUserLocationCode) {
        this.ERPSolStrUserLocationCode = ERPSolStrUserLocationCode;
    }

    public String getERPSolStrUserLocationCode() {
        return ERPSolStrUserLocationCode;
    }

    public void setERPSolStrUserRegionCode(String ERPSolStrUserRegionCode) {
        this.ERPSolStrUserRegionCode = ERPSolStrUserRegionCode;
    }

    public String getERPSolStrUserRegionCode() {
        return ERPSolStrUserRegionCode;
    }

    public void setERPSolStrUserStoreCode(String ERPSolStrUserStoreCode) {
        this.ERPSolStrUserStoreCode = ERPSolStrUserStoreCode;
    }

    public String getERPSolStrUserStoreCode() {
        return ERPSolStrUserStoreCode;
    }

    public List<SelectItem> doERPSolGetAutoSuggestedStoreValues(String pStringValues) {
//public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("c");
//        String ERPLocid=""+ERPSolib.getCurrentRow().getAttribute("Locationid");
        System.out.println("d");
        System.out.println(ERPLocid.getInputValue());//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllStoresAutoSuggestRO",
                                                            " EXISTS(SELECT '' FROM SYS_USER_STORE SUS WHERE SUS.STOREID=AllStores.STOREID AND SUS.USERID='"+ERPSolGlobalViewBean.doGetUserCode()+"') AND LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(STOREID,STORE_NAME))", "StoreName", "Storeid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }
    
    public List<SelectItem> doERPSolGetAutoSuggestedReceiveStoreValues(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllStoresReceivingAutoSuggestRO",
                                                            " UPPER(CONCAT(STOREID,STORE_NAME))", "StoreName", "Storeid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }
    public List<SelectItem> doERPSolGetAutoSuggestedGRNSourceDocValues(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("InReceivedItemsCRUDIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        ViewObject vo=ERPSolAM.findViewObject("VWGRNSourceDocNoAutoSuggestRO");
        AttributeBinding ERPStoreid         =(AttributeBinding)ERPSolbc.getControlBinding("Storeid");
        AttributeBinding ERPReceivingDate   =(AttributeBinding)ERPSolbc.getControlBinding("ReceivingDate");
        vo.setNamedWhereClauseParam("P_ADF_DATE", ERPReceivingDate.getInputValue());
        vo.setNamedWhereClauseParam("P_ADF_DOC_TYPE", "STA");
        vo.setNamedWhereClauseParam("P_ADF_STOREID", ERPStoreid.getInputValue());
        vo.executeQuery();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWGRNSourceDocNoAutoSuggestRO",
                                                            " UPPER(CONCAT(Stnno,Sname))", "Stnno", "Sname", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }
    
    
    
    public List<SelectItem> doERPSolGetAutoSuggestedCustomerValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("c");
        
        System.out.println("d");
        System.out.println(ERPLocid);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllCustomersAutoSuggestRO",
                                                            "LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(CUSTOMERID,CUSTOMER_NAME))", "CustomerName", "Customerid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }   

    public List<SelectItem> doERPSolGetAutoSuggestedSalesPersonValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPCustomerId =(AttributeBinding)ERPSolbc.getControlBinding("Customerid");
        System.out.println("c");
//        String ERPCustomerId=""+ERPSolib.getCurrentRow().getAttribute("Customerid");
        System.out.println("d");
        System.out.println(ERPCustomerId);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "SoSalesPersonsAutoSuggestRO",
                                                            "SALESPERSONID IN (SELECT ASP.SALESPERSONID from ALL_CUSTOMER_SALESPERSON ASP WHERE ASP.CUSTOMERID='"+ERPCustomerId.getInputValue()+"') AND UPPER(CONCAT(Salespersonid,name))", "Name", "Salespersonid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }   
   
    public List<SelectItem> doERPSolGetAutoSuggestedSupplierValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
//        Attribute ERPSolib =(Attribute)ERPSolbc.get("SoSalesOrderViewCRUDIterator");
        System.out.println("c");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("d");
        System.out.println(ERPLocid.getInputValue());//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "PuSuppliersAutoSuggestRO",
                                                            "LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(SUPPLIERID,SUPP_NAME))", "SuppName", "Supplierid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }   
     
    
    public List<SelectItem> doERPSolGetAutoSuggestedModelValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "InItemsAutoSuggestRO"," UPPER(CONCAT(Productid,Model_No))", "Productid", "ModelNo", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }   
    public void doERPSolGRNDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSuperviseGRN");
            binding.execute();
        }
    }


    public void doERPSolSTNDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSuperviseSTN");
            binding.execute();
        }
    }
    
    public void doERPSolShipmentDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSuperviseShipment");
            binding.execute();
        }
    }

    public void setERPSolImeiPopup(RichPopup ERPSolImeiPopup) {
        this.ERPSolImeiPopup = ERPSolImeiPopup;
    }

    public RichPopup getERPSolImeiPopup() {
        return ERPSolImeiPopup;
    }

    public String DoShowERPSolImeiPopup() {
        RichPopup.PopupHints ERPSolHints=new RichPopup.PopupHints();
        getERPSolImeiPopup().show(ERPSolHints);
        return null;
    }
    public void erpSolSTNIMEI(ValueChangeEvent erpvce) {
        if (erpvce.getNewValue()==null) {
            return ;
        }
        doInserterpSolSTNImeiBox(erpvce.getNewValue().toString(),"I");
//        AdfFacesContext.getCurrentInstance().addPartialTarget(getERPSolImeiBoxText());
        System.out.println("5435");    
        
    }
    
    public void erpSolGRNIMEI(ValueChangeEvent erpvce) {
        if (erpvce.getNewValue()==null) {
            return ;
        }
        doInserterpSolGRNImeiBox(erpvce.getNewValue().toString(),"I");
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(getERPSolImeiBoxText());
        System.out.println("5435");    
        
    }

    public void erpSolSTNBOX(ValueChangeEvent erpvce) {
        if (erpvce.getNewValue()==null) {
            return ;
        }
        doInserterpSolSTNImeiBox(erpvce.getNewValue().toString(),"B");
//        AdfFacesContext.getCurrentInstance().addPartialTarget(getERPSolImeiBoxText());
        System.out.println("5435");    
        
    }
 
    public void erpSolGRNBOX(ValueChangeEvent erpvce) {
        if (erpvce.getNewValue()==null) {
            return ;
        }
        doInserterpSolGRNImeiBox(erpvce.getNewValue().toString(),"B");
    //        AdfFacesContext.getCurrentInstance().addPartialTarget(getERPSolImeiBoxText());
        System.out.println("grnimei");    
        
    }
    
    public void doInserterpSolSTNImeiBox(String pImeiBox, String pValueType) {
        if (pImeiBox==null) {
            return ;
        }
        System.out.println("1so");
        DCBindingContainer bc = (DCBindingContainer) ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("2so");
        DCDataControl dc = bc.getDataControl();
        
        System.out.println("3");
        String ERPSolPlsql="begin ?:=PKG_GRN.FUNC_STA_IMEI_BOX_VALIDATION('"+getERPSolSTNNO()+"','"+pImeiBox+"','"+pValueType+"','"+getERPSolProductId()+"'); end;";
        System.out.println(ERPSolPlsql +"ERPSolPlsql");
        System.out.println("4");
        DBTransaction erpsoldbt=(DBTransaction)dc.getApplicationModule().getTransaction();
        System.out.println("5");
        CallableStatement cs = erpsoldbt.createCallableStatement(ERPSolPlsql, DBTransaction.DEFAULT);
        try {
                     System.out.println("6");
                     cs.registerOutParameter(1, Types.VARCHAR);
                     System.out.println("7");
                     cs.executeUpdate();
                     System.out.println("8");
                     ERPSolPlsql=cs.getString(1);
                     System.out.println("9");
                     if (ERPSolPlsql.equals("ERPSOLSUCCESS"))
                     {  
                         
                         erpsoldbt.commit();
                     dc.getApplicationModule().findViewObject("InItemTransferNoteImeiByStnseqlineCRUD").executeQuery();
//                         dc.getApplicationModule().findViewObject("SoSalesOrderViewCRUD").getCurrentRow().setAttribute("txtIMEIAndBox", null);
                     }
                     else {
                         FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(ERPSolPlsql));
                //                throw new JboException(ERPSolPlsql);
                     }
                 } catch (SQLException e) {
                e.printStackTrace();
                     
                 }
                 finally{
                    try {
                        cs.close();
                    } catch (SQLException e) {
                    }
                }
        
    }

    public void doInserterpSolGRNImeiBox(String pImeiBox, String pValueType) {
        if (pImeiBox==null) {
            return ;
        }
        System.out.println("1so");
        DCBindingContainer bc = (DCBindingContainer) ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("2so");
        DCDataControl dc = bc.getDataControl();
        
        System.out.println("3");
        String ERPSolPlsql="begin ?:=PKG_GRN.FUNC_GRN_IMEI_BOX_VALIDATION('"+getERPSolGRNNO()+"','"+pImeiBox+"','"+pValueType+"','"+getERPSolProductId()+"'); end;";
        System.out.println(ERPSolPlsql +"ERPSolPlsql");
        System.out.println("4");
        DBTransaction erpsoldbt=(DBTransaction)dc.getApplicationModule().getTransaction();
        System.out.println("5");
        CallableStatement cs = erpsoldbt.createCallableStatement(ERPSolPlsql, DBTransaction.DEFAULT);
        try {
                     System.out.println("6");
                     cs.registerOutParameter(1, Types.VARCHAR);
                     System.out.println("7");
                     cs.executeUpdate();
                     System.out.println("8");
                     ERPSolPlsql=cs.getString(1);
                     System.out.println("9");
                     if (ERPSolPlsql.equals("ERPSOLSUCCESS"))
                     {  
                         
                         erpsoldbt.commit();
                     dc.getApplicationModule().findViewObject("InReceivedItemsSnoDetCRUD").executeQuery();
    //                         dc.getApplicationModule().findViewObject("SoSalesOrderViewCRUD").getCurrentRow().setAttribute("txtIMEIAndBox", null);
                     }
                     else {
                         FacesContext.getCurrentInstance().addMessage(null , new FacesMessage(ERPSolPlsql));
                //                throw new JboException(ERPSolPlsql);
                     }
                 } catch (SQLException e) {
                e.printStackTrace();
                     
                 }
                 finally{
                    try {
                        cs.close();
                    } catch (SQLException e) {
                    }
                }
        
    }
    public void doERPSolScanRebateImei2(ValueChangeEvent erpvcevt) {
        System.out.println(erpvcevt.getNewValue()+ " get new value");
        System.out.println("one");
    }

    public void setERPSolScanType(String ERPSolScanType) {
        this.ERPSolScanType = ERPSolScanType;
    }

    public String getERPSolScanType() {
        return ERPSolScanType;
    }

    public void setERPSolProductId(String ERPSolProductId) {
        this.ERPSolProductId = ERPSolProductId;
    }

    public String getERPSolProductId() {
        return ERPSolProductId;
    }

  

    public void setERPSolImeiString(String ERPSolImeiString) {
        this.ERPSolImeiString = ERPSolImeiString;
    }

    public String getERPSolImeiString() {
        return null;
    }

    public String doERPSolExecutIMSReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("SysProgramDetROIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOIMSReport");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOIMSReport", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOIMSReport", "select PATH PATH FROM SYSTEM a where a.PROJECTID='IN' ");
        vo.executeQuery();
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+ERPSolReportName;
        
    
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPCompanyid       =(AttributeBinding)ERPSolbc.getControlBinding("Companyid");
        AttributeBinding ERPRegionid        =(AttributeBinding)ERPSolbc.getControlBinding("Regionid");
        AttributeBinding ERPLocationid      =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        AttributeBinding ERPProductid       =(AttributeBinding)ERPSolbc.getControlBinding("Productid");
        AttributeBinding ERPProductgroup    =(AttributeBinding)ERPSolbc.getControlBinding("Productgroup");
        AttributeBinding ERPStoreid         =(AttributeBinding)ERPSolbc.getControlBinding("Storeid");
    //        AttributeBinding ERPProductgroup    =(AttributeBinding)ERPSolbc.getControlBinding("Productgroup");
    //        AttributeBinding ERPProductid       =(AttributeBinding)ERPSolbc.getControlBinding("Productid");
        AttributeBinding ERPFromDate        =(AttributeBinding)ERPSolbc.getControlBinding("txtFromDate");
        AttributeBinding ERPToDate          =(AttributeBinding)ERPSolbc.getControlBinding("txtToDate");
        String reportParameter="";
        reportParameter="COMPANY="+ (ERPCompanyid.getInputValue()==null?"":ERPCompanyid.getInputValue());
        reportParameter+="&P_REGID="+(ERPRegionid.getInputValue()==null?"":ERPRegionid.getInputValue());
        reportParameter+="&P_LOCID="+(ERPLocationid.getInputValue()==null?"":ERPLocationid.getInputValue());
        reportParameter+="&P_STOREID="+(ERPStoreid.getInputValue()==null?"":ERPStoreid.getInputValue());
    //        reportParameter+="&P_STOREID_ID="+(ERPStoreid.getInputValue()==null?"":ERPStoreid.getInputValue());
        reportParameter+="&P_SIGROUPID="+(ERPProductgroup.getInputValue()==null?"":ERPProductgroup.getInputValue());
        reportParameter+="&P_PRODUCT_ID="+(ERPProductid.getInputValue()==null?"":ERPProductid.getInputValue());
        reportParameter+="&FROM_DATE="+(ERPFromDate.getInputValue()==null?"":doERPSolGetFormatDate(""+ERPFromDate.getInputValue() ) );
        reportParameter+="&TO_DATE="+(ERPToDate.getInputValue()==null?"":doERPSolGetFormatDate(""+ERPToDate.getInputValue())  );
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        System.out.println(ERPSolGlobClassModel.doGetUserHLevel()+"hlevel");
        if (  (!ERPSolGlobClassModel.doGetUserHLevel().equals("A")) && ERPStoreid.getInputValue()==null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please Select Store."));
            return null;
       }
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    public void doErpSolOpenReportTab(String url) {
    ExtendedRenderKitService erks =
    Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);
    StringBuilder strb = new StringBuilder("window.open('" + url + "');");
    erks.addScript(FacesContext.getCurrentInstance(), strb.toString());
    }


    public void setERPSolReportName(String ERPSolReportName) {
        this.ERPSolReportName = ERPSolReportName;
    }

    public String getERPSolReportName() {
        return ERPSolReportName;
    }
    public String doERPSolGetFormatDate(String pDate) {
     
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String fromDate="";
        try {
            Date dt = sdf.parse(pDate);
            sdf=new SimpleDateFormat("dd-MMM-yyyy");
            fromDate=sdf.format(dt).toUpperCase();
            return fromDate;
        }
        catch (NullPointerException npe) {
            System.out.println("null parseexception");
        }
        catch (ParseException e) {
            System.out.println("parseexception");
        }
        /////////////
           
        return null;
    }


    public List<SelectItem> doERPSolGetAutoSuggestedLocationValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllLocationsAutoSuggestRO",
                                                            "UPPER(CONCAT(Locationid,Location_Description))", "LocationDescription", "Locationid", 10,"ERPSolIMSAppModuleDataControl");
        return ResultList;
        
    }

    public void setERPSolSTNNO(String ERPSolSTNNO) {
        this.ERPSolSTNNO = ERPSolSTNNO;
    }

    public String getERPSolSTNNO() {
        return ERPSolSTNNO;
    }


    public void setERPSolStnLineSeq(Integer ERPSolStnLineSeq) {
        this.ERPSolStnLineSeq = ERPSolStnLineSeq;
    }

    public Integer getERPSolStnLineSeq() {
        return ERPSolStnLineSeq;
    }

    public String doERPSolGRNReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("InReceivedItemsCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOGRN");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PATH PATH FROM SYSTEM a where a.PROJECTID='IN' ");
        vo.executeQuery();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding Recvdoctypeid          =(AttributeBinding)ERPSolbc.getControlBinding("Recvdoctypeid");
        AttributeBinding Rnoteno                =(AttributeBinding)ERPSolbc.getControlBinding("Rnoteno");
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+(Recvdoctypeid.getInputValue()!=null && Recvdoctypeid.getInputValue().toString().equals("STA")? "GRN_DOC_STA.RDF":"GRN_DOC_PROD.RDF" );
        
    
        
        String reportParameter="";
        reportParameter="P_GRN_NO="+ (Rnoteno.getInputValue()==null?"":Rnoteno.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }

    public String doERPSolSTAReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("InItemTransferNoteCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOGRN");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PATH PATH FROM SYSTEM a where a.PROJECTID='IN' ");
        vo.executeQuery();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding Rnoteno                =(AttributeBinding)ERPSolbc.getControlBinding("Stnno");
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath = pReportPath + getERPSolReportName();


        String reportParameter="";
        reportParameter="P_GRN_NO="+ (Rnoteno.getInputValue()==null?"":Rnoteno.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    
    public String doERPSolDocumentReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("InDocumentShipmentHeaderCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOGRN");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOGRN", "select PATH PATH FROM SYSTEM a where a.PROJECTID='IN' ");
        vo.executeQuery();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding DocumentCode                =(AttributeBinding)ERPSolbc.getControlBinding("ShipmentHeaderId");
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath = pReportPath + "RPT_DOCUMENT_SHIP.RDF";


        String reportParameter="";
        reportParameter="P_SHIPMENT_NO="+ (DocumentCode.getInputValue()==null?"":DocumentCode.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    public List<SelectItem> doERPSolGetAutoSuggestedInUnsubmitDoc(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("VwInInventoryReportROIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        System.out.println("b");
        String ERPLocid=ERPSolGlobClassModel.doGetUserLocationCode();
        AttributeBinding ERPDocType =(AttributeBinding)ERPSolbc.getControlBinding("txtDoctypeId");
        ViewObject vo=ERPSolAM.findViewObject("VWDocumentIdForInUnsubmitAutoSuggestRO");
        vo.setNamedWhereClauseParam("P_ADF_DOCTYPEID", ERPDocType.getInputValue());
        vo.setNamedWhereClauseParam("P_ADF_LOCATIONID", ERPLocid);
        vo.executeQuery();
        System.out.println("d");
        System.out.println(ERPLocid);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWDocumentIdForInUnsubmitAutoSuggestRO",
                                                            " UPPER(CONCAT(DOCUMENT_ID,STORE_NAME))", "DocumentId", "Description", 10);
        return ResultList;
        
    }

    public List<SelectItem> doERPSolGetAutoSuggestedDocumentShipment(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("InDocumentShipmentHeaderCRUDIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        System.out.println("b");
        String ERPLocid=ERPSolGlobClassModel.doGetUserLocationCode();
        AttributeBinding ERPLocationid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        AttributeBinding ERPStoreid =(AttributeBinding)ERPSolbc.getControlBinding("Storeid");
        ViewObject vo=ERPSolAM.findViewObject("VwIssueItemForDocumentShipmentRO");
        vo.setNamedWhereClauseParam("P_ADF_STOREID", ERPStoreid.getInputValue());
        vo.setNamedWhereClauseParam("P_ADF_LOCATIONID", ERPLocationid.getInputValue());
        vo.executeQuery();
        System.out.println("d");
        System.out.println(ERPLocid);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VwIssueItemForDocumentShipmentRO",
                                                            " UPPER(CONCAT(DOCUMENT_ID,STORE_NAME))", "DocumentId", "Description", 10);
        return ResultList;
        
    }
    public void setERPSolGRNNO(String ERPSolGRNNO) {
        this.ERPSolGRNNO = ERPSolGRNNO;
    }

    public String getERPSolGRNNO() {
        return ERPSolGRNNO;
    }
}

