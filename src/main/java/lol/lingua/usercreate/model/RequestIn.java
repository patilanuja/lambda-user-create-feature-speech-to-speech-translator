package lol.lingua.usercreate.model;

import java.util.Objects;

public class RequestIn {

    private String uid;
    private String name;
    private String language;

    public RequestIn() {
    }

    public RequestIn(String uid, String name, String language) {
        this.uid = uid;
        this.name = name;
        this.language = language;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestIn requestIn = (RequestIn) o;
        return uid.equals(requestIn.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

    @Override
    public String toString() {
        return "Request{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

}
