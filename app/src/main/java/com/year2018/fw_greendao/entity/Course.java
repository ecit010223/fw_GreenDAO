package com.year2018.fw_greendao.entity;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Course {
    @Id(autoincrement = true)
    Long id;

    Long studentId;
    
    String cName;

    @Generated(hash = 718974562)
    public Course(Long id, Long studentId, String cName) {
        this.id = id;
        this.studentId = studentId;
        this.cName = cName;
    }

    @Generated(hash = 1355838961)
    public Course() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCName() {
        return this.cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }
}
