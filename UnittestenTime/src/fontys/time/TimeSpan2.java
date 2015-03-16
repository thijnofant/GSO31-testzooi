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
        if (bt.compareTo(et) <= 0) {
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
        this.bt = beginTime;
    }

    @Override
    public void setEndTime(ITime endTime) {
        this.length = this.bt.difference(endTime);
    }

    @Override
    public void move(int minutes) {
        this.bt.plus(minutes);
    }

    @Override
    public void changeLengthWith(int minutes) {
        this.length += minutes;
    }

    @Override
    public boolean isPartOf(ITimeSpan timeSpan) {
        if (this.bt.compareTo(timeSpan.getBeginTime()) <= 0) {
            return false;
        }
        //if (this.bt.)
        return false;
    }

    @Override
    public ITimeSpan unionWith(ITimeSpan timeSpan) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ITimeSpan intersectionWith(ITimeSpan timeSpan) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
