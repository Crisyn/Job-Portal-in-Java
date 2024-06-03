package org.example.jobportal;

public class Job {
    public int id;

    public String jobName;
    public String jobLocation;
    public String jobDescription;
    public String employmentType;
    public boolean favorited;
    public String eMail;

    public Job(String jobName, String jobLocation, String jobDescription, String employmentType, boolean favorite, String eMail) {
        this.jobName = jobName;
        this.jobLocation = jobLocation;
        this.jobDescription = jobDescription;
        this.employmentType = employmentType;
        this.favorited = favorite;
        this.eMail = eMail;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", jobName='" + jobName + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", jobDescription='" + jobDescription + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", favorite=" + favorited +'\'' +
                ", eMailAddress='" + eMail +
                '}';
    }


    public String getJobName() {
        return jobName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public String getEMailAddress() {
        return eMail;
    }
}
