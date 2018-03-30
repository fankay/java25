package com.kaishengit.service;

import com.kaishengit.dao.StudentDao;

public class StudentService {

    private StudentDao studentDao;

    private StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void save() {
        studentDao.save();
    }

    /*public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }*/
}
