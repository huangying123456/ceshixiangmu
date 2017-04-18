package com.youhujia.solar.domain.common;

import java.io.Serializable;

/**
 * Created by Shawn Tien on 24/10/2016.
 */

public interface Identifiable<T extends Serializable> {
    T getId();
}
