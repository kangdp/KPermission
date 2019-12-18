package com.kdp.permission;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.Arrays;


/***
 * @author kdp
 * @date 2019/6/28 9:39
 * @description
 */
public class KPermissionFragment extends Fragment {
    private static final int PERMISSION_REQUEST_CODE = 100;
    static final int ALL_PERMISSION = 0; //是否所有权限已授权
    static final int EACH_PERMISSION = 1;//每个权限是否已授权
    int PERMISSION = ALL_PERMISSION;
    private PermissionsCallback permissionsCallback;
    private String[] permissions;
    public KPermissionFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    void setPermissions(String[] permissions){
        this.permissions = permissions;
    }

    public String[] getPermissions() {
        return permissions;
    }

    @TargetApi(23)
    void requestPermissions(PermissionsCallback permissionsCallback){
        this.permissionsCallback = permissionsCallback;
        if (permissions == null || permissions.length == 0)
            throw new RuntimeException("The number of permissions must not be less than 0");
        this.requestPermissions(permissions,PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && permissionsCallback != null){
            if (PERMISSION == ALL_PERMISSION){
                permissionsCallback.onResult(new Permission(Arrays.toString(permissions),checkApplyPermission(grantResults),shouldShowRequestPermission(permissions)));
            }else {
                for (int i = 0; i < permissions.length; i++) {
                    permissionsCallback.onResult(new Permission(permissions[i],grantResults[i] == PackageManager.PERMISSION_GRANTED,shouldShowRequestPermissionRationale(permissions[i])));
                }
            }
        }
    }


    /**
     * 请求多个权限，是否全部被授权
     * @param grantResults
     * @return
     */
    private boolean checkApplyPermission(int[] grantResults) {
        boolean isGrantResult = false;
        if (grantResults.length > 0) {
            isGrantResult = true;
            for (int grantResult : grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    isGrantResult = false;
                    break;
                }
            }
        }
        return isGrantResult;
    }

    /**
     * 检查是否已授权
     * @param permission
     * @return
     */
    public boolean isGranted(String permission){
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity == null)
            throw new IllegalArgumentException("This fragment must be attached to an activity");
        return ActivityCompat.checkSelfPermission(fragmentActivity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求多个权限，只要有一个权限被拒绝且选中了【不在询问】，此方法都会返回false，否则返回true
     * @param permissions
     * @return
     */

    boolean shouldShowRequestPermission(String[] permissions){
        for (String permission : permissions) {
            if (!shouldShowRequestPermissionRationale(permission)) return false;
        }
        return true;
    }


}
