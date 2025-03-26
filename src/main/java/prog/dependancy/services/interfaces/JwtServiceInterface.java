package prog.dependancy.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.Map;

public interface JwtServiceInterface {

    /**
     * Extrait le nom d'utilisateur du token JWT.
     *
     * @param token Le tok                                  en JWT.
     * @return Le nom d'utilisateur extrait.
     */
    String extractUsername(String token);

    /**
     * Extrait une revendication (claim) spécifique du token JWT.
     *
     * @param <T>            Le type de la revendication.
     * @param token          Le token JWT.
     * @param claimsResolver Une fonction pour extraire la revendication.
     * @return La revendication extraite.
     */
    <T> T extractClaim(String token, java.util.function.Function<io.jsonwebtoken.Claims, T> claimsResolver);

    /**
     * Génère un token JWT sans revendications supplémentaires.
     *
     * @param userDetails Les détails de l'utilisateur.
     * @return Le token JWT généré.
     */
    String generateToken(UserDetails userDetails);

    /**
     * Génère un token JWT avec des revendications supplémentaires.
     *
     * @param extraClaims Les revendications supplémentaires.
     * @param userDetails Les détails de l'utilisateur.
     * @return Le token JWT généré.
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Vérifie si un token JWT est valide pour un utilisateur donné.
     *
     * @param token       Le token JWT.
     * @param userDetails Les détails de l'utilisateur.
     * @return `true` si le token est valide, sinon `false`.
     */
    boolean isTokenValid(String token, UserDetails userDetails);

    /**
     * Vérifie si un token JWT a expiré.
     *
     * @param token Le token JWT.
     * @return `true` si le token a expiré, sinon `false`.
     */
    boolean isTokenExpired(String token);

    /**
     * Génère un token JWT simple avec un sujet et une durée d'expiration spécifiés.
     *
     * @param subject        Le sujet du token.
     * @param expirationTime La durée d'expiration en millisecondes.
     * @return Le token JWT généré.
     */
    String generateSimpleToken(String subject, long expirationTime);

    /**
     * Récupère la durée d'expiration configurée pour le token JWT.
     *
     * @return La durée d'expiration en millisecondes.
     */
    long getExpirationTime();
}
