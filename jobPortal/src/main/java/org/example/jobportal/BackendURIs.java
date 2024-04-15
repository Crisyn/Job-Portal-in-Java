package org.example.jobportal;

public class BackendURIs {
    private static String base = "http://localhost:8080";

    public static String getJobs() {
        return base + "/jobs";
    }
}
