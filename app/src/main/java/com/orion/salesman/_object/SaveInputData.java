package com.orion.salesman._object;

/**
 * Created by maidinh on 22/9/2016.
 */
public class SaveInputData {
    int id;
    String DATE = "";
    String SHOPID = "";
    String STOCK_PIE = "";
    String STOCK_SNACK = "";
    String STOCK_GUM = "";
    String DISPLAY_PIE = "";
    String DISPLAY_SNACK = "";
    String ORDER_PIE = "";
    String ORDER_SNACK = "";
    String ORDER_GUM = "";
    String URL_IMG = "";
    String IS_124 = ""; // 0 ;not yet, 1-> saved
    String COUNT_124 = ""; // ex: 4 product, update ok 3 product -> still 1 produtct :COUNT_UPDATE = 3; next time post to API product start 3 in list
    String DATA_124 = "";
    String FILE_PATH_IMG = "";
    String IS_118 = ""; //0 : 1;
    String DATA_118 = "";
    String IS_115 = "";
    String DATA_115 = "";
    String IS_119 = "";
    String DATA_119 = "";


    public String getIS_124() {
        return IS_124;
    }

    public void setIS_124(String IS_124) {
        this.IS_124 = IS_124;
    }

    public String getCOUNT_124() {
        return COUNT_124;
    }

    public void setCOUNT_124(String COUNT_124) {
        this.COUNT_124 = COUNT_124;
    }

    public String getDATA_124() {
        return DATA_124;
    }

    public void setDATA_124(String DATA_124) {
        this.DATA_124 = DATA_124;
    }

    public String getFILE_PATH_IMG() {
        return FILE_PATH_IMG;
    }

    public void setFILE_PATH_IMG(String FILE_PATH_IMG) {
        this.FILE_PATH_IMG = FILE_PATH_IMG;
    }

    public String getIS_118() {
        return IS_118;
    }

    public void setIS_118(String IS_118) {
        this.IS_118 = IS_118;
    }

    public String getDATA_118() {
        return DATA_118;
    }

    public void setDATA_118(String DATA_118) {
        this.DATA_118 = DATA_118;
    }

    public String getIS_115() {
        return IS_115;
    }

    public void setIS_115(String IS_115) {
        this.IS_115 = IS_115;
    }

    public String getDATA_115() {
        return DATA_115;
    }

    public void setDATA_115(String DATA_115) {
        this.DATA_115 = DATA_115;
    }

    public String getIS_119() {
        return IS_119;
    }

    public void setIS_119(String IS_119) {
        this.IS_119 = IS_119;
    }

    public String getDATA_119() {
        return DATA_119;
    }

    public void setDATA_119(String DATA_119) {
        this.DATA_119 = DATA_119;
    }

    public String getURL_IMG() {
        return URL_IMG;
    }

    public void setURL_IMG(String URL_IMG) {
        this.URL_IMG = URL_IMG;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getSHOPID() {
        return SHOPID;
    }

    public void setSHOPID(String SHOPID) {
        this.SHOPID = SHOPID;
    }

    public String getSTOCK_PIE() {
        return STOCK_PIE;
    }

    public void setSTOCK_PIE(String STOCK_PIE) {
        this.STOCK_PIE = STOCK_PIE;
    }

    public String getSTOCK_SNACK() {
        return STOCK_SNACK;
    }

    public void setSTOCK_SNACK(String STOCK_SNACK) {
        this.STOCK_SNACK = STOCK_SNACK;
    }

    public String getSTOCK_GUM() {
        return STOCK_GUM;
    }

    public void setSTOCK_GUM(String STOCK_GUM) {
        this.STOCK_GUM = STOCK_GUM;
    }

    public String getDISPLAY_PIE() {
        return DISPLAY_PIE;
    }

    public void setDISPLAY_PIE(String DISPLAY_PIE) {
        this.DISPLAY_PIE = DISPLAY_PIE;
    }

    public String getDISPLAY_SNACK() {
        return DISPLAY_SNACK;
    }

    public void setDISPLAY_SNACK(String DISPLAY_SNACK) {
        this.DISPLAY_SNACK = DISPLAY_SNACK;
    }

    public String getORDER_PIE() {
        return ORDER_PIE;
    }

    public void setORDER_PIE(String ORDER_PIE) {
        this.ORDER_PIE = ORDER_PIE;
    }

    public String getORDER_SNACK() {
        return ORDER_SNACK;
    }

    public void setORDER_SNACK(String ORDER_SNACK) {
        this.ORDER_SNACK = ORDER_SNACK;
    }

    public String getORDER_GUM() {
        return ORDER_GUM;
    }

    public void setORDER_GUM(String ORDER_GUM) {
        this.ORDER_GUM = ORDER_GUM;
    }
}
