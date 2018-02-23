package com.orion.salesman._interface;

import com.orion.salesman._object.SeBranch;

import java.util.List;

/**
 * Created by maidinh on 13/12/2016.
 */

public interface IF_SE_BRANCH {
    void onSuccess(List<SeBranch> lst);
    void onFail();
}
