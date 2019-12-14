package com.kdp.permission;


import androidx.annotation.NonNull;

/***
 * @author kdp
 * @date 2019/6/28 11:13
 * @description
 */
public interface PermissionsCallback{
    void onResult(@NonNull Permission permission);
}
