package com.chat.jchat0.controller;


import com.chat.jchat0.model.Calculator;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/calculator")
public class CalculatorController {
  
  @GetMapping
  public Model calculator(Model model) {
    model.addAttribute("calculator");
    return model;
  }
  
  //---------------------------------INSTANCES------------------------------------------
  String responseMonth1, responseDay1, responseYear1, responseYear2, responseMonth2, responseDay2;
  DateTimeFormatter formatter;
  long days;
  String response;
  boolean validEntryMonth1;
  boolean validEntryMonth2;
  boolean validEntryDay1;
  boolean validEntryDay2;
  boolean validEntryYear1;
  boolean validEntryYear2;
  LocalDate firstDate;
  LocalDate secondDate;
  int responseDayInt1;
  int responseDayInt2;
  int responseMonthInt1;
  int responseMonthInt2;
  int responseYearInt1;
  int responseYearInt2;
  YearMonth yearMonthObject1;
  YearMonth yearMonthObject2;
  int daysInMonth1;
  int daysInMonth2;
  //---------------------------------/INSTANCES-------------------------------------------
  
   
  @PostMapping(consumes = "application/json",
              produces = "application/json")
  @ResponseBody
  public ResponseEntity calcCount(@RequestBody Calculator calculator) { 
    System.out.println("------------- !!!!! ------------- started now ------------- !!!!! -------------");
    
      formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
      responseDayInt1 = calculator.day1; 
      responseDayInt2 = calculator.day2;
      responseMonthInt1 = calculator.month1;
      responseMonthInt2 = calculator.month2;
      responseYearInt1 = calculator.year1;
      responseYearInt2 = calculator.year2;
      yearMonthObject1 = YearMonth.of(responseYearInt1, responseMonthInt1);
      yearMonthObject2 = YearMonth.of(responseYearInt2, responseMonthInt2);
      daysInMonth1 = yearMonthObject1.lengthOfMonth();
      daysInMonth2 = yearMonthObject2.lengthOfMonth();

      cleanUp();
      validityMonth1();
      
      System.out.println(response);
      
//   return new ResponseEntity(response, HttpStatus.OK);
      return ResponseEntity.status(HttpStatus.OK).body(response);
  }
 
  public void cleanUp() {
    validEntryMonth1 = false;
    validEntryMonth2 = false;
    validEntryDay1 = false;
    validEntryDay2 = false;
    validEntryYear1 = false;
    validEntryYear2 = false;
  }
  
  public void validityMonth1() {
    responseMonthInt1 += 1;
    if (responseMonthInt1 > 9 && responseMonthInt1 < 12) {
      responseMonth1 = responseMonthInt1 + "";
    } else if (responseMonthInt1 < 10) {
      responseMonth1 = "0" + responseMonthInt1;
    } else {
      GetTime2();
      return;
    }
    validEntryMonth1 = true;
    validityYear1();
  }

  public void validityYear1() {
    if (validEntryMonth1) {
      // slight changes to make responseYear appropriate to use in formatter (YYYY) method
      if (responseYearInt1 > 1000 && responseYearInt1 < 10000) {
        responseYear1 = "" + responseYearInt1;
      } else if (responseYearInt1 < 1000 && 100 <= responseYearInt1) {
        responseYear1 = "0" + responseYearInt1;
      } else if (10 <= responseYearInt1 && responseYearInt1 < 100) {
        responseYear1 = "00" + responseYearInt1;
      } else if (responseYearInt1 < 10 && responseYearInt1 > 0) {
        responseYear1 = "000" + responseYearInt1;
      } else {
        GetTime2();
        return;
      }
      validEntryYear1 = true;
      validityDay1();
    }
  }

  public void validityDay1() {
    yearMonthObject1 = YearMonth.of(responseYearInt1, responseMonthInt1);
    daysInMonth1 = yearMonthObject1.lengthOfMonth();
    if (responseDayInt1 <= daysInMonth1 && responseDayInt1 > 0) {
      if (responseDayInt1 < 10) {
        // to apply the reference for LocalDate.parse method (it has to be like 01 instead of 1 (DD format))
        responseDay1 = "0" + responseDayInt1 + " ";
      } else {
        responseDay1 = responseDayInt1 + " ";
      }
      validEntryDay1 = true;
      GetTime1();
    } else {
      GetTime2();
    }
  }

  public void GetTime1() {
    String firstInput = responseDay1 + responseMonth1 + " " + responseYear1;
    firstDate = LocalDate.parse(firstInput, formatter);
    validityMonth2();
  }

  public void validityMonth2() {
    responseMonthInt2 += 1;
    if (responseMonthInt2 > 9 && responseMonthInt2 < 12) {
      responseMonth2 = responseMonthInt2 + "";
    } else if (responseMonthInt2 < 10) {
      responseMonth2 = "0" + responseMonthInt2;
    } else {
      GetTime2();
      return;
    }
    validEntryMonth2 = true;
    validityYear2();
  }

  public void validityYear2() throws NumberFormatException {
    if (validEntryMonth2) {
      if (responseYearInt2 > 1000 && responseYearInt2 < 10000) {
        responseYear2 = "" + responseYearInt2;
      } else if (responseYearInt2 < 1000 && 100 <= responseYearInt2) {
        responseYear2 = "0" + responseYearInt2;
      } else if (10 <= responseYearInt2 && responseYearInt2 < 100) {
        responseYear2 = "00" + responseYearInt2;
      } else if (responseYearInt2 < 10 && responseYearInt2 > 0) {
        responseYear2 = "000" + responseYearInt2;
      } else {
        GetTime2();
        return;
      }
      validEntryYear2 = true;
      validityDay2();
    }
  }

  public void validityDay2() {
    yearMonthObject2 = YearMonth.of(responseYearInt2, responseMonthInt2);
    daysInMonth2 = yearMonthObject2.lengthOfMonth();
    if (responseDayInt2 <= daysInMonth2 && responseDayInt2 > 0) {
      if (responseDayInt2 < 10) {
        responseDay2 = "0" + responseDayInt2 + " ";
      } else {
        responseDay2 = responseDayInt2 + " ";
      }
      validEntryDay2 = true;
    }
    GetTime2();
  }

  public void GetTime2() {
    if (validEntryMonth1 == true
        && validEntryMonth2 == true
        && validEntryDay1 == true
        && validEntryDay2 == true
        && validEntryYear1 == true
        && validEntryYear2 == true) {

      String secondInput = responseDay2 + responseMonth2 + " " + responseYear2;
      secondDate = LocalDate.parse(secondInput, formatter);
      days = ChronoUnit.DAYS.between(firstDate, secondDate);
      cleanUp();
      response = "Total number of days between the dates is: " + days + " days.";
    } else {
      cleanUp();
      response = "Please, check your input. Year should be positive and < 10000.";
    }
  }
}