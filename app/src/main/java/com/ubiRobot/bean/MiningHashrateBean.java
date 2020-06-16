package com.ubiRobot.bean;

public class MiningHashrateBean {
    private String WHashrate;//全网算力
    private String WNode;//"WNode": "integer,全网节点数",
    private String MHashrate;//"MHashrate": "integer,我的算力",
    private String THashrate;//"THashrate": "integer,团队算力"

    public String getWHashrate() {
        return WHashrate;
    }

    public String getWNode() {
        return WNode;
    }

    public String getMHashrate() {
        return MHashrate;
    }

    public String getTHashrate() {
        return THashrate;
    }
}
