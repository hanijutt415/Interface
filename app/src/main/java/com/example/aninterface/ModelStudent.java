package com.example.aninterface;

public class ModelStudent {
    String studentName;
    String fatherName;
    String fatherCNIC;
    String registrationNo;
    String imageUri;

    public ModelStudent(String studentName, String fatherName, String fatherCNIC, String registrationNo, String imageUri) {
        this.studentName = studentName;
        this.fatherName = fatherName;
        this.fatherCNIC = fatherCNIC;
        this.registrationNo = registrationNo;
        this.imageUri = imageUri;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCNIC() {
        return fatherCNIC;
    }

    public void setFatherCNIC(String fatherCNIC) {
        this.fatherCNIC = fatherCNIC;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
