package com.zhiku.entity.mongodb;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "myindex")
public class Index{
    @Indexed
    private Integer cid;
    @Field("vid")
    private String vid;
    @Field("catalog")
    private List<Child> catalog;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public List<Child> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Child> child) {
        this.catalog = child;
    }

}
