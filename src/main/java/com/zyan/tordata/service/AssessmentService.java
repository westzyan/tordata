package com.zyan.tordata.service;

import com.zyan.tordata.dao.AssessmentDao;
import com.zyan.tordata.domain.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyan
 * @date 2021/6/8 下午7:59
 */
@Service
public class AssessmentService {
    @Autowired
    AssessmentDao assessmentDao;

    public List<Assessment> listAssessment(String condition){
        return assessmentDao.listAssessment(condition);
    }
}
