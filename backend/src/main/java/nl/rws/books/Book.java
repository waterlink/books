package nl.rws.books;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String title;
    private String status;

    @JsonSerialize(using = MemberToIdSerializer.class)
    private Member borrowedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Book() {
        this.status = "available";
    }

    public Book(String title) {
        this();
        this.title = title;
    }

    public Book(String id, String title, String status, Member borrowedBy) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.borrowedBy = borrowedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member getBorrowedBy() {
        return borrowedBy;
    }

    public void setBorrowedBy(Member borrowedBy) {
        this.borrowedBy = borrowedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (id != null ? !id.equals(book.id) : book.id != null) return false;
        if (!title.equals(book.title)) return false;
        if (!status.equals(book.status)) return false;
        return borrowedBy != null ? borrowedBy.equals(book.borrowedBy) : book.borrowedBy == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + (borrowedBy != null ? borrowedBy.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", borrowedBy=" + borrowedBy +
                '}';
    }
}
