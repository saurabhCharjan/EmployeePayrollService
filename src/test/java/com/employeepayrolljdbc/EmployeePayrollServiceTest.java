package com.employeepayrolljdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class EmployeePayrollServiceTest {
    @Test
    public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> databaseEmployeePayrolls = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        System.out.println(databaseEmployeePayrolls);
        Assertions.assertEquals(3,databaseEmployeePayrolls.size());
    }

    @Test
    public  void  givenNewSalaryForEmployee_WhenUpdated_ShouldSyncWithDB()
    {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.updateEmployeeSalary("terisa",200000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("terisa");
        Assertions.assertTrue(result);
    }

    @Test
    void givenNewBasicPAyForEmployee_WhenUpdated_ShouldSyncWithDB() {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.updateEmployeeBasicPay("terisa",3000000.00);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("terisa");
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrieved_ShouldReturnEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        LocalDate startDate = LocalDate.of(2019,06,22);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataForDataRange(startDate,endDate);
        Assertions.assertEquals(3,employeePayrollData.size());
    }

    @Test
    public void givenDateRange_WhenAverageSalaryRetrievedGroupByGender_ShouldReturnAverageSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> averageSalaryGroupByGender = employeePayrollService.getAverageSalaryGroupByGender();
        boolean result = averageSalaryGroupByGender.get("M").equals(200000.0) && averageSalaryGroupByGender.get("F").equals(200000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenMinimumSalaryRetrievedGroupByGender_ShouldReturnMinimumSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> minimumSalaryGroupByGender = employeePayrollService.getMinimumSalaryGroupByGender();
        boolean result = minimumSalaryGroupByGender.get("M").equals(100000.0) && minimumSalaryGroupByGender.get("F").equals(200000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenMaximumSalaryRetrievedGroupByGender_ShouldReturnMaximumSalaryByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> maximumSalaryGroupByGender = employeePayrollService.getMaximumSalaryGroupByGender();
        boolean result = maximumSalaryGroupByGender.get("M").equals(300000.0) && maximumSalaryGroupByGender.get("F").equals(200000.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenDateRange_WhenRetrievedGroupByGender_ShouldReturnCountOfEmployeesByGender(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        Map<String, Double> countOfEmployeeGroupByGender = employeePayrollService.getCountOfEmployeeGroupByGender();
        boolean result = countOfEmployeeGroupByGender.get("M").equals(2.0) && countOfEmployeeGroupByGender.get("F").equals(1.0);
        Assertions.assertTrue(result);
    }

    @Test
    public void givenNewEmployee_whenAdded_shouldSyncWithDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollData(EmployeePayrollService.IOServices.DB_IO);
        employeePayrollService.addEmployeeToPayroll("mark","M",500000.00,LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("mark");
        Assertions.assertTrue(result);
    }

}
