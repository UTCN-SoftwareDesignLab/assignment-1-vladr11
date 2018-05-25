package application.controller;

import application.Constants;
import application.model.Consultation;
import application.model.Patient;
import application.model.User;
import application.service.IConsultationService;
import application.service.IPatientService;
import application.service.IUserService;
import application.utils.DateFormatter;
import application.utils.JSONError;
import application.websocket.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
public class ConsultationController {

    private IConsultationService consultationService;
    private IUserService userService;
    private IPatientService patientService;

    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ConsultationController(IConsultationService consultationService, IUserService userService, IPatientService patientService, SimpMessagingTemplate messagingTemplate) {
        this.consultationService = consultationService;
        this.userService = userService;
        this.patientService = patientService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(path = "/consultations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getConsultations(@RequestParam(value = "doctor", required = false) Long doctorId,
                                           @RequestParam(value = "patient", required = false) Long patientId) {
        List<Consultation> consultations;
        Patient patient = null;
        User doctor = null;

        if (doctorId != null) {
            doctor = userService.findById(doctorId);
            if (doctor == null || !doctor.getRole().equals(Constants.Roles.DOCTOR)) {
                JSONObject errorJSON = JSONError.jsonErrorWithMessage("Doctor not found.");
                return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
            }
        }

        if (patientId != null) {
            patient = patientService.findById(patientId);
            if (patient == null) {
                JSONObject errorJSON = JSONError.jsonErrorWithMessage("Patient not found.");
                return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
            }
        }

        if (patient != null && doctor != null) {
            consultations = consultationService.findByPatientAndDoctor(patient, doctor);
        } else if (patient != null) {
            consultations = consultationService.findByPatient(patient);
        } else if (doctor != null) {
            consultations = consultationService.findByDoctor(doctor);
        } else {
            consultations = consultationService.findAll();
        }

        JSONArray jsonArray = new JSONArray();
        for (Consultation consultation: consultations) {
            JSONObject consultationJSON = consultation.toJSON();
            jsonArray.add(consultationJSON);
        }

        JSONObject result = new JSONObject();
        result.put("consultations", jsonArray);

        return new ResponseEntity(result.toJSONString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/consultations", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createConsultation(@RequestBody String jsonString) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid JSON.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        Consultation consultation = new Consultation();
        User doctor;
        Patient patient;

        Long doctorId = (Long) jsonObject.get(Consultation.JSON_KEY_DOCTOR);
        Long patientId = (Long) jsonObject.get(Consultation.JSON_KEY_PATIENT);
        String description = (String) jsonObject.get(Consultation.JSON_KEY_DESCRIPTION);
        String dateString = (String) jsonObject.get(Consultation.JSON_KEY_DATE);
        Date date = DateFormatter.dateFromString(dateString);

        if (doctorId == null || patientId == null || description == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid consultation object.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        doctor = userService.findById(doctorId);
        if (doctor == null || !doctor.getRole().equals(Constants.Roles.DOCTOR)) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Doctor not found");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        patient = patientService.findById(patientId);
        if (patient == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Patient not found");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        consultation.setDescription(description);
        consultation.setDoctor(doctor);
        consultation.setPatient(patient);
        consultation.setDate(date);

        consultation = consultationService.create(consultation);
        return new ResponseEntity(consultation.toJSON().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/consultations/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateConsultation(@PathVariable("id") Long id, @RequestBody String jsonString) {
        Consultation consultation = consultationService.findById(id);
        if (consultation == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Consultation not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;
        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Invalid JSON.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.BAD_REQUEST);
        }

        Long doctorId = (Long) jsonObject.get(Consultation.JSON_KEY_DOCTOR);
        Long patientId = (Long) jsonObject.get(Consultation.JSON_KEY_PATIENT);
        String description = (String) jsonObject.get(Consultation.JSON_KEY_DESCRIPTION);
        String dateString = (String) jsonObject.get(Consultation.JSON_KEY_DATE);

        if (doctorId != null) {
            User doctor = userService.findById(doctorId);
            if (doctor == null || !doctor.getRole().equals(Constants.Roles.DOCTOR)) {
                JSONObject errorJSON = JSONError.jsonErrorWithMessage("Doctor not found");
                return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
            }
            consultation.setDoctor(doctor);
        }

        if (patientId != null) {
            Patient patient = patientService.findById(patientId);
            if (patient == null) {
                JSONObject errorJSON = JSONError.jsonErrorWithMessage("Patient not found");
                return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
            }
            consultation.setPatient(patient);
        }

        if (description != null) {
            consultation.setDescription(description);
        }

        if (dateString != null) {
            Date date = DateFormatter.dateFromString(dateString);
            consultation.setDate(date);
        }

        consultation = consultationService.update(consultation);
        return new ResponseEntity(consultation.toJSON().toJSONString(), HttpStatus.CREATED);
    }

    @RequestMapping(path = "/consultations/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteConsultation(@PathVariable("id") Long id) {
        Consultation consultation = consultationService.findById(id);
        if (consultation == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Consultation not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        consultationService.delete(consultation);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(path = "/consultations/{id}/arrived", method = RequestMethod.POST)
    public ResponseEntity patientArrived(@PathVariable("id") Long id) {
        Consultation consultation = consultationService.findById(id);
        if (consultation == null) {
            JSONObject errorJSON = JSONError.jsonErrorWithMessage("Consultation not found.");
            return new ResponseEntity(errorJSON.toJSONString(), HttpStatus.NOT_FOUND);
        }

        Message message = new Message("Patient " + consultation.getPatient().getName() + " has arrived.");
        System.out.println("Send to doctor: " + consultation.getDoctor().getUsername());
        messagingTemplate.convertAndSendToUser(consultation.getDoctor().getUsername(), "/queue", message);

        return new ResponseEntity(HttpStatus.OK);
    }
}
