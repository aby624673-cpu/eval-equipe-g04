public class Plateforme {
    private Compte[] comptes = new Compte[Parametres.CAPACITE];
    private int nombre = 0;
    private String[] lignes = new String[100];
    private int nbLignes = 0;
    private long totalFrais = 0;

    public boolean ajouter(Compte c) {
        if (c == null) {
            return false;                       // rien à ajouter
        }
        if (rechercher(c.getNumero()) != null) {
            return false;                       // numéro déjà présent
        }
        if (nombre == comptes.length) {
            return false;                       // tableau plein
        }
        comptes[nombre] = c;
        nombre++;
        return true;
    }

    public Compte rechercher(String numero) {
        for (int i = 0; i < nombre; i++) {
            if (comptes[i].getNumero().equals(numero)) {
                return comptes[i];
            }
        }
        return null;                            // pas trouvé
    }

    public int getNombre() {
        return nombre;
    }
}