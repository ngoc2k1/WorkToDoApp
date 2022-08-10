package com.bichngoc.worktodoapplication;

public class CongViec {
    private int IdCV;
    private String TenCV;

    public CongViec(String tenCV) {
        TenCV = tenCV;
    }

    public CongViec(int idCV, String tenCV) {
        IdCV = idCV;
        TenCV = tenCV;
    }

    public int getIdCV() {
        return IdCV;
    }

    public void setIdCV(int idCV) {
        IdCV = idCV;
    }

    public String getTenCV() {
        return TenCV;
    }

    public void setTenCV(String tenCV) {
        TenCV = tenCV;
    }
}
