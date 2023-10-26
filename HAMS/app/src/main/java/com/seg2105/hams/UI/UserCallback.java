package com.seg2105.hams.UI;

/*
    Used when getting object from database, as this is an asynchronous procedure,
    which requires a callback to function properly in the code
 */

public interface UserCallback {
    void onUserLoaded();
    void onFailure(String error);
}
