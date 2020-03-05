package com.zhiku.entity.mysql;

import javax.lang.model.element.NestingKind;
import java.util.Date;

public class File {
    public static String STATEON="n";
    public static String STATEOFF="u";

    private Integer fid;
    
    private String filePath;

    private String fileName;

    private Integer fileCourse;

    private String fileTeacher;

    private String fileType;

    private Integer fileUpper;

    private Date fileUploadTime;

    private Integer fileDownloadCount;

    private String fileDesc;

    private String fileStatus;

    private Float fileScore;

    private String fileSha;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Integer getFileCourse() {
        return fileCourse;
    }

    public void setFileCourse(Integer fileCourse) {
        this.fileCourse = fileCourse;
    }

    public String getFileTeacher() {
        return fileTeacher;
    }

    public void setFileTeacher(String fileTeacher) {
        this.fileTeacher = fileTeacher == null ? null : fileTeacher.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Integer getFileUpper() {
        return fileUpper;
    }

    public void setFileUpper(Integer fileUpper) {
        this.fileUpper = fileUpper;
    }

    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }

    public Integer getFileDownloadCount() {
        return fileDownloadCount;
    }

    public void setFileDownloadCount(Integer fileDownloadCount) {
        this.fileDownloadCount = fileDownloadCount;
    }

    public String getFileDesc() {
        return fileDesc;
    }

    public void setFileDesc(String fileDesc) {
        this.fileDesc = fileDesc == null ? null : fileDesc.trim();
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(String fileStatus) {
        this.fileStatus = fileStatus == null ? null : fileStatus.trim();
    }

    public Float getFileScore() {
        return fileScore;
    }

    public void setFileScore(Float fileScore) {
        this.fileScore = fileScore;
    }

    public String getFileSha() {
        return fileSha;
    }

    public void setFileSha(String fileSha) {
        this.fileSha = fileSha == null ? null : fileSha.trim();
    }

    @Override
    public String toString() {
        return "File{" +
                "fid=" + fid +
                ", filePath='" + filePath + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileCourse=" + fileCourse +
                ", fileTeacher='" + fileTeacher + '\'' +
                ", fileType='" + fileType + '\'' +
                ", fileUpper=" + fileUpper +
                ", fileUploadTime=" + fileUploadTime +
                ", fileDownloadCount=" + fileDownloadCount +
                ", fileDesc='" + fileDesc + '\'' +
                ", fileStatus='" + fileStatus + '\'' +
                ", fileScore=" + fileScore +
                ", fileSha='" + fileSha + '\'' +
                '}';
    }
}