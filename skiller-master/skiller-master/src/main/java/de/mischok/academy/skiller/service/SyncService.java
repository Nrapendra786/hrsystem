package de.mischok.academy.skiller.service;

import de.mischok.academy.skiller.domain.VacationAndAbsence;
import de.mischok.academy.skiller.mappers.VacationAndAbsenceMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SyncService {

    @Autowired
    private PlanningSystem planningSystem;

    public void makeChanges(final List<HrSystemVacation> vacations) {
        // query the saved absences from planning system
        List<PlanningSystem.Absence> absences = planningSystem.getAllPlanningSystemAbsences();

        // query the persons from planning system
        List<PlanningSystem.Person> persons = planningSystem.getAllPlanningSystemPersons();

        // TODO: update planning system with vacation data from HR-system

        planningSystem.createBooking(vacations.get(0));

        List<VacationAndAbsence> absenceList = absences.
                stream().
                filter(absence -> absence.status.equals("Genehmigt")).
                map(absence -> {
                            VacationAndAbsence vacationAndAbsence = new VacationAndAbsence();
                            vacationAndAbsence.setVacationId(absence.getId());
                            vacationAndAbsence.setEmployeeId(absence.personId);
                            return vacationAndAbsence;
                        }
                ).collect(Collectors.toList());

        vacations.stream().
                forEach(vacation -> {
                    absenceList.stream().forEach(absence -> {
                        if(absence.getEmployeeId().equals(vacation.getEmployeeID())) {
                            planningSystem.updateBooking(vacation, absence.getVacationId());
                        }
                    });
                });
    }

    @Builder
    @Data
    public static class HrSystemVacation {
        LocalDate startDate;
        LocalDate endDate;
        String employeeID;
        String status;
    }
}
