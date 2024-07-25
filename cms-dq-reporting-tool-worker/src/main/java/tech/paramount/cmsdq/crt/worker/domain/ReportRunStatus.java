package tech.paramount.cmsdq.crt.worker.domain;

public record ReportRunStatus(String value) {
    public static final String PENDING = "P";
    public static final String RUNNING = "R";
    public static final String COMPLETED = "S";
    public static final String FAILED = "F";

    public static ReportRunStatus of(String value) {
        return new ReportRunStatus(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
