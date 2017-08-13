package com.nilin.simplenote

import com.nilin.greendaodemo.DaoMaster
import com.nilin.greendaodemo.DaoSession


/**
 * Created by nilin on 2017/8/13.
 */
class GreenDaoManager private constructor() {

    var master: DaoMaster? = null
        private set
    var session: DaoSession? = null
        private set

    init {
        if (mInstance == null) {
            val devOpenHelper = DaoMaster.DevOpenHelper(AppContext.context, "user1-db", null)//此处为自己需要处理的表
            master = DaoMaster(devOpenHelper.writableDatabase)
            session = master!!.newSession()
        }
    }

    val newSession: DaoSession
        get() {
            session = master!!.newSession()
            return session as DaoSession
        }

    companion object {
        private var mInstance: GreenDaoManager? = null //单例

        //保证异步处理安全操作
        val instance: GreenDaoManager
            get() {
                if (mInstance == null) {
                    synchronized(GreenDaoManager::class.java) {

                        if (mInstance == null) {
                            mInstance = GreenDaoManager()
                        }
                    }
                }
                return mInstance as GreenDaoManager
            }
    }
}