package org.example.jobportal;

public class Job {
    public int id;
    public String jobName;
    public String jobShortDescription;
    public String jobLocation;
    public String emplymentType;
    public boolean favorited;

    public Job(int id, String jobName, String jobShortDescription, String jobLocation, String emplymentType, boolean favorited) {
        this.id = id;
        this.jobName = jobName;
        this.jobShortDescription = jobShortDescription;
        this.jobLocation = jobLocation;
        this.emplymentType = emplymentType;
        this.favorited = favorited;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", jobShortDescription='" + jobShortDescription + '\'' +
                ", jobLocation='" + jobLocation + '\'' +
                ", emplymentType='" + emplymentType + '\'' +
                ", favorited=" + favorited +
                '}';
    }
}
