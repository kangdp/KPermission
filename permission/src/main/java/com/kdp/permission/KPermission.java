package com.kdp.permission;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.Arrays;

import static com.kdp.permission.KPermissionFragment.*;

/***
 * @author kdp
 * @date 2019/6/28 9:35
 * @description 动态权限
 */
public class KPermission {
    private static final String TAG = KPermission.class.getSimpleName();
    private KPermissionFragment kPermissionFragment;


    public KPermission(FragmentActivity activity) {
        kPermissionFragment = createFragment(activity.getSupportFragmentManager());
    }
    public KPermission(Fragment fragment) {
        kPermissionFragment = createFragment(fragment.getChildFragmentManager());
    }

    private KPermissionFragment createFragment(FragmentManager fragmentManager) {
        KPermissionFragment kPermissionFragment = (KPermissionFragment) fragmentManager.findFragmentByTag(TAG);
        boolean isNull = kPermissionFragment == null;
        if (isNull){
            kPermissionFragment = new KPermissionFragment();
            fragmentManager.beginTransaction().add(kPermissionFragment,TAG).commitNow();
        }
        return kPermissionFragment;
    }

    /**
     * 请求所有权限是否都已授权
     * @param permissions
     * @return
     */
    public KPermission request(String... permissions){
        kPermissionFragment.PERMISSION = ALL_PERMISSION;
        kPermissionFragment.setPermissions(permissions);
        return this;
    }

    /**
     * 请求每一个权限的授权结果
     * @param permissions
     * @return
     */
    public KPermission requestEach(String... permissions){
        kPermissionFragment.PERMISSION = EACH_PERMISSION;
        kPermissionFragment.setPermissions(permissions);
        return this;
    }
    public void execute(PermissionsCallback permissionsCallback){
        if (kPermissionFragment.PERMISSION == ALL_PERMISSION){
            if (isMarshmallow() && !isAllGranted()){
                kPermissionFragment.requestPermissions(permissionsCallback);
            }else {
                String[] permissions = kPermissionFragment.getPermissions();
                permissionsCallback.onResult(new Permission(Arrays.toString(permissions),true,kPermissionFragment.shouldShowRequestPermission(permissions,true)));
            }
        }else {
            kPermissionFragment.requestPermissions(permissionsCallback);
        }
    }

    private boolean isMarshmallow(){
        return Build.VERSION.SDK_INT >= 23;
    }


    /**
     * 检查是否授权
     * @param permission
     * @return
     */
    public boolean isGranted(String permission){
        return kPermissionFragment.isGranted(permission);
    }

    /**
     * 是否要展示请求的权限弹窗
     * @param permission
     * @return 返回在拒绝权限后，是否选中了【不再询问】选项
     */
    public boolean shouldShowRequestPermission(String permission){
        return kPermissionFragment.shouldShowRequestPermissionRationale(permission);
    }

    private boolean isAllGranted(){
        String[] permissions = kPermissionFragment.getPermissions();
        if (permissions == null || permissions.length == 0)
            throw new RuntimeException("The number of permissions must not be less than 0");
        boolean isGrant = true;
        for (String permission : permissions) {
            if (!kPermissionFragment.isGranted(permission)){
                isGrant = false;
                break;
            }
        }
        return isGrant;
    }




}
