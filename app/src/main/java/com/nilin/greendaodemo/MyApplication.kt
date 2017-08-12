package com.nilin.greendaodemo

import android.database.sqlite.SQLiteDatabase
import android.app.Application



/**
 * Created by liangd on 2017/8/10.
 */
class MyApplication : Application() {
    private var mHelper: DaoMaster.DevOpenHelper? = null
    var db: SQLiteDatabase? = null
        private set
    private var mDaoMaster: DaoMaster? = null
    var daoSession: DaoSession? = null
        private set

    override fun onCreate() {
        super.onCreate()
        instances = this
        setDatabase()
    }

    /**
     * 设置greenDao
     */
    private fun setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = DaoMaster.DevOpenHelper(this, "notes-db", null)
        db = mHelper!!.writableDatabase
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = DaoMaster(db)
        daoSession = mDaoMaster!!.newSession()
    }

    companion object {
        lateinit var instances: MyApplication
    }
}

