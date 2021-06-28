package notes.utils.security;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import net.minidev.json.JSONArray;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Map;

@UtilityClass
public class NoteSecurity {

    private static final String UID = "uid";
    private static final String ADMIN_ROLE = "ADMINS";
    private static final String USER_ROLE = "USERS";
    private static final String GROUPS = "groups";
    private static final String USERID_UNKNOWN = "UNKNOWN";

    public void isAuthorizedToAccessAllNotes(@NonNull Jwt jwt) {
        if (isAdmin(jwt.getClaims())) {
            return;
        }
        throwAccessDeniedException();
    }

    public void isAuthorizedToAccessUserNotes(@NonNull String targetUserId, @NonNull Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();

        if (isAdmin(claims)) {
            return;
        } else if (isUser(claims)) {
            String userId = (String) claims.get(UID);
            if (userId.equals(targetUserId)) {
                return;
            }
            throwAccessDeniedException(userId);
        }
        throwAccessDeniedException();
    }

    private boolean isAdmin(Map<String, Object> claims) {
        return ((JSONArray) claims.get(GROUPS)).contains(ADMIN_ROLE);
    }

    private boolean isUser(Map<String, Object> claims) {
        return ((JSONArray) claims.get(GROUPS)).contains(USER_ROLE);
    }

    private void throwAccessDeniedException() {
        throwAccessDeniedException(USERID_UNKNOWN);
    }

    private void throwAccessDeniedException(String userId) {
        throw new AccessDeniedException("Nein! Nein! Nein! Nein! UserId: " + userId);
    }


}
