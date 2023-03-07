package io.github.kylinhunter.commons.clazz.monitor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:53
 **/
public class TrackContext {

    private static final ThreadLocal<String> trackLocal = new ThreadLocal<String>();

    public static void clear(){
        trackLocal.remove();
    }

    public static String getLinkId(){
        return trackLocal.get();
    }

    public static void setLinkId(String linkId){
        trackLocal.set(linkId);
    }

}
