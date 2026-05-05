public class AttackCard extends Card implements DealsDamage {

    private int attackDamage;

    public AttackCard() {

        // Attack card settings
        int minAttackDamage = 5;
        int maxAttackDamage = 8;
        this.attackDamage = Rand.randomInt(minAttackDamage, maxAttackDamage + 1);

        // Points gained from playing this card
        int minPoints = 1;
        int maxPoints = 2;
        int pointValue = Rand.randomInt(minPoints, maxPoints + 1);
        super(pointValue);
    }

    @Override
    public void play(Player currentPlayer) {

    }

    @Override
    public void doDamage(Player playerToDamage, int damageAmount) {

    }
}
