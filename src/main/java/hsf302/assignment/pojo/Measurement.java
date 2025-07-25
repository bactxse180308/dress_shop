package hsf302.assignment.pojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "measurements")
@Getter
@Setter
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double bustSize;
    private Double waistSize;
    private Double hipsSize;
    private Double buttSize;
    private Double skirtLength;
    private Double shoulderWidth;
    private Double armLength;
    private Double waistDrop;
    private Double hipDrop;
    private Double neckSize;

    @Column(length = 500, name = "note")
    private String note;

    // Manual getters and setters to avoid Lombok issues
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getBustSize() {
        return bustSize;
    }

    public void setBustSize(Double bustSize) {
        this.bustSize = bustSize;
    }

    public Double getWaistSize() {
        return waistSize;
    }

    public void setWaistSize(Double waistSize) {
        this.waistSize = waistSize;
    }

    public Double getHipsSize() {
        return hipsSize;
    }

    public void setHipsSize(Double hipsSize) {
        this.hipsSize = hipsSize;
    }

    public Double getButtSize() {
        return buttSize;
    }

    public void setButtSize(Double buttSize) {
        this.buttSize = buttSize;
    }

    public Double getSkirtLength() {
        return skirtLength;
    }

    public void setSkirtLength(Double skirtLength) {
        this.skirtLength = skirtLength;
    }

    public Double getShoulderWidth() {
        return shoulderWidth;
    }

    public void setShoulderWidth(Double shoulderWidth) {
        this.shoulderWidth = shoulderWidth;
    }

    public Double getArmLength() {
        return armLength;
    }

    public void setArmLength(Double armLength) {
        this.armLength = armLength;
    }

    public Double getWaistDrop() {
        return waistDrop;
    }

    public void setWaistDrop(Double waistDrop) {
        this.waistDrop = waistDrop;
    }

    public Double getHipDrop() {
        return hipDrop;
    }

    public void setHipDrop(Double hipDrop) {
        this.hipDrop = hipDrop;
    }

    public Double getNeckSize() {
        return neckSize;
    }

    public void setNeckSize(Double neckSize) {
        this.neckSize = neckSize;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
