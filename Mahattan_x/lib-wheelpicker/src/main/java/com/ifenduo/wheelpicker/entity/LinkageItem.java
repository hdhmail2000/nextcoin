package com.ifenduo.wheelpicker.entity;

/**
 * 用于联动选择器展示的条目
 * <br />
 * DateTime:2017/04/17 00:31
 * Builder:Android Studio
 *
 * @see com.ifenduo.wheelpicker.picker.LinkagePicker
 */
interface LinkageItem extends WheelItem {

    /**
     * 唯一标识，用于判断两个条目是否相同
     */
    Object getId();

}
