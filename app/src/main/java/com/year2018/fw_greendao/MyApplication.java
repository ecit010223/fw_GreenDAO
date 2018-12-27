package com.year2018.fw_greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.year2018.fw_greendao.dao.DaoMaster;
import com.year2018.fw_greendao.dao.DaoSession;

public class MyApplication extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this,"test.db");
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        /**
         * 数据库连接属于DaoMaster，因此多个会话引用相同的数据库连接。因此，可以很快创建新的会话。
         * 但是，每个会话都会分配内存，通常是实体的会话“缓存”。
         */
        daoSession = daoMaster.newSession();
        /**
         * 清除整个session，没有缓存对象返回。
         * 如果有两个查询返回相同的数据库对象,则返回的对象是同一个。
         * 这个副作用是某种实体“缓存”。如果一个实体对象仍然在内存中（greenDAO在这里使用弱引用），
         * 则该实体不会再构造。另外，greenDAO不执行数据库查询来更新实体值。相反，
         * 对象会从会话缓存中“立即”返回，这会比一个或两个数量级更快。
         */
//        daoSession.clear();
    }

    public DaoSession getDaoSession(){
        return daoSession;
    }
}
