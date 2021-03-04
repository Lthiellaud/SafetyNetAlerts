package com.safetynet.safetynetalerts.service;

import com.safetynet.safetynetalerts.model.MedicalRecord;
import com.safetynet.safetynetalerts.model.PersonId;
import com.safetynet.safetynetalerts.repository.MedicalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static java.util.Calendar.JANUARY;
import static java.util.Calendar.MARCH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setMaxElementsForPrinting;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = "command.line.runner.enabled=false")
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private static MedicalRecord medicalRecord1;
    private static MedicalRecord medicalRecord2;
    private static MedicalRecord medicalRecord2Update;
    private static MedicalRecord medicalRecord2FalseUpdate;
    private static PersonId personId;

    private Calendar calendar = Calendar.getInstance();

    @BeforeEach
    public void initDtoTest(){
        medicalRecord1 = new MedicalRecord();
        medicalRecord2 = new MedicalRecord();
        medicalRecord2Update = new MedicalRecord();
        medicalRecord2FalseUpdate = new MedicalRecord();
        personId = new PersonId("Dad", "Family12");

        calendar.set(2021, JANUARY, 17);
        medicalRecord1.setFirstName("Baby");
        medicalRecord1.setLastName("Family12");
        medicalRecord1.setBirthdate(calendar.getTime());
        medicalRecord1.setMedications(new ArrayList<>());
        medicalRecord1.setAllergies(new ArrayList<>());

        calendar.set(1985, MARCH, 17);
        medicalRecord2.setFirstName("Dad");
        medicalRecord2.setLastName("Family12");
        medicalRecord2.setBirthdate(calendar.getTime());
        medicalRecord2.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord2.setAllergies(Arrays.asList("allergie2"));

        calendar.set(1985, MARCH, 18);
        medicalRecord2Update.setFirstName("Dad");
        medicalRecord2Update.setLastName("Family12");
        medicalRecord2Update.setBirthdate(calendar.getTime());
        medicalRecord2.setAllergies(Arrays.asList("allergieUpdate", "allergie2"));

        medicalRecord2FalseUpdate.setFirstName("Dad");
        medicalRecord2FalseUpdate.setLastName("Family12");
        medicalRecord2FalseUpdate.setMedications(Arrays.asList("med1", "med2"));
        medicalRecord2FalseUpdate.setAllergies(Arrays.asList("allergie2"));

    }

    @Test
    public void updateMedicalRecordTest() {
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(medicalRecord2);
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(any(MedicalRecord.class));

        //WHEN - all attributes of medicalRecord2Update, except firstname and lastname have changed
        Optional<MedicalRecord> medicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord2Update);

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
        assertThat(medicalRecord.isPresent()).isTrue();
        assertThat(medicalRecord.get().equals(medicalRecord2Update)).isTrue();
    }

    @Test
    public void falseUpdateMedicalRecordTest() {
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(medicalRecord2);
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);
        when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(any(MedicalRecord.class));

        //WHEN - all attributes of medicalRecord2FalseUpdate are null
        // except firstname and lastname, medications and allergies which are equal to those of person2
        Optional<MedicalRecord> medicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord2FalseUpdate);

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository, times(1)).save(any(MedicalRecord.class));
        assertThat(medicalRecord.isPresent()).isTrue();
        assertThat(medicalRecord.get().equals(medicalRecord2)).isTrue();

    }

    @Test
    public void tryToUpdateMedicalRecordWithNoNameTest() {
        //WHEN
        Optional<MedicalRecord> medicalRecord = medicalRecordService.updateMedicalRecord(new MedicalRecord());

        //THEN
        verify(medicalRecordRepository, times(0)).findById(personId);
        verify(medicalRecordRepository, times(0)).save(any(MedicalRecord.class));
        assertThat(medicalRecord.isPresent()).isFalse();

    }

    @Test
    public void updateNonExistingMedicalRecordTest() {
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.empty();
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);

        //WHEN
        Optional<MedicalRecord> medicalRecord = medicalRecordService.updateMedicalRecord(medicalRecord2Update);

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository, times(0)).save(any(MedicalRecord.class));
        assertThat(medicalRecord.isPresent()).isFalse();

    }

    @Test
    public void saveExistingMedicalRecordTest() {
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(medicalRecord2);
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);

        //WHEN
        Optional<MedicalRecord> result = medicalRecordService.createMedicalRecord(medicalRecord2);

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository, times(0)).save(any(MedicalRecord.class));
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void tryToSaveMedicalRecordWithNoNameTest() {
        //WHEN
        Optional<MedicalRecord> result = medicalRecordService.createMedicalRecord(new MedicalRecord());

        //THEN
        verify(medicalRecordRepository, times(0)).findById(any(PersonId.class));
        verify(medicalRecordRepository, times(0)).save(any(MedicalRecord.class));
        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void saveMedicalRecordTest() {
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.empty();
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);
        when(medicalRecordRepository.save(medicalRecord2)).thenReturn(medicalRecord2);

        //WHEN
        Optional<MedicalRecord> result = medicalRecordService.createMedicalRecord(medicalRecord2);

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository, times(1)).save(medicalRecord2);
        assertThat(result).isEqualTo(Optional.of(medicalRecord2));
    }

    @Test
    public void getMedicalRecordIdTest(){
        //GIVEN
        Optional<MedicalRecord> optionalMedicalRecord = Optional.of(medicalRecord2);
        when(medicalRecordRepository.findById(personId)).thenReturn(optionalMedicalRecord);

        //THEN
        assertThat(medicalRecordService.getMedicalRecord(personId).isPresent()).isTrue();
        verify(medicalRecordRepository,times(1)).findById(personId);
    }

    @Test
    public void saveMedicalRecordListTest(){
        //GIVEN
        List<MedicalRecord> medicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);
        when(medicalRecordRepository.saveAll(medicalRecords)).thenReturn(medicalRecords);

        //THEN
        assertThat(medicalRecordService.saveMedicalRecordList(medicalRecords)).containsExactly(medicalRecord1, medicalRecord2);
        verify(medicalRecordRepository,times(1)).saveAll(medicalRecords);
    }

    @Test
    public void deleteMedicalRecordTest(){
        //GIVEN
        when(medicalRecordRepository.findById(personId)).thenReturn(Optional.of(medicalRecord2));

        //WHEN
        medicalRecordService.deleteMedicalRecord("Dad", "Family12");

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository,times(1)).deleteById(personId);
    }

    @Test
    public void deleteMedicalRecordForNonExistingMedicalRecordTest(){
        //GIVEN
        when(medicalRecordRepository.findById(personId)).thenReturn(Optional.empty());

        //WHEN
        medicalRecordService.deleteMedicalRecord("Dad", "Family12");

        //THEN
        verify(medicalRecordRepository, times(1)).findById(personId);
        verify(medicalRecordRepository,times(0)).deleteById(any(PersonId.class));
    }

    @Test
    public void deleteMedicalRecordWithNullPersonIdTest(){
        //WHEN
        medicalRecordService.deleteMedicalRecord(null, null);

        //THEN
        verify(medicalRecordRepository,times(0)).findById(any(PersonId.class));
        verify(medicalRecordRepository,times(0)).deleteById(any(PersonId.class));
    }

}
