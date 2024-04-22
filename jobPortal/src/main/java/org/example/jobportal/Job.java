package org.example.jobportal;

public class Job {
    public int id;
    public String jobName;
    public String jobShortDescription;
    public String jobLocation;
    public String employmentType;
    public boolean favorited;

    public Job(String jobName, String jobShortDescription, String jobLocation, String emplymentType, boolean favorited) {
        this.jobName = jobName;
        this.jobShortDescription = jobShortDescription;
        this.jobLocation = jobLocation;
        this.employmentType = emplymentType;
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", jobName='" + jobName + '\'' +
                ", jobShortDescription='" + jobShortDescription + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", emplymentType='" + employmentType + '\'' +
                ", favorited=" + favorited +
                '}';
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobShortDescription() {
        return jobShortDescription;
    }

    public void setJobShortDescription(String jobShortDescription) {
        this.jobShortDescription = jobShortDescription;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String emplymentType) {
        this.employmentType = emplymentType;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
