package com.restful.daily_click.repository;

import com.alibaba.fastjson.JSONObject;
import com.restful.daily_click.entity.StudentsEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface StudentsRepository extends JpaRepository<StudentsEntity,Integer> {
    StudentsEntity findByStuCode(String stu_code);

    @Query(value = "select stu_code,name,sex,class_name,tele,email from students,class_info where students.grade = class_info.class_id " +
            "and stu_code = ?1",nativeQuery = true)
    JSONObject findStuInfoByStuCode(String stu_code);


    @Transactional
    @Modifying
    //----------------添加学生信息记录----------------
    @Query(value = "insert into students(stu_code,name,sex,grade,tele,email) values(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    int addStudent(String stu_code, String name, String sex, String grade, String tele, String email);

    @Query(value = "select * from students where grade like %?1%",nativeQuery = true)
    ArrayList<StudentsEntity> findAllByGrade(String grade);

    @Query(value = "select * from class_majority",nativeQuery = true)
    List<JSONObject> getAllMajority(Pageable pageable);

    @Query(value = "select count(*) from class_majority",nativeQuery = true)
    int getAllMajorityNum();


    @Transactional
    @Modifying

    @Query(value = "DELETE from class_info where id = ?1",nativeQuery = true)
    int delClassInf(String id);

    //-------------根据班级ID获取学生信息--------------
    @Query(value = "select * from students where grade like %?1%",nativeQuery = true)
    List<JSONObject> getAllStudentByClassId(Pageable pageable, String class_id);

    @Query(value = "select count(*) from students where grade like %?1%",nativeQuery = true)
    int getAllStudentByClassIdNum(String class_id);

    @Transactional
    @Modifying
    //---------删除学生记录（根据学生账户）---------------
    @Query(value = "DELETE from students where stu_code = ?1",nativeQuery = true)
    int delStudentInf(String stu_code);

    @Transactional
    @Modifying
    @Query(value = "UPDATE students set stu_code=?1, email=?2, name=?3, sex=?4, tele=?5, grade=?6 where id = ?7",nativeQuery = true)
    int editStudent(String stu_code, String email, String name, String sex, String tele, String grade, String id);

    //----根据学生code查询----------
    @Query(value = "select grade from students where stu_code = ?1",nativeQuery = true)
    String getStuCode(String stu_code);

    @Query(value = "select * from students where students.stu_code = ?1",nativeQuery = true)
    StudentsEntity getStuInfonById(String stu_code);

    @Query(value = "select * from students where students.name like %?1%",nativeQuery = true)
    List<JSONObject> searchStudentByName(String name);
}
