package com.year2018.fw_greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;


/**
 * @Entity 将一个Java类转变成一个实体类，必须是java类，kotlin不支持。
 * 在Entity中我们可以配置许多信息，如：
 * nameInDb：声明了该表在数据库中的名；
 * indexes：用于建立索引，配合unique来标志一条数据的唯一性
 * schema：当你有多个schema，用这个属性来告诉数据库当前entity属于哪个schema
 * active：用于标志某个entity是否是active的，active为true的该实体类将生成有删改的方法，默认是false。
 * @Id 标志主键
 * @Transient 表示不存储在数据库中
 * @NotNull 标志这个字段不能是null
 * @Property 如果定义了这个属性，那么nameInDb的值就是该字段在数据表里面的名称
 * @Unique 用于标志列的值的唯一性
 */
@Entity(nameInDb = "user_info",
        indexes = {
                @Index(value = "name DESC", unique = true)
        })
public class User {
    /**
     * entity必须有一个long或者Long的属性作为主键，但是有时候我们的主键不一定是long或者Long型，可能是string之类的，
     * 这个时候就可以定义索引属性并且注明独一无二，如：
     *
     * @Index(name = "keyword", unique = true)  //name用于指明这个索引的列名，unique表示这个列是不可重复的
     * private String key;
     */
    @Id
    private Long id;

    @Property(nameInDb = "USERNAME")
    @NotNull
    private String name;

    @Transient
    private int tempUsageCount; // not persisted

    @Generated(hash = 1709734220)
    public User(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
