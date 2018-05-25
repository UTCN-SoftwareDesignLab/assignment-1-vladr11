package application.model;

import application.utils.DateFormatter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "consultations")
public class Consultation {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_DESCRIPTION = "description";
    public static final String JSON_KEY_DOCTOR = "doctor";
    public static final String JSON_KEY_PATIENT = "patient";
    public static final String JSON_KEY_DATE = "date";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 5)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @NotNull
    private Patient patient;

    @NotNull
    private Date date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_DESCRIPTION, getDescription());
        jsonObject.put(JSON_KEY_DOCTOR, getDoctor().toJSON());
        jsonObject.put(JSON_KEY_PATIENT, getPatient().toJSON());
        jsonObject.put(JSON_KEY_DATE, DateFormatter.stringFromDate(getDate()));

        return jsonObject;
    }

    public static Consultation fromJSON(JSONObject jsonObject) {
        Consultation consultation = new Consultation();

        consultation.setId((Long) jsonObject.get(JSON_KEY_ID));
        consultation.setDescription((String) jsonObject.get(JSON_KEY_DESCRIPTION));
        consultation.setDoctor(User.fromJSON((JSONObject) jsonObject.get(JSON_KEY_DOCTOR)));
        consultation.setPatient(Patient.fromJSON((JSONObject) jsonObject.get(JSON_KEY_PATIENT)));
        consultation.setDate(DateFormatter.dateFromString((String) jsonObject.get(JSON_KEY_DATE)));

        return consultation;
    }
}
