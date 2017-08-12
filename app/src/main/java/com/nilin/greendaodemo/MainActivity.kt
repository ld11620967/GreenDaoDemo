package com.nilin.greendaodemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    var userDao: UserDao?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDbHelp()

        /*新增一条数据*/
        btnAdd!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val id = etId.text.toString()
                val name = etName.text.toString()
                if (isNotEmpty(id) && isNotEmpty(name)) {
                    val qb = userDao!!.queryBuilder()
                    val list = qb.where(UserDao.Properties.Id.eq(id)).list() as ArrayList<User>
                    if (list.size > 0) {
                        Toast.makeText(this@MainActivity, "主键重复", Toast.LENGTH_SHORT).show()
                    } else {
                        userDao!!.insert(User(java.lang.Long.valueOf(id), name))
                        Toast.makeText(this@MainActivity, "插入数据成功", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (isEmpty(id) && isNotEmpty(name)) {
                        Toast.makeText(this@MainActivity, "id为空", Toast.LENGTH_SHORT).show()
                    }
                    if (isEmpty(name) && isNotEmpty(id)) {
                        Toast.makeText(this@MainActivity, "姓名为空", Toast.LENGTH_SHORT).show()
                    }
                    if (isEmpty(id) && isEmpty(name)) {
                        Toast.makeText(this@MainActivity, "请填写信息", Toast.LENGTH_SHORT).show()
                    }

                }
                etId!!.setText("")
                etName!!.setText("")
            }
        })

        /*删除指定数据*/
        btnDelete!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val id = etId.text.toString()
                if (isNotEmpty(id)) {
                    userDao!!.deleteByKey(java.lang.Long.valueOf(id)!!)
                    val qb = userDao!!.queryBuilder()
                    val list = qb.where(UserDao.Properties.Id.eq(id)).list() as ArrayList<User>
                    if (list.size < 1) {
                        Toast.makeText(this@MainActivity, "删除数据成功", Toast.LENGTH_SHORT).show()
                        etId.setText("")
                        etName.setText("")
                    }
                } else {
                    Toast.makeText(this@MainActivity, "id为空", Toast.LENGTH_SHORT).show()
                }
            }
        })

        /*查询数据*/
        btnQuery!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val id = etId.text.toString()
                if (isNotEmpty(id)) {
                    val qb = userDao!!.queryBuilder()
                    val list = qb.where(UserDao.Properties.Id.eq(id)).list() as ArrayList<User>
                    if (list.size > 0) {
                        var text = ""
                        for (user in list) {
                            text = text + "\r\n" + user.name
                        }
                        tvQuery.text = text
                    } else {
                        tvQuery.text = ""
                        Toast.makeText(this@MainActivity, "不存在该数据", Toast.LENGTH_SHORT).show()
                    }
                    etId.setText("")
                    etName.setText("")
                } else {
                    Toast.makeText(this@MainActivity, "id为空", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    /*初始化数据库相关*/
    private fun initDbHelp() {
        val helper = DaoMaster.DevOpenHelper(this, "greendao", null)
        val db = helper.writableDatabase
        val daoMaster = DaoMaster(db)
        val daoSession = daoMaster.newSession()
        userDao = daoSession.userDao
    }

    private fun isNotEmpty(s: String?): Boolean {
        if (s != null && s != "" || s!!.length > 0) {
            return true
        } else {
            return false
        }
    }

    private fun isEmpty(s: String): Boolean {
        if (isNotEmpty(s)) {
            return false
        } else {
            return true
        }
    }

}

