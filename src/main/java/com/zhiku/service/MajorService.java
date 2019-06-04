package com.zhiku.service;

import com.zhiku.entity.Major;
import com.zhiku.mapper.MajorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MajorService {
    @Autowired
    private MajorMapper majorMapper;

    @Deprecated
    public List<Major> getAllMajors(){
        return majorMapper.selectAllMajors();
    }
}
