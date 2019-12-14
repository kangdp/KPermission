package com.kdp.permission;


/***
 * @author kdp
 * @date 2019/8/8 15:44
 * @description
 */
public class Permission {
    public String name;
    public boolean isGrant;
    public boolean isShouldShowRequestPermission;

    Permission(String name, boolean isGrant, boolean isShouldShowRequestPermission) {
        this.name = name;
        this.isGrant = isGrant;
        this.isShouldShowRequestPermission = isShouldShowRequestPermission;
    }


    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", isGrant=" + isGrant +
                ", isShouldShowRequestPermission=" + isShouldShowRequestPermission +
                '}';
    }

}
