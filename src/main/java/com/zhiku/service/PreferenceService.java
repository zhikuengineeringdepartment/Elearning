package com.zhiku.service;

import com.zhiku.entity.mysql.PreferKey;
import com.zhiku.entity.mysql.Preference;
import com.zhiku.mapper.PreferMapper;
import com.zhiku.mapper.PreferenceMapper;
import com.zhiku.view.PreferView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class PreferenceService {
    @Autowired
    private PreferenceMapper preferenceMapper;
    @Autowired
    private PreferMapper preferMapper;

    public List<Preference> getAllPreference(){
        return preferenceMapper.selectAllPreferences();
    }

    public PreferView getPrfByUid(int uid){
        return preferMapper.selectPreferViewByUid(uid);
    }

    public void addPrefers(int uid,List<Preference> preferences){
        Iterator<Preference> preferenceIterator = preferences.iterator();
        Preference preference = null;
        PreferKey preferKey = new PreferKey();
        preferKey.setPreferUser(uid);
        while(preferenceIterator.hasNext()){
            preference = preferenceIterator.next();
            preferKey.setPreferPrf(preference.getPrfid());
            preferMapper.insert(preferKey);
        }
    }
    public void removePrefers(int uid,List<Preference> preferences){
        Iterator<Preference> preferenceIterator = preferences.iterator();
        Preference preference = null;
        PreferKey preferKey = new PreferKey();
        preferKey.setPreferUser(uid);
        while(preferenceIterator.hasNext()){
            preference = preferenceIterator.next();
            preferKey.setPreferPrf(preference.getPrfid());
            preferMapper.deleteByPrimaryKey(preferKey);
        }
    }
}
