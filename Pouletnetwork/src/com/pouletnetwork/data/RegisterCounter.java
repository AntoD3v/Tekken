package com.pouletnetwork.data;

import com.tekken.data.cache.Cache;

public class RegisterCounter extends Cache<Integer> {



    @Override
    public Integer updater() {
        return 1;
    }
}
