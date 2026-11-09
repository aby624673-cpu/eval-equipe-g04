
public abstract class Compte {

    
    private String numero;
    private String titulaire;
    private long solde;

    
    public static final int TAUX_FRAIS = 10 + 4 % 6;                 
    public static final long PLAFOND_RETRAIT = 150_000 + 10_000 * (4 % 8);
     public static final long SEUIL_PREMIUM = 50_000;
     public static final int CAPACITE = 20; 

    
    private static int nombreCrees = 0;

    // Constructeur
    public Compte(String numero, String titulaire) {
        this.numero = numero;
        this.titulaire = titulaire;
        this.solde = 0;
        nombreCrees++;
    }

    // Accesseurs
    public String getNumero() {
        return numero;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public long getSolde() {
        return solde;
    }

    // A2 - Dépôt
    public boolean deposer(long montant) {
        if (montant <= 0) {
            return false;
        }

        solde += montant;
        return true;
    }

    // A2 - Retrait
    public boolean retirer(long montant) {
        if (montant <= 0 || montant > plafond()) {
            return false;
        }

        long montantTotal = montant + frais(montant);

        if (montantTotal > solde) {
            return false;
        }

        solde -= montantTotal;
        return true;
    }

    // A3 - Méthodes abstraites
    public abstract long frais(long montant);

    public abstract long plafond();

    public abstract String type();

    // A6 - Nombre de comptes créés
    public static int getNombreCrees() {
        return nombreCrees;
    }
    
@Override
public String toString() {
    return "[" + type() + "] " + numero + " "
            + titulaire + " " + solde + " FCFA";
}

@Override
public boolean equals(Object obj) {

    if (this == obj) {
        return true;
    }

    if (obj == null || !(obj instanceof Compte)) {
        return false;
    }

    Compte autre = (Compte) obj;

    return this.numero.equals(autre.numero);
}

@Override
public int hashCode() {
    return numero.hashCode();
}

}

