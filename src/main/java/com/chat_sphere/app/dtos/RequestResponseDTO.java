package com.chat_sphere.app.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


public class RequestResponseDTO implements Serializable {
    private final static long serialVersionUID = 1L;

    private int pageNo;
    private int numberOfRecords;
    private boolean fullData;

    public int getPageNo() {
        return pageNo > 0 ? pageNo - 1 : pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(int numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public boolean isFullData() {
        return fullData;
    }

    public void setFullData(boolean fullData) {
        this.fullData = fullData;
    }
}
