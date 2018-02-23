package com.orion.salesman._interface;

import com.orion.salesman._object.ObjA0127;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 8/11/2016.
 */
public interface IF_127 {
    void onSuccess(ArrayList<ObjA0127> lst);

    void onFail();
    void onError(String s);
}
