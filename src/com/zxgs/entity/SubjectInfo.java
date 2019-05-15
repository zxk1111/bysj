package com.zxgs.entity;

public class SubjectInfo {
    private Integer id;

    private String subjectName;

    private Integer subjectChapter;

    private Integer teacherId;

    private Integer level;

    private String mainCodeUrl;

    private String subjectContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName == null ? null : subjectName.trim();
    }

    public Integer getSubjectChapter() {
        return subjectChapter;
    }

    public void setSubjectChapter(Integer subjectChapter) {
        this.subjectChapter = subjectChapter;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getMainCodeUrl() {
        return mainCodeUrl;
    }

    public void setMainCodeUrl(String mainCodeUrl) {
        this.mainCodeUrl = mainCodeUrl == null ? null : mainCodeUrl.trim();
    }

    public String getSubjectContent() {
        return subjectContent;
    }

    public void setSubjectContent(String subjectContent) {
        this.subjectContent = subjectContent == null ? null : subjectContent.trim();
    }
}