package com.ubiRobot.modules.user.collectionCode;

import com.ubiRobot.base.BaseView;

public interface CollectionCodeView extends BaseView {

    void requestFail(int code, String msg);

    void uplodeSuccess(String msg);



}
