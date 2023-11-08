package com.seg2105.hams.Util;

/*
    Used when getting object from database, as this is an asynchronous procedure,
    which requires a callback to function properly in the code
 */

import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Users.User;

import java.util.List;

public interface UserCallback {
    void onSuccess();
    void onSuccess(Object object);
    void onListLoaded(List list);
    void onFailure(String error);
}
