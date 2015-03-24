/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fontys.time;

/**
 *
 * @author Robert
 */
public class TimeSpan2 implements ITimeSpan {

    private ITime bt;
    private long length;
    
    public TimeSpan2(ITime bt, ITime et) {
        if (bt.compareTo(et) >= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + et);
        }
        this.bt = bt;
        this.length = this.bt.difference(et);
    }
    
    @Override
    public ITime getBeginTime() {
        return this.bt;
    }

    @Override
    public ITime getEndTime() {
        return this.bt.plus(this.length());
    }

    @Override
    public int length() {
        return (int)this.length;
    }

    @Override
    public void setBeginTime(ITime beginTime) {
        if (beginTime.compareTo(this.getEndTime()) >= 0) {
            throw new IllegalArgumentException("begin time "
                    + bt + " must be earlier than end time " + this.getEndTime());
        }
        this.bt = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        if (endTime.compareTo(bt) <= 0) {
            throw new IllegalArgumentException("end time "
                    + this.getEndTime() + " must be later then begin time " + bt);
        }
        this.length = this.bt.difference(endTime);
    }

    @Override
    public void move(int minutes) {
        this.bt = this.bt.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) {
        if (minutes <= 0) {
            throw new IllegalArgumentException("length of period must be positive");
        }
        this.length += minutes;
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        return (this.bt.compareTo(timeSpan.getBeginTime()) <= 0 && 
                this.getEndTime().compareTo(timeSpan.getBeginTime()) >= 0);
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        if (bt.compareTo(timeSpan.getEndTime()) > 0 || this.getEndTime().compareTo(timeSpan.getBeginTime()) < 0) {
            return null;
        }
        
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) <= 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }
        if (this.getEndTime().compareTo(timeSpan.getEndTime()) > 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }

        return new TimeSpan(begintime, endtime);
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        ITime begintime, endtime;
        if (bt.compareTo(timeSpan.getBeginTime()) > 0) {
            begintime = bt;
        } else {
            begintime = timeSpan.getBeginTime();
        }
        if (this.getEndTime().compareTo(timeSpan.getEndTime()) < 0) {
            endtime = this.getEndTime();
        } else {
            endtime = timeSpan.getEndTime();
        }
        if (begintime.compareTo(endtime) >= 0) {
            return null;
        }

        return new TimeSpan(begintime, endtime);
    }
    
}
