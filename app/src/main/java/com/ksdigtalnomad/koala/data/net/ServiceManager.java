package com.ksdigtalnomad.koala.data.net;


import com.ksdigtalnomad.koala.data.net.services.AlarmService;
import com.ksdigtalnomad.koala.data.net.services.AppCSService;
import com.ksdigtalnomad.koala.data.net.services.AppVersionService;
import com.ksdigtalnomad.koala.data.net.services.CalendarService;
import com.ksdigtalnomad.koala.data.net.services.DefaultDataService;
import com.ksdigtalnomad.koala.data.net.services.InterviewService;
import com.ksdigtalnomad.koala.data.net.services.UserService;

public enum  ServiceManager {
    INSTANCE;
    private UserService userService;
    private AlarmService alarmService;
    private AppCSService appCSService;
    private AppVersionService appVersionService;
    private CalendarService calendarService;
    private DefaultDataService defaultDataService;
    private InterviewService interviewService;

    ServiceManager() {
        userService = RetrofitServiceGenericFactory.createService(UserService.class);
        alarmService = RetrofitServiceGenericFactory.createService(AlarmService.class);
        appCSService = RetrofitServiceGenericFactory.createService(AppCSService.class);
        appVersionService = RetrofitServiceGenericFactory.createService(AppVersionService.class);
        calendarService = RetrofitServiceGenericFactory.createService(CalendarService.class);
        defaultDataService = RetrofitServiceGenericFactory.createService(DefaultDataService.class);
        interviewService = RetrofitServiceGenericFactory.createService(InterviewService.class);
    }

    public static ServiceManager getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() { return userService; }
    public AlarmService getAlarmService() { return alarmService; }
    public AppCSService getAppCSService() { return appCSService; }
    public AppVersionService getAppVersionService() { return appVersionService; }
    public CalendarService getCalendarService() { return calendarService; }
    public DefaultDataService getDefaultDataService() { return defaultDataService; }
    public InterviewService getInterviewService() { return interviewService; }

}
