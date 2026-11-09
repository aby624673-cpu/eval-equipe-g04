
public class CompteStandard extends Compte {

    // Constructeur
    public CompteStandard(String numero, String titulaire) {
        super(numero, titulaire);
    }

    // Calcul des frais
    @Override
    public long frais(long montant) {
        return montant * TAUX_FRAIS / 1000;
    }

    // Plafond de retrait
    @Override
    public long plafond() {
        return PLAFOND_RETRAIT;
    }

    // Type du compte
    @Override
    public String type() {
        return "STD";
    }
}

