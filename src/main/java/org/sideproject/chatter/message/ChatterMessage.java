package org.sideproject.chatter.message;

import java.util.Date;

public class ChatterMessage {

    private String content;
    private Date timestamp;
    private String des_Name;

	public ChatterMessage() {
    }

    public ChatterMessage(String content, Date timestamp, String des_Name) {
        this.content = content;
        this.timestamp = timestamp;
        this.des_Name = des_Name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getDes_Name() {
		return des_Name;
	}

	public void setDes_Name(String des_Name) {
		this.des_Name = des_Name;
	}
	
    @Override
    public String toString() {
        return "Order{" +
                "content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}