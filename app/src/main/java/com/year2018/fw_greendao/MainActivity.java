package com.year2018.fw_greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.year2018.fw_greendao.dao.StudentDao;
import com.year2018.fw_greendao.entity.Student;
import com.year2018.fw_greendao.entity.User;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDB();
    }

    private void createDB(){
        StudentDao studentDao = ((MyApplication)getApplication()).getDaoSession().getStudentDao();
        // 清除单个Dao的identity scope ，这样某个dao不会有缓存对象返回
//        studentDao.detachAll();

        Student student = new Student();
        student.setName("alex");
        student.setAge(19);
        student.setGrades("B2");

        studentDao.insert(student);   //插入一条数据
        studentDao.insertOrReplace(student);   //增加一条数据，如果有这条数据则更新

        studentDao.delete(student);      //通过对象删除
        studentDao.deleteByKey(1L);   //通过id删除

        studentDao.update(student);

        List<Student> studentsAll = studentDao.loadAll();   //获取所有数据
        List<Student> students1 = studentDao.queryRaw("where age>?","18"); //直接写查询条件
        Student student2 = studentDao.loadByRowId(1);  //根据ID查询
        QueryBuilder builder = studentDao.queryBuilder();

        //通过QueryBuilder拼查询条件
        List<Student> list2 = builder
                .where(StudentDao.Properties.Age.gt(18))
                .build()
                .list();

        // 可以有多个查询条件，用逗号分隔开来
        // 查询name为Joe，且Age等于10的人
        List<Student> joes = builder
                .where(StudentDao.Properties.Name.eq("Joe"), StudentDao.Properties.Age.eq("10"))
                .orderAsc(StudentDao.Properties.Name)
                .list();

        // 多个条件查询
        // Name is "Joe" AND (Age is greater than 1970 OR (Grades is equals to B2 AND Id is >= 10))
        builder.where(StudentDao.Properties.Name.eq("Joe"),
                builder.or(StudentDao.Properties.Age.gt(1970),
                        builder.and(StudentDao.Properties.Grades.eq("B2"),
                                StudentDao.Properties.Id.ge(10))));
        List<Student> youngJoes = builder.list();

        /**
         * limit(int)限制查询返回的条数。
         * 当你根据条件查询，返回N条数据的时候，但是你只想要里面的前十个的时候，这个时候用limit可以
         * 限制获取n条数据，不用for循环cursor。
         */

        /**
         * offset(int)
         * 设置返回数据的起始偏移值，得到offset开始的n条数据。必须要结合limit（int n）来使用
         */
        builder.where(StudentDao.Properties.Id.gt(1)).offset(3).limit(10);

        /**
         * eq:等于
         * notEq:不等于
         * like:模糊查询,string要用夹在%key%中间
         */
        builder.where(StudentDao.Properties.Name.like("%doris%")).list();

        /**
         * BETWEEN ... AND ... ...和...之间。
         * IN（..., ..., ...） 在给出的value的范围内的符合项
         * gt 大于
         * ge 大于等于
         * lt 小于
         * le 小于等于
         * isNull 不是空的
         * 查询有懒查询，也叫延迟查询吧，我觉得，就会你先执行了查询语句但是呢只有在你用的时候才会加载到内存，就类似于懒加载。
         * list() 不是懒加载，正常查询，查询结果立马加载到内存。
         * listLazy() 实体按需加载到内存中，一旦列表中的元素第一次被访问，它将被加载并缓存以供将来使用，必须关闭。
         * listLazyUncached() 一个“虚拟”实体列表，对列表元素的任何访问都会导致从数据库加载其数据，必须关闭。
         * listIterator() 让我们通过按需加载数据（懒惰地）来遍历结果，数据没有被缓存，必须关闭。
         */
        /**************************** 原始查询 ********************************/
        //方法1：
        Query<Student> query = builder.where(
                new WhereCondition.StringCondition("_ID IN " + "(SELECT id FROM Student WHERE name = 'A')")
        ).build();
        //方法2：queryRaw 和queryRawCreate
        Query<Student> query2 = studentDao.queryRawCreate(
                ", GROUP G WHERE G.NAME=? AND T.GROUP_ID=G._ID", "admin"
        );

        /***************** debug查询:设置这两个属性就可以看到log *****************************/
        QueryBuilder.LOG_SQL = true;
        QueryBuilder.LOG_VALUES = true;
    }
}