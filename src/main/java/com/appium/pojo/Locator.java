package com.appium.pojo;

import lombok.Data;

/**
 * Created by mgg on 2021/10/20
 */
@Data
public class Locator {
    private String locatorBy;
    private String locatorValue;
    private String valueDesc;

    public Locator(String locatorBy, String locatorValue, String valueDesc) {
        this.locatorBy = locatorBy;
        this.locatorValue = locatorValue;
        this.valueDesc = valueDesc;
    }
}
