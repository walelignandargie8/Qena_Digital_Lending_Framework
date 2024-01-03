package portal.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.util.HashMap;
import java.util.Map;

public class TokenGenerator {

    /**
     * Method to generate Token
     *
     * @param sub UserID
     * @param tid ApplicationKey
     * @param exp Expiration
     * @return Token
     */
    public static String getJWTToken(String sub, String tid, String exp) {
        String token = null;
        Map<String, String> payloadUser = new HashMap<String, String>();
        payloadUser.put("sub", sub);
        payloadUser.put("user_customer_id", tid);
        payloadUser.put("exp", exp);
        try {
            Algorithm algorithm = Algorithm.HMAC256(
                    PropertiesReader.getDefaultOrPreferredSetting("secretKey", "secretKey"));
            token = JWT.create()
                    .withPayload(payloadUser)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {

        }
        return token;
    }
}
