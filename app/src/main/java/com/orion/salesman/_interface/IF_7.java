package com.orion.salesman._interface;

import com.orion.salesman._object.NearShop;

import java.util.List;

/**
 * Created by maidinh on 15/12/2016.
 */

public interface IF_7 {
    void onSuccess(List<NearShop> LIST);
    void onNoInternet();
    void onFail();
    void onError(String s);
}
