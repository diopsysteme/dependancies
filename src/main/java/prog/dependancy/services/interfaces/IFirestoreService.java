package prog.dependancy.services.interfaces;


import java.util.List;
import java.util.Map;

public interface IFirestoreService {

    /**
     * Crée un document et retourne l'objet créé
     *
     * @param <T>          Type de retour souhaité
     * @param collection   Nom de la collection
     * @param entity       Objet à sauvegarder
     * @param responseType Type de retour souhaité
     * @return L'objet créé converti dans le type demandé
     */
    <T> T create(String collection, Object entity, Class<T> responseType);

    /**
     * Récupère tous les documents d'une collection
     *
     * @param <T>          Type des objets à retourner
     * @param collection   Nom de la collection
     * @param responseType Type des objets à retourner
     * @return Liste des documents convertis
     */
    <T> List<T> getAll(String collection, Class<T> responseType);

    /**
     * Recherche avec critères multiples
     *
     * @param <T>          Type des objets à retourner
     * @param collection   Nom de la collection
     * @param criteria     Map des critères (field, value)
     * @param responseType Type des objets à retourner
     * @return Liste des documents correspondants
     */
    <T> List<T> findByFields(String collection, Map<String, Object> criteria, Class<T> responseType);

    /**
     * Recherche un document par ID avec conversion dynamique
     *
     * @param <T>          Type de l'objet à retourner
     * @param collection   Nom de la collection
     * @param id           ID du document
     * @param responseType Type de l'objet à retourner
     * @return Document correspondant ou null si inexistant
     */
    <T> T findById(String collection, String id, Class<T> responseType);

    /**
     * Met à jour un document existant
     *
     * @param <T>          Type de retour souhaité
     * @param collection   Nom de la collection
     * @param id           ID du document
     * @param updates      Données à mettre à jour
     * @param responseType Type de retour souhaité
     * @return Document mis à jour
     */
    <T> T update(String collection, String id, Map<String, Object> updates, Class<T> responseType);

    /**
     * Supprime un document
     *
     * @param collection Nom de la collection
     * @param id         ID du document à supprimer
     * @return true si supprimé avec succès
     */
    boolean delete(String collection, String id);
}

