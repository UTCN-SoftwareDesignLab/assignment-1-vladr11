package application.controller;

import application.model.Patient;
import application.service.IPatientService;
import application.utils.DateFormatter;
import application.utils.JSONError;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @RequestMapping(path = "/patients", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPatients() {
        List<Patient> patients = patientService.findAll();

        JSONArray jsonArray = new JSONArray();

        for (Patient patient: patients) {
            JSONObject patientJSON = patient.toJSON();
            jsonArray.add(patientJSON);
        }

        JSONObject result = new JSONObject();
        result.put("patients", jsonArray);

        return new ResponseEntity(result.toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/patients", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPatient(@RequestBody String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid JSON.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        String name = (String) jsonObject.get(Patient.JSON_KEY_NAME);
        String idCardNo = (String) jsonObject.get(Patient.JSON_KEY_ID_CARD_NUMBER);
        String cnp = (String) jsonObject.get(Patient.JSON_KEY_CNP);
        String address = (String) jsonObject.get(Patient.JSON_KEY_ADDRESS);
        String birthDateString = (String) jsonObject.get(Patient.JSON_KEY_BIRTH_DATE);
        Date birthDate = DateFormatter.dateFromString(birthDateString);

        Patient patient = new Patient();
        patient.setName(name);
        patient.setIdCardNumber(idCardNo);
        patient.setCnp(cnp);
        patient.setAddress(address);
        patient.setBirthDate(birthDate);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Patient>> constraintViolations = validator.validate(patient);
        if (constraintViolations.size() > 0) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid patient data.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        patient = patientService.create(patient);

        return new ResponseEntity(patient.toJSON().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/patients/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePatient(@PathVariable("id") Long id, @RequestBody String jsonString) {
        Patient patient = patientService.findById(id);
        if (patient == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Patient not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            e.printStackTrace();
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid JSON.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        String name = (String) jsonObject.get(Patient.JSON_KEY_NAME);
        if (name != null) {
            patient.setName(name);
        }

        String dateString = (String) jsonObject.get(Patient.JSON_KEY_BIRTH_DATE);
        if (dateString != null) {
            Date date = DateFormatter.dateFromString(dateString);
            if (date != null) {
                patient.setBirthDate(date);
            }
        }

        String idCardNumber = (String) jsonObject.get(Patient.JSON_KEY_ID_CARD_NUMBER);
        if (idCardNumber != null) {
            patient.setIdCardNumber(idCardNumber);
        }

        String cnp = (String) jsonObject.get(Patient.JSON_KEY_CNP);
        if (cnp != null) {
            patient.setCnp(cnp);
        }

        String address = (String) jsonObject.get(Patient.JSON_KEY_ADDRESS);
        if (address != null) {
            patient.setAddress(address);
        }

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Patient>> constraintViolations = validator.validate(patient);
        if (constraintViolations.size() > 0) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid patient data.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        patient = patientService.update(patient);

        return new ResponseEntity(patient.toJSON().toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/patients/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deletePatient(@PathVariable("id") Long id) {
        Patient patient = patientService.findById(id);
        if (patient == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Patient not found");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        patientService.delete(patient);
        return new ResponseEntity(HttpStatus.OK);
    }
}
