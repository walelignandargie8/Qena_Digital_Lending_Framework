package portal.models.communications;

import lombok.Getter;

import java.util.List;

@Getter
public class Communications {
    private List<Communication> communications;

    /**
     * Method to get the communication given the Status
     *
     * @param status Status
     * @return the Communication
     */
    public Communication getCommunicationGivenStatus(String status) {
        return communications.stream()
                .filter(communication -> communication.getStatus() != null)
                .filter(communication -> communication.getStatus().equalsIgnoreCase(status))
                .findFirst().orElse(null);
    }
}
