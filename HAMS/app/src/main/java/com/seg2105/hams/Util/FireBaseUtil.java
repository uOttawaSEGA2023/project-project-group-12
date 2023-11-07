package com.seg2105.hams.Util;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseUtil {
    public static void updateField(String objectType, String objectID, String fieldName, Object newValue) {
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference(objectType).child(objectID);
        userReference.child(fieldName).setValue(newValue);
    }
}
