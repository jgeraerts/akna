package net.umask.akna.dto;

import java.io.Serializable;

/**
 * User: Me
 * Date: 30-dec-2009
 * Time: 21:17:56
 */
public class MaternalCaseDTO implements Serializable {
    private Long id;
    private String title;
    private String body;


    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
