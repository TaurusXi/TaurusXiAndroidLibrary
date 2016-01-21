package org.taurusxi.taurusxi.kotlin.utils

import android.os.Build

/**
 * Created by wumin on 16/1/21.
 */

public fun hasKitKat(code:() -> Unit){
    supportsVersion(code, 19)
}

public fun hasLollipop(code:() -> Unit){
    supportsVersion(code, 21)
}

public fun hasM(code:() -> Unit){
    supportsVersion(code, 23)
}

private fun supportsVersion(code:() -> Unit, sdk: Int){
    if (Build.VERSION.SDK_INT >= sdk){
        code.invoke()
    }
}