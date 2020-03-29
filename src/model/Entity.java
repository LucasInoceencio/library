package model;

import java.util.Date;

public abstract class Entity {

    private int id;
    private Date dateHourInclusion;
    private Date dateHourChange;
    private Date dateHourDeletion;
    private User userWhoIncluded;
    private User userWhoChanged;
    private User userWhoDeleted;
    private boolean excluded;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateHourInclusion() {
        return dateHourInclusion;
    }

    public void setDateHourInclusion(Date dateHourInclusion) {
        this.dateHourInclusion = dateHourInclusion;
    }

    public Date getDateHourChange() {
        return dateHourChange;
    }

    public void setDateHourChange(Date dateHourChange) {
        this.dateHourChange = dateHourChange;
    }

    public Date getDateHourDeletion() {
        return dateHourDeletion;
    }

    public void setDateHourDeletion(Date dateHourDeletion) {
        this.dateHourDeletion = dateHourDeletion;
    }

    public User getUserWhoIncluded() {
        return userWhoIncluded;
    }

    public void setUserWhoIncluded(User userWhoIncluded) {
        this.userWhoIncluded = userWhoIncluded;
    }

    public User getUserWhoChanged() {
        return userWhoChanged;
    }

    public void setUserWhoChanged(User userWhoChanged) {
        this.userWhoChanged = userWhoChanged;
    }

    public User getUserWhoDeleted() {
        return userWhoDeleted;
    }

    public void setUserWhoDeleted(User userWhoDeleted) {
        this.userWhoDeleted = userWhoDeleted;
    }

    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }

}
