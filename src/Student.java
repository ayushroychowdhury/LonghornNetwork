/*
 * Name: Stefan Cox
 * EID: sbc2287
 * Slip Days Used: 0
 */


 import java.util.*;

 /**
  * Student class that represents a student.
  */
 public abstract class Student {
     protected String name;
     protected int age;
     protected String gender;
     protected int year;
     protected String major;
     protected double gpa;
     protected List<String> roommatePreferences;
     protected List<String> previousInternships;
 
     public abstract int calculateConnectionStrength(Student other);
 }
 