package net.umask.akna.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Me
 * Date: 21-nov-2009
 * Time: 9:43:03
 */

@Entity
public class MaternalCase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String title;
    @Column(columnDefinition = "TEXT")
    private String body;

    @OneToMany(mappedBy = "maternalCase", cascade = CascadeType.ALL)
    List<OrderedCaseQuestionAssociation> associations;

    public MaternalCase(String title, String body) {
        this.title = title;
        this.body = body;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "MaternalCase{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id +
                '}';
    }

    public void updateTitleAndBody(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public MaternalCase() {
    }
}
