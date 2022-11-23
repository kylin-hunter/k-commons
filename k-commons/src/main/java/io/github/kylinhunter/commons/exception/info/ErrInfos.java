package io.github.kylinhunter.commons.exception.info;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@ErrInfoAware
public class ErrInfos {
    public static final int CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "success";
    public static final int CODE_UNKNOWN = -1;
    public static final String MSG_UNKNOWN = "UNKNOWN";
    public static final ErrInfo UNKNOWN = new ErrInfo(CODE_UNKNOWN, MSG_UNKNOWN);

    private static int BASE_CODE = 10000;
    public static final ErrInfo FORMAT = new ErrInfo(++BASE_CODE);
    public static final ErrInfo INIT = new ErrInfo(++BASE_CODE);
    public static final ErrInfo INTERNAL = new ErrInfo(++BASE_CODE);
    public static final ErrInfo IO = new ErrInfo(++BASE_CODE);
    public static final ErrInfo PARAM = new ErrInfo(++BASE_CODE);
    public static final ErrInfo GENERAL = new ErrInfo(++BASE_CODE);
    public static final ErrInfo SYSTEM = new ErrInfo(++BASE_CODE);
    public static final ErrInfo CRYPT = new ErrInfo(++BASE_CODE);

    private static int BASE_BIZ_CODE = 20000;
    public static final ErrInfo BIZ = new ErrInfo(++BASE_BIZ_CODE);
    public static final ErrInfo DB = new ErrInfo(++BASE_BIZ_CODE);
    public static final ErrInfo DB_NO_EXIST = new ErrInfo(++BASE_BIZ_CODE);

}
