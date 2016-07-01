/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package leo.demo.democreate.utils;

import java.util.Date;

/**
 *
 * @author odzhara-ongom
 */
public class LongOperation {

    private Date createdTime = new Date();
    private Date startTime, endTime;
    private Long durationMs;

    private Exception exception;

    public Date getCreatedTime() {
        return createdTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public LongOperation sleep(long ms) {
        if (ms < 0) {
            return this;
        }
        try {
            startTime = new Date();
            Thread.sleep(ms);
            endTime = new Date();
            durationMs = endTime.getTime() - startTime.getTime();
            exception = null;
            return this;
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            exception = ex;
            return this;
        }
    }

}
