package erpsolims.erpsolimsmodel.erpsolimsvo;

import erpsolims.erpsolimsmodel.erpsolimseo.InDocumentShipmentLinesImpl;

import java.math.BigDecimal;

import oracle.jbo.Row;
import oracle.jbo.RowSet;
import oracle.jbo.domain.Date;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Nov 10 22:38:51 PKT 2023
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class InDocumentShipmentLinesVORowImpl extends ViewRowImpl {
    public static final int ENTITY_INDOCUMENTSHIPMENTLINES = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        ShipmentLinesSno,
        ShipmentHeaderSno,
        DocumentId,
        DocumentType,
        Quantity,
        CreatedBy,
        CreatedDate,
        ModifiedBy,
        ModifiedDate,
        InDocumentShipmentHeaderVO,
        AccVwIssueItemForDocumentShipmentQVO;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }
    public static final int SHIPMENTLINESSNO = AttributesEnum.ShipmentLinesSno.index();
    public static final int SHIPMENTHEADERSNO = AttributesEnum.ShipmentHeaderSno.index();
    public static final int DOCUMENTID = AttributesEnum.DocumentId.index();
    public static final int DOCUMENTTYPE = AttributesEnum.DocumentType.index();
    public static final int QUANTITY = AttributesEnum.Quantity.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATEDDATE = AttributesEnum.CreatedDate.index();
    public static final int MODIFIEDBY = AttributesEnum.ModifiedBy.index();
    public static final int MODIFIEDDATE = AttributesEnum.ModifiedDate.index();
    public static final int INDOCUMENTSHIPMENTHEADERVO = AttributesEnum.InDocumentShipmentHeaderVO.index();
    public static final int ACCVWISSUEITEMFORDOCUMENTSHIPMENTQVO =
        AttributesEnum.AccVwIssueItemForDocumentShipmentQVO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public InDocumentShipmentLinesVORowImpl() {
    }

    /**
     * Gets InDocumentShipmentLines entity object.
     * @return the InDocumentShipmentLines
     */
    public InDocumentShipmentLinesImpl getInDocumentShipmentLines() {
        return (InDocumentShipmentLinesImpl) getEntity(ENTITY_INDOCUMENTSHIPMENTLINES);
    }

    /**
     * Gets the attribute value for SHIPMENT_LINES_SNO using the alias name ShipmentLinesSno.
     * @return the SHIPMENT_LINES_SNO
     */
    public Integer getShipmentLinesSno() {
        return (Integer) getAttributeInternal(SHIPMENTLINESSNO);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIPMENT_LINES_SNO using the alias name ShipmentLinesSno.
     * @param value value to set the SHIPMENT_LINES_SNO
     */
    public void setShipmentLinesSno(Integer value) {
        setAttributeInternal(SHIPMENTLINESSNO, value);
    }

    /**
     * Gets the attribute value for SHIPMENT_HEADER_SNO using the alias name ShipmentHeaderSno.
     * @return the SHIPMENT_HEADER_SNO
     */
    public Integer getShipmentHeaderSno() {
        return (Integer) getAttributeInternal(SHIPMENTHEADERSNO);
    }

    /**
     * Sets <code>value</code> as attribute value for SHIPMENT_HEADER_SNO using the alias name ShipmentHeaderSno.
     * @param value value to set the SHIPMENT_HEADER_SNO
     */
    public void setShipmentHeaderSno(Integer value) {
        setAttributeInternal(SHIPMENTHEADERSNO, value);
    }

    /**
     * Gets the attribute value for DOCUMENT_ID using the alias name DocumentId.
     * @return the DOCUMENT_ID
     */
    public String getDocumentId() {
        return (String) getAttributeInternal(DOCUMENTID);
    }

    /**
     * Sets <code>value</code> as attribute value for DOCUMENT_ID using the alias name DocumentId.
     * @param value value to set the DOCUMENT_ID
     */
    public void setDocumentId(String value) {
        setAttributeInternal(DOCUMENTID, value);
    }

    /**
     * Gets the attribute value for DOCUMENT_TYPE using the alias name DocumentType.
     * @return the DOCUMENT_TYPE
     */
    public String getDocumentType() {
        return (String) getAttributeInternal(DOCUMENTTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for DOCUMENT_TYPE using the alias name DocumentType.
     * @param value value to set the DOCUMENT_TYPE
     */
    public void setDocumentType(String value) {
        setAttributeInternal(DOCUMENTTYPE, value);
    }

    /**
     * Gets the attribute value for QUANTITY using the alias name Quantity.
     * @return the QUANTITY
     */
    public BigDecimal getQuantity() {
        return (BigDecimal) getAttributeInternal(QUANTITY);
    }

    /**
     * Sets <code>value</code> as attribute value for QUANTITY using the alias name Quantity.
     * @param value value to set the QUANTITY
     */
    public void setQuantity(BigDecimal value) {
        setAttributeInternal(QUANTITY, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATED_DATE using the alias name CreatedDate.
     * @return the CREATED_DATE
     */
    public Date getCreatedDate() {
        return (Date) getAttributeInternal(CREATEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for CREATED_DATE using the alias name CreatedDate.
     * @param value value to set the CREATED_DATE
     */
    public void setCreatedDate(Date value) {
        setAttributeInternal(CREATEDDATE, value);
    }

    /**
     * Gets the attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @return the MODIFIED_BY
     */
    public String getModifiedBy() {
        return (String) getAttributeInternal(MODIFIEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_BY using the alias name ModifiedBy.
     * @param value value to set the MODIFIED_BY
     */
    public void setModifiedBy(String value) {
        setAttributeInternal(MODIFIEDBY, value);
    }

    /**
     * Gets the attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @return the MODIFIED_DATE
     */
    public Date getModifiedDate() {
        return (Date) getAttributeInternal(MODIFIEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for MODIFIED_DATE using the alias name ModifiedDate.
     * @param value value to set the MODIFIED_DATE
     */
    public void setModifiedDate(Date value) {
        setAttributeInternal(MODIFIEDDATE, value);
    }

    /**
     * Gets the associated <code>Row</code> using master-detail link InDocumentShipmentHeaderVO.
     */
    public Row getInDocumentShipmentHeaderVO() {
        return (Row) getAttributeInternal(INDOCUMENTSHIPMENTHEADERVO);
    }

    /**
     * Sets the master-detail link InDocumentShipmentHeaderVO between this object and <code>value</code>.
     */
    public void setInDocumentShipmentHeaderVO(Row value) {
        setAttributeInternal(INDOCUMENTSHIPMENTHEADERVO, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> AccVwIssueItemForDocumentShipmentQVO.
     */
    public RowSet getAccVwIssueItemForDocumentShipmentQVO() {
        return (RowSet) getAttributeInternal(ACCVWISSUEITEMFORDOCUMENTSHIPMENTQVO);
    }
    @Override
    public boolean isAttributeUpdateable(int i) {
        // TODO Implement this method
        if (getInDocumentShipmentHeaderVO().getAttribute("Posted").toString().equals("Y")) {
            return false;
       }
        return super.isAttributeUpdateable(i);
    }
}

