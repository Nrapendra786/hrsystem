package de.mischok.academy.skiller.domain;

import java.util.Objects;

public class VacationAndAbsence {

    private String employeeId;
    private String vacationId;


    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getVacationId() {
        return vacationId;
    }

    public void setVacationId(String vacationId) {
        this.vacationId = vacationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VacationAndAbsence that = (VacationAndAbsence) o;
        return employeeId.equals(that.employeeId) && vacationId.equals(that.vacationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, vacationId);
    }
}
