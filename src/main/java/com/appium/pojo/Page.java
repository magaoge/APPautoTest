package com.appium.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by mgg on 2021/10/20
 */
@Data
public class Page {
    private String activityName;
    private String pageDesc;
    private List<Locator> listLocator;

    public Page(String activityName, String pageDesc, List<Locator> listLocator) {
        this.activityName = activityName;
        this.pageDesc = pageDesc;
        this.listLocator = listLocator;
    }
}
