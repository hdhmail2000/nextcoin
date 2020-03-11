package com.ifenduo.wheelpicker.entity;

import java.util.List;

/**
 * 用于联动选择器展示的第一级条目
 * <br />
 * DateTime:2017/04/17 00:33
 * Builder:Android Studio
 *
 * @see com.ifenduo.wheelpicker.picker.LinkagePicker
 */
public interface LinkageFirst<Snd> extends LinkageItem {

    List<Snd> getSeconds();

}