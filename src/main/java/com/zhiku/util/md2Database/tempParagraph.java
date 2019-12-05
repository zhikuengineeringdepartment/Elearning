package com.zhiku.util.md2Database;

public class tempParagraph {
    public int id;
    private char type;
    private String content;
    public tempParagraph(int id,char type,String content){
        this.id=id;
        this.content=content;
        this.type=type;
    }
    public char getType(){
        return type;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String str){
        this.content=str;
    }
}
