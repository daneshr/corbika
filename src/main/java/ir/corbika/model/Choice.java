package ir.corbika.model;

import javax.persistence.*;

@Entity(name = "Choice")
@Table(name = "choice")
public class Choice {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "choice")
    private String choice;

    @Column(name = "value")
    private Long value;

    public Choice() {
    }

    public Choice(Long value, String choice) {
        this.choice = choice;
        this.value = value;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
