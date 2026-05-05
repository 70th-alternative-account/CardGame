public class FreezeCard extends Card implements DealsDamage, AppliesFreeze {
    private float freezeTime;

    public FreezeCard() {

        // Freeze card settings
        freezeTime = 1; // 1 turn
        int minPointValue = 2;
        int maxPointValue = 3;

        int pointValue = Rand.randomInt(minPointValue, maxPointValue + 1);

        super(pointValue);
    }

    public float getFreezeTime() {
        return freezeTime;
    }

    @Override
    public void play(Player currentPlayer) {

    }

    @Override
    public void freeze(Player playerToFreeze) {

    }

    @Override
    public void doDamage(Player playerToDamage, int damageAmount) {

    }
}
