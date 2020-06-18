package utilites;

import java.sql.Blob;
import java.sql.Date;

public class Entry {
    private String fileId;
    private String authorName;
    private String authorLink;
    private Date msgDate;
    private Blob fileBlob;
    private String chatId;

    public Entry(String fileId, String authorName, String authorLink, Date msgDate, Blob fileBlob, String chatId) {
        this.fileId = fileId;
        this.authorName = authorName;
        this.authorLink = authorLink;
        this.msgDate = msgDate;
        this.fileBlob = fileBlob;
        this.chatId = chatId;
    }

    public Entry(){}

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLink() {
        return authorLink;
    }

    public void setAuthorLink(String authorLink) {
        this.authorLink = authorLink;
    }

    public Date getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(Date msgDate) {
        this.msgDate = msgDate;
    }

    public Blob getFileBlob() {
        return fileBlob;
    }

    public void setFileBlob(Blob fileBlob) {
        this.fileBlob = fileBlob;
    }

    @Override
    public String toString() {
        return "Entry{" +
                ", fileId='" + fileId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorLink='" + authorLink + '\'' +
                ", msgDate=" + msgDate +
                ", fileBlob=" + fileBlob +
                '}';
    }
}
