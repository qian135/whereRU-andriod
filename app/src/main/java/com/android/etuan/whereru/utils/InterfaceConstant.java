package com.android.etuan.whereru.utils;

public class InterfaceConstant {

    public static final String LOCALHOST = "192.168.191.1";
//    private static final String LOCALHOST = "10.0.2.2";

    public static final String LOGIN_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/WUsers/login";

    public static final String REGISTER_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/WUsers/";

    public static final String SCHOOL_NAME_LIST_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Schools?filter[fields][name]=true";

    //注意在代码里需要在最后添加上用户的学校
    //注意刘建东这里把hotActivities写成了hotActiveties
    public static final String HOT_SCHOOL_ACTIVITY_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Activities/hotActiveties?school=";

    //注意在代码里需要在最后添加上用户的学校
    public static final String SCHOOL_ACTIVITY_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Activities/mySchoolActiveties?school=";

    //注意在代码里需要在最后添加上用户的学校
    public static final String SCHOOL_COMPETITION_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Races/mySchoolRaces?school=";

    //注意在代码里需要在最后添加上用户的学校
    public static final String SCHOOL_TEAM_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Teams/mySchoolTeams?school=";


    //要在URL最后补充上搜索的关键字
    public static final String ACTIVITY_SEARCH_URL =
            "http://" + InterfaceConstant.LOCALHOST + ":3000/api/Activities/search?keyword=";

    public final static int POST_SUCCESS = 200;
    public final static int POST_FAIL = 400;
    public final static int GET_SUCCESS = 201;
    public final static int GET_FAIL = 401;


    //搜索类型
    public static final int ACTIVITY = 1;
    public static final int RACE = 2;
    public static final int TEAM = 3;
    public static final int USER = 4;
    public static final int COTERIE = 5;

}