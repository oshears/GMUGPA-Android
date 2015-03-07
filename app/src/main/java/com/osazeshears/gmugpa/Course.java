package com.osazeshears.gmugpa;


public class Course{
    String courseName = "";
    String courseGrade = "";
    int courseCredits = 0;
    double coursePoints = 0;


    public Course(String name, String grade, int credits){
        this.courseName=name;
        this.courseGrade = grade;
        this.courseCredits = credits;
        this.coursePoints = gradeToQuals(grade) * credits;
    }

    public String toString(){

        return ("Course Name: "+this.courseName + "    Grade: " + this.courseGrade +
                "    Credits: " + this.courseCredits);
    }

    static public double gradeToQuals(String grade){
        switch (grade){
            case ("A+/A"):
                return 4.0;
            case ("A-"):
                return 3.67;
            case ("B+"):
                return 3.33;
            case ("B"):
                return 3.0;
            case ("B-"):
                return 2.67;
            case ("C+"):
                return 2.33;
            case ("C"):
                return 2.0;
            case ("C-"):
                return 1.67;
            case ("D"):
                return 1.0;
            case ("F"):
                return 0;
            default:
                return 0;
        }
    }



}
