package com.nilin.simplenote

import android.app.Application
import android.content.Context


/**
 * Created by nilin on 2017/8/13.
 */
class AppContext : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //greenDao全局配置,只希望有一个数据库操作对象
        GreenDaoManager.instance
    }

    companion object {

        var context: Context? = null
            private set
    }
}
