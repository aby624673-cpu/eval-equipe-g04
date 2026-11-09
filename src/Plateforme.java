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
        public long getTotalFrais() {
        return totalFrais;
    }

    public boolean transferer(String de, String vers, long montant) {
        if (montant <= 0) {
            return false;                       // montant interdit
        }
        if (de == null || vers == null || de.equals(vers)) {
            return false;                       // numéro manquant ou vers soi-même
        }
        Compte source = rechercher(de);
        Compte dest = rechercher(vers);
        if (source == null || dest == null) {
            return false;                       // compte absent
        }
        long f = source.frais(montant);         // AVANT le retrait
        if (!source.retirer(montant)) {
            return false;                       // plafond ou solde : rien n'a bougé
        }
        dest.deposer(montant);
        totalFrais = totalFrais + f;

        if (nbLignes < lignes.length) {         // journal pas plein
            lignes[nbLignes] = String.format("TX%04d", nbLignes + 1) + " " + de + " -> " + vers
                    + " " + montant + " FCFA (frais " + f + ")";
            nbLignes++;
        }
        return true;
    }

    public String[] journal() {
        String[] copie = new String[nbLignes];
        for (int i = 0; i < nbLignes; i++) {
            copie[i] = lignes[i];
        }
        return copie;
    }
}