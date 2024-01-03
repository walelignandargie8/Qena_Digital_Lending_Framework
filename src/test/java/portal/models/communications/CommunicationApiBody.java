package portal.models.communications;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunicationApiBody {
    private String name;
    private String description;
    private String id;
}
