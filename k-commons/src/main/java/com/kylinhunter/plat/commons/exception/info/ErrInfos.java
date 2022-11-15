package com.kylinhunter.plat.commons.exception.info;

import org.apache.commons.codec.digest.Crypt;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class ErrInfos {
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "success";
    public static final int CODE_UNKNOWN = -1;
    public static final String MSG_UNKNOWN = "UNKNOWN";
    public static final ErrInfo UNKNOWN = new ErrInfo(CODE_UNKNOWN, MSG_UNKNOWN);

    public static final ErrInfoClassify BASE = new ErrInfoClassify(10000);
    public static final ErrInfo FORMAT = new ErrInfo(BASE);
    public static final ErrInfo INIT = new ErrInfo(BASE);
    public static final ErrInfo INTERNAL = new ErrInfo(BASE);
    public static final ErrInfo IO = new ErrInfo(BASE);
    public static final ErrInfo PARAM = new ErrInfo(BASE);
    public static final ErrInfo GENERAL = new ErrInfo(BASE);
    public static final ErrInfo SYSTEM = new ErrInfo(BASE);
    public static final ErrInfo CRYPT = new ErrInfo(BASE);



    private static final ErrInfoClassify CLASSIFY_BIZ = new ErrInfoClassify(10001);
    public static final ErrInfo BIZ = new ErrInfo(CLASSIFY_BIZ);

    private static final ErrInfoClassify CLASSIFY_DB = new ErrInfoClassify(10002);
    public static final ErrInfo DB = new ErrInfo(CLASSIFY_DB);
    public static final ErrInfo DB_NO_EXIST = new ErrInfo(CLASSIFY_DB);

}
