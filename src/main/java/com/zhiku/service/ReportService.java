package com.zhiku.service;

import com.zhiku.entity.mysql.Report;
import com.zhiku.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public void add(Report report){
        reportMapper.insertSelective( report );
    }
}
