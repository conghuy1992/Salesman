package com.orion.salesman._class;

import android.util.Log;

import com.google.gson.Gson;
import com.orion.salesman._object.BUY;
import com.orion.salesman._object.OrderBuying;
import com.orion.salesman._object.OrderGifItem;
import com.orion.salesman._object.OrderGift;
import com.orion.salesman._object.OrderResult;
import com.orion.salesman._object.PMPRODUCTLIST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 24/10/2016.
 */
public class getListPromotion {
    public static String TAG = "getListPromotion";

    public static boolean isAdd(List<PMPRODUCTLIST> lst, String pmId) {
        // KM 20170411
        /**
         for (PMPRODUCTLIST obj : lst) {
         if (obj.getV1().trim().equals(pmId))
         return false;
         }
         return true;
         */

        return true;
    }

    public static OrderResult ORD_GiftListPromotion(List<OrderBuying> lstBuy) {

        List<PMPRODUCTLIST> lstSUM = new ArrayList<>();
        for (OrderBuying order : lstBuy) {
            List<PMPRODUCTLIST> LIST_PROMOTION = order.getLIST_PROMOTION();
            for (int k = 0; k < LIST_PROMOTION.size(); k++) {
                PMPRODUCTLIST pmproductlist = LIST_PROMOTION.get(k);
                String pmId = pmproductlist.getV1().trim();
                if (isAdd(lstSUM, pmId))
                    lstSUM.add(pmproductlist);
            }
        }

        lstSUM = Const.SortListPM(lstSUM);


        OrderResult result = new OrderResult();
        List<OrderGift> lstGift = new ArrayList<>();
        List<PMPRODUCTLIST> LIST_PROMOTION = lstSUM;
//        for (OrderBuying order : lstBuy) {
//            LIST_PROMOTION = order.getLIST_PROMOTION();
        for (int k = 0; k < LIST_PROMOTION.size(); k++) {
            PMPRODUCTLIST pmproductlist = LIST_PROMOTION.get(k);
            String promotionCode = pmproductlist.getV1();
            String ASSORTEDITEM = pmproductlist.getV18();

//                Log.d(TAG, "promotionCode:" + promotionCode + " getPRICE:" + pmproductlist.getPRICE());

            List<PMPRODUCTLIST> LIST = pmproductlist.getPMPRODUCTLIST();
            int ConBuying = 0;
            int ConGift = 0;
            String strPMBuy = "";
            String strPMGift = "";
            List<OrderGifItem> lst1 = new ArrayList<>();
            List<OrderGifItem> lst2 = new ArrayList<>();

            for (PMPRODUCTLIST b : LIST) {
                if (b.getV1().trim().equals("BUY")) {
                    ConBuying = Integer.parseInt(b.getV9());
                    OrderGifItem ob = new OrderGifItem();
                    ob.setPRDCD("");
                    ob.setSKUCD(b.getV5());
                    ob.setBRNCD(b.getV3());
                    ob.setPRDNM("");
                    ob.setSKUNM("");
                    ob.setBRNNM("");
                    ob.setUNIT(Integer.parseInt(b.getV7()));
                    ob.setQTY(Integer.parseInt(b.getV8()));
                    lst1.add(ob);
                    if (!strPMBuy.equals(""))
                        strPMBuy += (b.getV9().equals("0") ? " AND " : " OR ");
                    strPMBuy += b.getV8() + (b.getV7().trim().equals("1") ? "BX" : b.getV7().trim().equals("2") ? "CS" : "EA") +
                            " " + b.getV4() + " " + b.getV6();
                } else {
                    ConGift = Integer.parseInt(b.getV9());
                    OrderGifItem ob = new OrderGifItem();
                    ob.setPRDCD(b.getV11());
                    ob.setSKUCD(b.getV5());
                    ob.setBRNCD(b.getV3());
                    ob.setPRDNM(b.getV12());
                    ob.setSKUNM(b.getV6());
                    ob.setBRNNM(b.getV4());
                    ob.setUNIT(Integer.parseInt(b.getV7()));
                    ob.setQTY(Integer.parseInt(b.getV8()));
                    lst2.add(ob);
                    if (!strPMGift.equals(""))
                        strPMGift += (b.getV9().trim().equals("0") ? " AND " : " OR ");
                    strPMGift += b.getV8() + (b.getV7().trim().equals("1") ? "BX" : b.getV7().trim().equals("2") ? "CS" : "EA") + " " + b.getV12();

                }
            }
//                Log.d(TAG,"ConBuying:"+ConBuying);
            if (ConBuying == 1) {
                boolean isAss = true;
                int assUnit = 0;
                int assQty = 0;
                for (OrderGifItem item : lst1) {
                    if ((assUnit != 0 && assUnit != item.getUNIT()) || (assQty != 0 && assQty != item.getQTY())) {
                        isAss = false;
                    } else {
                        assUnit = item.getUNIT();
                        assQty = item.getQTY();
                    }

                    for (OrderBuying objBuy : lstBuy) {
                        if (item.getSKUCD().trim().equals(objBuy.getSKUCD().trim())) {
                            String SKU = objBuy.getSKUCD().trim();
                            boolean isDS = objBuy.isDS();
                            int sumQty = item.getUNIT() == 3 ? item.getQTY() : item.getUNIT() == 2 ? item.getQTY() * objBuy.getCSES() : item.getQTY() * objBuy.getBXES();
                            if (sumQty != 0) {
                                int count = (int) (objBuy.getQTY() / sumQty);
                                int ex = objBuy.getQTY() % sumQty;

                                if (count > 0) {
                                    OrderGift objGift = new OrderGift();
                                    objGift.setCONDITION(ConGift);
                                    List<OrderGifItem> LIST1 = new ArrayList<>();
                                    List<OrderBuying> LIST2 = new ArrayList<>();
                                    objGift.setGiftList(LIST1);
                                    objGift.setBuyList(LIST2);
                                    for (OrderGifItem gift : lst2) {
                                        boolean gDS = objBuy.isDS();
                                        OrderGifItem ob = new OrderGifItem();
                                        ob.setBRNCD(gift.getBRNCD());
                                        ob.setPRDCD(gift.getPRDCD());
                                        ob.setQTY(count * gift.getQTY());
                                        ob.setSKUCD(gift.getSKUCD());
                                        ob.setUNIT(gift.getUNIT());
                                        ob.setPRDNM(gift.getPRDNM());
                                        ob.setSKUNM(gift.getSKUNM());
                                        ob.setBRNNM(gift.getBRNNM());
                                        ob.setDS(gDS);
                                        objGift.getGiftList().add(ob);
                                    }
                                    OrderBuying orderBuying = new OrderBuying();
                                    orderBuying.setPRDCD(objBuy.getPRDCD());
                                    orderBuying.setSKUCD(objBuy.getSKUCD());
                                    orderBuying.setBRNCD(objBuy.getBRNCD());
                                    orderBuying.setUNIT(objBuy.getUNIT());
                                    int divide = (objBuy.getUNIT() == 3 ? 1 : objBuy.getUNIT() == 2 ? objBuy.getCSES() : objBuy.getBXES());
                                    if (divide != 0) {
                                        orderBuying.setQTY((objBuy.getQTY() - ex) / divide);
                                        orderBuying.setCSES(objBuy.getCSES());
                                        orderBuying.setBXCS(objBuy.getBXCS());
                                        orderBuying.setBXES(objBuy.getBXES());
                                        objGift.getBuyList().add(orderBuying);
                                        objGift.setPMID(promotionCode);
                                        objGift.setPMDES(strPMBuy + " => " + strPMGift);
                                        lstGift.add(objGift);
                                    }
                                }
                                objBuy.setQTY(ex);
                            }
                        }
                    }
                }

                // calculator 2 times
                if (lst1.size() > 1 && isAss) {
                    if (ASSORTEDITEM.trim().equals("Y")) {
                        float assSUMQTY = 0;
                        for (OrderGifItem item : lst1) {
                            for (OrderBuying objBuy : lstBuy) {
                                if (item.getSKUCD().trim().equals(objBuy.getSKUCD().trim())) {
                                    float sumQty = assUnit == 3 ? objBuy.getQTY() : assUnit == 2 ? (float) (objBuy.getQTY() * 1.0 / objBuy.getCSES()) : (float) (objBuy.getQTY() * 1.0 / objBuy.getBXES());
                                    assSUMQTY += sumQty;
                                }
                            }
                        }

                        int assCount = (int) (assSUMQTY / assQty);
                        assSUMQTY = assCount * assQty;
                        float mintemp = 0;
                        if (assCount > 0) {
                            boolean gDS = true;
                            OrderGift objGift = new OrderGift();
                            objGift.setCONDITION(ConGift);
                            List<OrderGifItem> GiftList = new ArrayList<>();
                            List<OrderBuying> BuyList = new ArrayList<>();
                            objGift.setGiftList(GiftList);
                            objGift.setBuyList(BuyList);
                            for (OrderGifItem item : lst1) {
                                if (mintemp >= assSUMQTY)
                                    break;
                                //int counttemp = 0;
                                //int mintemp = mincount;
                                for (OrderBuying objBuy : lstBuy) {
                                    if (mintemp >= assSUMQTY)
                                        break;
                                    if (item.getSKUCD().trim().equals(objBuy.getSKUCD())) {
                                        float sumQty = assUnit == 3 ? objBuy.getQTY() : assUnit == 2 ? (float) (objBuy.getQTY() * 1.0 / objBuy.getCSES()) : (float) (objBuy.getQTY() * 1.0 / objBuy.getBXES());
                                        // int sumQty = item.UNIT == 3 ? item.QTY : item.UNIT == 2 ? item.QTY * objBuy.CSES : item.QTY * objBuy.BXES;
                                        mintemp += sumQty;
                                        if (mintemp > assSUMQTY) {
                                            sumQty = sumQty - (mintemp - assSUMQTY);
                                            mintemp = assSUMQTY;
                                        }
                                        int ex = (int) (objBuy.getQTY() - (int) (sumQty * (assUnit == 3 ? 1 : assUnit == 2 ? objBuy.getCSES() : objBuy.getBXES())));
                                        //counttemp += count;
                                        if (sumQty > 0) {
                                            OrderBuying orderBuying = new OrderBuying();
                                            orderBuying.setPRDCD(objBuy.getPRDCD());
                                            orderBuying.setSKUCD(objBuy.getSKUCD());
                                            orderBuying.setBRNCD(objBuy.getBRNCD());
                                            orderBuying.setUNIT(objBuy.getUNIT());
                                            int inputQTY = 0;
                                            if (sumQty < 1) {
                                                inputQTY = objBuy.getQTY();
                                            } else {
                                                inputQTY = (int) sumQty;
                                            }
                                            Log.d(TAG, "sumQty:" + sumQty + " inputQTY:" + inputQTY);
                                            orderBuying.setQTY(inputQTY);//(objBuy.QTY - ex) / (objBuy.UNIT == 3 ? 1 : objBuy.UNIT == 2 ? objBuy.CSES : objBuy.BXES),
                                            orderBuying.setCSES(objBuy.getCSES());
                                            orderBuying.setBXCS(objBuy.getBXCS());
                                            orderBuying.setBXES(objBuy.getBXES());
                                            objGift.getBuyList().add(orderBuying);
                                            objBuy.setQTY(ex);
                                            if (objBuy.isDS() == false)
                                                gDS = false;
                                        }
                                    }
                                }
                            }

                            boolean isFirst = true;

                            for (OrderGifItem gift : lst2) {
                                OrderGifItem orderGifItem = new OrderGifItem();
                                orderGifItem.setBRNCD(gift.getBRNCD());
                                orderGifItem.setPRDCD(gift.getPRDCD());
                                orderGifItem.setQTY(assCount * gift.getQTY());
                                orderGifItem.setSKUCD(gift.getSKUCD());
                                orderGifItem.setUNIT(gift.getUNIT());
                                orderGifItem.setPRDNM(gift.getPRDNM());
                                orderGifItem.setSKUNM(gift.getSKUNM());
                                orderGifItem.setBRNNM(gift.getBRNNM());
                                orderGifItem.setDS(gDS);
                                objGift.getGiftList().add(orderGifItem);
                                isFirst = false;
                            }
                            objGift.setPMID(promotionCode);
                            objGift.setPMDES(strPMBuy + " => " + strPMGift);
                            lstGift.add(objGift);
                        }
                    }
                }
            } else {
                boolean isFlag = true;
                boolean isDS = true;
                List<OrderBuying> lstTemp = new ArrayList<>();
                int mincount = 0;
                boolean gDS = true;
                for (OrderGifItem item : lst1) {
                    if (!isFlag)
                        break;
                    isFlag = false;
                    int sumQty = 0;
                    int count = 0;
                    for (OrderBuying objBuy : lstBuy) {
                        if (!objBuy.isDS()) {
                            isDS = false;
                        }
                        if (item.getSKUCD().trim().equals(objBuy.getSKUCD().trim())) {
                            sumQty = item.getUNIT() == 3 ? item.getQTY() : item.getUNIT() == 2 ? item.getQTY() * objBuy.getCSES() : item.getQTY() * objBuy.getBXES();
                            count += (int) (objBuy.getQTY() / sumQty);
                            int ex = (int) objBuy.getQTY() % sumQty;

                            if (count > 0) {
                                isFlag = true;
                                if (objBuy.isDS() == false)
                                    gDS = false;
                            }
                        }
                    }
                    if (count < mincount || mincount == 0)
                        mincount = count;
                }
                if (isFlag) {
                    OrderGift objGift = new OrderGift();
                    objGift.setCONDITION(ConGift);
                    List<OrderGifItem> LIST1 = new ArrayList<>();
                    List<OrderBuying> LIST2 = new ArrayList<>();
                    objGift.setGiftList(LIST1);
                    objGift.setBuyList(LIST2);

                    for (OrderGifItem item : lst1) {
                        int mintemp = mincount;
                        for (OrderBuying objBuy : lstBuy) {
                            if (item.getSKUCD().trim().equals(objBuy.getSKUCD().trim())) {

                                int sumQty = item.getUNIT() == 3 ? item.getQTY() : item.getUNIT() == 2 ? item.getQTY() * objBuy.getCSES() : item.getQTY() * objBuy.getBXES();
                                if (sumQty != 0) {
                                    int count = (int) (objBuy.getQTY() / sumQty);
                                    int ex = objBuy.getQTY() % sumQty;
                                    if (count >= mintemp) {
                                        OrderBuying ob = new OrderBuying();
                                        ob.setPRDCD(objBuy.getPRDCD());
                                        ob.setSKUCD(objBuy.getSKUCD());
                                        ob.setBRNCD(objBuy.getBRNCD());
                                        ob.setUNIT(objBuy.getUNIT());
                                        int divide = (objBuy.getUNIT() == 3 ? 1 : objBuy.getUNIT() == 2 ? objBuy.getCSES() : objBuy.getBXES());
                                        if (divide != 0) {
//                                            ob.setQTY((objBuy.getQTY() - ex) / divide);
                                            ob.setQTY(mintemp);
                                            ob.setCSES(objBuy.getCSES());
                                            ob.setBXCS(objBuy.getBXCS());
                                            ob.setBXES(objBuy.getBXES());
                                            objGift.getBuyList().add(ob);
//                                            objBuy.setQTY(ex + (count - mincount) * sumQty);
                                            objBuy.setQTY(ex + (count - mintemp) * sumQty);
                                            mintemp = 0;
                                        }
                                    } else {
                                        OrderBuying ob = new OrderBuying();
                                        ob.setPRDCD(objBuy.getPRDCD());
                                        ob.setSKUCD(objBuy.getSKUCD());
                                        ob.setBRNCD(objBuy.getBRNCD());
                                        ob.setUNIT(objBuy.getUNIT());
                                        int divide = (objBuy.getUNIT() == 3 ? 1 : objBuy.getUNIT() == 2 ? objBuy.getCSES() : objBuy.getBXES());
                                        if (divide != 0) {
//                                            ob.setQTY((objBuy.getQTY() - ex) / divide);
                                            ob.setQTY(count);
                                            ob.setCSES(objBuy.getCSES());
                                            ob.setBXCS(objBuy.getBXCS());
                                            ob.setBXES(objBuy.getBXES());
                                            objGift.getBuyList().add(ob);
//                                            objBuy.setQTY(ex + (count - mincount) * sumQty);
                                            objBuy.setQTY(ex);
                                            mintemp = mintemp - count;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    for (OrderGifItem gift : lst2) {
                        OrderGifItem ob = new OrderGifItem();
                        ob.setBRNCD(gift.getBRNCD());
                        ob.setPRDCD(gift.getPRDCD());
//                        ob.setQTY(mincount * gift.getQTY());
                        ob.setQTY(mincount * gift.getQTY());
                        ob.setSKUCD(gift.getSKUCD());
                        ob.setUNIT(gift.getUNIT());
                        ob.setPRDNM(gift.getPRDNM());
                        ob.setSKUNM(gift.getSKUNM());
                        ob.setBRNNM(gift.getBRNNM());
                        ob.setDS(gDS);
                        objGift.getGiftList().add(ob);
                    }
                    objGift.setPMID(promotionCode);
                    objGift.setPMDES(strPMBuy + " => " + strPMGift);
                    lstGift.add(objGift);
                }
            }
        }
//        }

        result.setLstBuy(lstGift);
        for (OrderBuying item : lstBuy) {
            if (item.getQTY() == 0) {
                //lstBuy.Remove(item);
            } else {
                int exTemp = item.getUNIT() == 1 ? item.getBXES() : item.getUNIT() == 2 ? item.getCSES() : 1;
                if (exTemp != 0) {
                    if ((item.getQTY() % exTemp) > 0) {
                        item.setUNIT(3);
                    } else {
                        if (exTemp != 0) {
                            item.setQTY(item.getQTY() / exTemp);
                        } else {
                            // save temp
                            item.setQTY(0);
                        }

                    }
                }

            }
        }
        result.setLstEx(lstBuy);
//        if(lstBuy!=null&&lstBuy.size()>0)
//        {
//            //calculator 2 times
//
//        }

//        Const.longInfo(TAG,"result:"+new Gson().toJson(result));
        return result;

    }

}
