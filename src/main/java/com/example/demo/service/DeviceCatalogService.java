import java.util.List;

public interface DeviceCatalogService {

    DeviceCatalogItem createItem(DeviceCatalogItem item);

    void updateActiveStatus(Long id, boolean active);

    List<DeviceCatalogItem> getAllItems();
}
