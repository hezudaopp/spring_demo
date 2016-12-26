package spittr.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

/**
 * Created by 273cn on 16/12/14.
 */
@Entity
public class Spittle {
    @Id
    @Null
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long spitterId;

    @NotNull
    @Size(min = 10, max = 2000)
    private String message;

    private Long postedTime;

    @Min(-180)
    @Max(180)
    private Double longitude;

    @Min(-90)
    @Max(90)
    private Double latitude;

    public Spittle() {}

    public Spittle(Long spitterId, String message, Long postedTime, Double latitude, Double longitude) {
        this.id = null;
        this.spitterId = spitterId;
        this.message = message;
        this.postedTime = postedTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Spittle(Long spitterId, String message, Long postedTime) {
        this(spitterId, message, postedTime, null, null);
    }

    public void setId(Long id) {this.id = id;};

    public Long getId() {
        return id;
    }

    public void setSpitterId(Long spitterId) {this.spitterId = spitterId;}

    public Long getSpitterId() {
        return spitterId;
    }

    public void setMessage(String message) {this.message = message;}

    public String getMessage() {
        return message;
    }

    public void setPostedTime(Long postedTime) {this.postedTime = postedTime;}

    public Long getPostedTime() {
        return postedTime;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object that) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        Spittle spittle = (Spittle) o;
//
//        if (!id.equals(spittle.id)) return false;
//        return time.equals(spittle.time);
        return EqualsBuilder.reflectionEquals(this, that, "id", "time");
    }

    @Override
    public int hashCode() {
//        int result = id.hashCode();
//        result = 31 * result + time.hashCode();
//        return result;
        return HashCodeBuilder.reflectionHashCode(this, "id", "time");
    }
}
