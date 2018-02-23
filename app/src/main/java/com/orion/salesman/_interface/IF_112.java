package com.orion.salesman._interface;

import com.orion.salesman._route._object.InforShopDetails;

import java.util.List;

/**
 * Created by maidinh on 21/9/2016.
 */
public interface IF_112 {
    void onSuccess(List<InforShopDetails> LIST);
    void onFail();
}
