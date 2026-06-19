package utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonReader {
    protected ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonResponse;
    public jsonReader(String resText) throws JsonProcessingException {
        jsonResponse = mapper.readTree(resText);
    }

    public String getStringValue(String attribute) {
        return String.valueOf(jsonResponse.get(attribute));
    }

    public int getIntValue(String attribute) {
        return Integer.parseInt(String.valueOf(jsonResponse.get(attribute)));
    }
}
