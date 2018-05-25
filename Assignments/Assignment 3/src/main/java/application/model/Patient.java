package application.model;

import application.utils.DateFormatter;
import org.json.simple.JSONObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "patients")
public class Patient {

    public static final String JSON_KEY_ID = "id";
    public static final String JSON_KEY_NAME = "name";
    public static final String JSON_KEY_ID_CARD_NUMBER = "id_card_number";
    public static final String JSON_KEY_CNP = "cnp";
    public static final String JSON_KEY_BIRTH_DATE = "birth_date";
    public static final String JSON_KEY_ADDRESS = "address";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "[A-Z]{2}[0-9]{6}")
    private String idCardNumber;

    @Column(nullable = false)
    @NotNull
    @Pattern(regexp = "[1-6][0-9]{12}")
    private String cnp;

    private Date birthDate;

    @Size(min = 3)
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public JSONObject toJSON() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put(JSON_KEY_ID, getId());
        jsonObject.put(JSON_KEY_NAME, getName());
        jsonObject.put(JSON_KEY_ID_CARD_NUMBER, getIdCardNumber());
        jsonObject.put(JSON_KEY_CNP, getCnp());
        jsonObject.put(JSON_KEY_BIRTH_DATE, getBirthDate().toString());
        jsonObject.put(JSON_KEY_ADDRESS, getAddress());

        return jsonObject;
    }

    public static Patient fromJSON(JSONObject jsonObject) {
        Patient patient = new Patient();

        patient.setId((Long) jsonObject.get(JSON_KEY_ID));
        patient.setName((String) jsonObject.get(JSON_KEY_NAME));
        patient.setIdCardNumber((String) jsonObject.get(JSON_KEY_ID_CARD_NUMBER));
        patient.setCnp((String) jsonObject.get(JSON_KEY_CNP));
        patient.setBirthDate(DateFormatter.dateFromString((String) jsonObject.get(JSON_KEY_BIRTH_DATE)));
        patient.setAddress((String) jsonObject.get(JSON_KEY_ADDRESS));

        return patient;
    }
}
