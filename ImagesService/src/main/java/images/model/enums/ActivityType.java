package images.model.enums;

public enum ActivityType {
    UPLOAD("Uploaded a new image"),
    DELETE("Deleted an image"),
    UPDATE("Updated an image"),
    READ("Read an image from the database");

    final String type;

    ActivityType(String type) {
        this.type = type;
    }
}
