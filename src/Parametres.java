/**
 * Paramètres de l'équipe. Remplissez GROUPE et MEMBRES au checkpoint 0,
 * puis ne modifiez plus rien : les autres valeurs sont calculées à partir de GROUPE.
 */
public final class Parametres {
    public static final int GROUPE = 4;                                   // numéro donné par l'enseignant
    public static final String[] MEMBRES = {
        "A : Baba niang (ISEPAT202603271)",
        "B : Mame Diarra Diop (ISEPAT202602952)",
        "C : Khady Diallo (ISEPAT202602872)",
        "D : Aby Cisse (ISEPAT202602772)"
    };

    public static final int TAUX_FRAIS = 10 + 4 % 6;                 // pour mille (10 = 1 %)
    public static final long PLAFOND_RETRAIT = 150_000 + 10_000 * (4 % 8);   // FCFA
    public static final long SEUIL_PREMIUM = 50_000;                      // FCFA
    public static final int CAPACITE = 20;                                // comptes

    private Parametres() { }
}
    
