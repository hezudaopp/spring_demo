package spittr.exceptions;

/**
 * Created by 273cn on 16/12/22.
 */
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private String resourceId;

    public ResourceNotFoundException(String resourceName, String resourceId) {
        this.resourceName = resourceName;
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceId() {
        return resourceId;
    }
}
