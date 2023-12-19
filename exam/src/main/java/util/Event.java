package util;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
    private String name;
    private Date startDate;
    private Date endDate;
    private String category;
    private String description;
    private String repeat;

    public Event(String name, Date startDate, Date endDate, String category, String description, String repeat) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
        this.description = description;
        this.repeat = repeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
