package com.seg2105.hams.Util;

/*
    Used when getting object from database, as this is an asynchronous procedure,
    which requires a callback to function properly in the code
 */

import com.seg2105.hams.Users.Person;

import java.util.List;

public interface UserCallback {
    void onSuccess();
    void onPersonsLoaded(List<Person> persons);
    void onFailure(String error);
}
