## KPermission
一个使用简单的权限库

## 添加依赖
    implementation 'com.kdp:permission:1.0.0'
## 从1.1.0版本开始支持Androidx
    implementation 'com.kdp:permission:1.1.0'
## 简单使用

- 请求单个或多个权限并返回请求结果

      private fun requestAllPermission(){
        //实例化KPermission
        val kPermission = KPermission(this)
        //申请权限
        kPermission.request(Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .execute {
                    permission ->
                // name: 所有请求的权限
                // isGrant: 只要有一个权限被拒绝， 都返回false,否则返回true
                // isShouldShowRequestPermission都返回false: 只要有一个权限被拒绝且选择了【不在询问】选项，都返回false,否则返回true
                when {
                    permission.isGrant -> logger("你已经获取了${permission.name}权限")
                    permission.isShouldShowRequestPermission -> {
                        if (!kPermission.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            logger("你已经拒绝了${Manifest.permission.READ_EXTERNAL_STORAGE}权限")
                        }

                        if (!kPermission.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            logger("你已经拒绝了${Manifest.permission.WRITE_EXTERNAL_STORAGE}权限")
                        }
                    }
                    else -> {
                        if (!kPermission.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && !kPermission.shouldShowRequestPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
                            logger("你已经禁止了${Manifest.permission.READ_EXTERNAL_STORAGE}权限,请手动在设置页面打开")
                        }

                        if (!kPermission.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !kPermission.shouldShowRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                            logger("你已经禁止了${Manifest.permission.WRITE_EXTERNAL_STORAGE}权限,请手动在设置页面打开")
                        }
                    }
                }
            }
      }



- 请求单个或多个权限并返回每一个权限的请求结果
    
      private fun requestEachPermission() {
        //实例化KPermission
        val kPermission = KPermission(this)
        //申请权限
        kPermission.requestEach(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .execute {//结果回调
                    permission ->
                when {
                    permission.isGrant -> logger("你已经获取了${permission.name}权限")
                    permission.isShouldShowRequestPermission -> logger("你已经拒绝了${permission.name}权限")
                    else ->  logger("你已经禁止了${permission.name}权限，请手动在设置页面打开")
                }
            }
      }
      
## 混淆

    -keep class com.kdp.permission.Permission { *; }
      

## LICENSE

        Copyright 2019 kangdongpu

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

            http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
