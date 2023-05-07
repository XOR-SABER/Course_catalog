package Datatypes;

public class Course {
    private String courseId;
    private String courseName;
    private Transfer transferability;
    private float units;

    // I do like java's enums.. a bit more than C++'s
    public static enum Transfer {
        NON_TRANSFERABLE("", 0),
        CSU_TRANSFERABLE("Transferable to CSU only", 1),
        UC_TRANSFERABLE("Transferable to UC only", 2),
        U_TRANSFERABLE("Transferable to both UC and CSU", 3);

        private final String formatted;
        private final int code;

        private Transfer(String formatted, int code) {
            this.formatted = formatted;
            this.code = code;
        }

        public String getFormatted() {
            return formatted;
        }

        public int getCode() {
            return code;
        }
    }

    public Course() {
    }

    public Course(String line) {
        String[] arr = line.split(",");
        courseId = arr[0];
        courseName = arr[1];
        switch (arr[2]) {
            case "Transferable to CSU only":
                transferability = Transfer.CSU_TRANSFERABLE;
                break;
            case "Transferable to UC only":
                transferability = Transfer.UC_TRANSFERABLE;
                break;
            case "Transferable to both UC and CSU":
                transferability = Transfer.U_TRANSFERABLE;
                break;
            default:
                transferability = Transfer.NON_TRANSFERABLE;
                break;
        }
        try {
            units = Float.parseFloat(arr[3]);
        } catch (NumberFormatException error) {
            System.out.println("Invalid float on this line \"" + line + "\" exiting program..");
            System.exit(1);
        }
    }

    // Proper class design is such a drag..
    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public Transfer getTransferability() {
        return transferability;
    }

    public float getUnits() {
        return units;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setTransferability(Transfer transferability) {
        this.transferability = transferability;
    }

    public void setUnits(float units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return String.format("%s : %s\n\t%s Code: %d\n\tUnits: %f",
                getCourseId(), getCourseName(), getTransferability().getFormatted(),
                getTransferability().getCode(), getUnits());
    }
}