package com.baidu.testframework;

/**
 * Created by edwardsbean on 14-10-14.
 */
public enum SoftSortOption {
    Default(0),
    f_idASC(1);

    private final int value;

    private SoftSortOption(int value) {
        this.value = value;
    }


}
