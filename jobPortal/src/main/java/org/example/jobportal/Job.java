package org.example.jobportal;

public class Job {
    public int id;

    public String jobName;
    public String jobDescription;
    public String jobLocation;
    public String employmentType;
    public boolean favorited;

    public Job(String jobName, String jobShortDescription, String jobLocation, String employmentType, boolean favorite) {
        this.jobName = jobName;
        this.jobDescription = jobShortDescription;
        this.jobLocation = jobLocation;
        this.employmentType = employmentType;
        this.favorited = favorite;
    }

    @Override
    public String toString() {
        return "Job{" +
                ", jobName='" + jobName + '\'' +
                ", jobShortDescription='" + jobDescription + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", favorite=" + favorited +
                '}';
    }


    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobShortDescription() {
        return jobDescription;
    }

    public void setJobShortDescription(String jobShortDescription) {
        this.jobDescription = jobShortDescription;
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

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }
}
