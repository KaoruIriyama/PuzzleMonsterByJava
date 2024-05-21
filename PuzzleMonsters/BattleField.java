package PuzzleMonsters;
/*バトルが行われるバトルフィールドを表すクラス。
 * 
 */
public class BattleField {

    public static final char[] chara = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N'} ;
    
    static final int MAX_GEMS = 14;
    
    private Gem[] gems = new Gem[MAX_GEMS];

    private Party playerparty;
    
    private Monster mon;
    /*BattleFieldクラスのコンストラクタ。
       
     */
    public BattleField(Party party, Monster m){
        this.playerparty = party;
        this.mon = m;
        fillGems();
    }

    public Party getPlayerParty(){return this.playerparty;}
    public Monster getMonster(){return this.mon;}
    public void setGems(int n, Gem g){this.gems[n] = g;}
    public Gem getGems(int n){return this.gems[n];}
    /**
     * 
     */
    public void showBattleField(Party party){
        System.out.println("------------------------------" + "\n");
        System.out.println(mon.toString());
        for(Monster m : this.playerparty.getFellows()){ System.out.print(m.printname() + ' '); }//ここを横並びにした
        System.out.println("\nHP=" + party.getPartyHp() + "/" + party.getPartyMaxHp());//ここを中央揃えにする
        System.out.println("------------------------------");
        for (int chara = 0; chara < MAX_GEMS; chara++) { System.out.print(BattleField.chara[chara] + " "); }
        System.out.print("\n");
        printGems();
        System.out.println( "------------------------------");
    }
    /**
     * 
     * @param i
     */
    public void spawnGems(int i){
        int r = new java.util.Random().nextInt(Element.EMPTY.getId());
        gems[i] = new Gem(r);
    }
    /** */
    private void fillGems(){
        for(int rand = 0; rand < MAX_GEMS; rand++){
            // ここの乱数発生がおかしいので直した。拡張for文でなく通常のfor文にしたら想定通りのランダムな配列になった。
            spawnGems(rand);
        }
    }
    /**
     * 
     */
    public void printGems(){ 
        for(int value = 0; value < MAX_GEMS; value++){ 
            System.out.print(printGem(value) + " ");
        } System.out.println("\n");
    }
    /**
     * 
     * @param g
     * @return
     */
    private char printGem(int g){ return gems[g].getElement().getSymbol();}
    /**
     * 
     * @param combo
     */
    public void printCombo(int combo){ System.out.print(combo + "COMBO!\n");}
    /**
     * 
     * @param cm
     * @param boo
     */
    public void moveGem(char[] cm, boolean boo){
        int start = cm[0] - 'A';
        int goal = cm[1] - 'A'; //charは中に整数が入っているので、char型同士なら演算も出来る
        int root =(start < goal) ? 1 : -1 ;
            
        printGems();
            
        for (int i = start; (i != goal); i += root){
            this.swapGem(i, root);
            if (boo == false){printGems();}
        }    
    }
    /**
     * 
     * @param St
     * @param root
     */
    private void swapGem(int St,int root){

            //バグはコマンドの範囲がA~Eの中か、A~EからF以降に動く時に、またF以降からA~Dに動くときに起こる
            // A~EとF以降の範囲を移動する場合、左右どちらの移動でもA~Eの範囲で移動が行われる時のみバグも発生する(F～Nの範囲内ではバグはない)
            // バグの内容は、正常な石の移動に伴って他の石が別の石に交換される（かなわず二種類の石が一斉にお互いに対の石へと変わる）
            // 交換のタイミングと対となる石同士の組み合わせはランダム、一度すべての石の交換が起きてももう一度同じつい同士での交換が行われたり、
            // 不明なタイミングで対の組み合わせが変更されて交換が行われることもある(フィールド移動が長いほど交換回数も多くなりやすい)
          
        Gem vac = this.gems[St];
        this.gems[St] = this.gems[St + root];
        this.gems[St + root] = vac;
    }
    /**
     * 
     * @return
     */
    private double blurDamage(){
        double blur = (new java.util.Random().nextInt(3) + 9) * 0.1;
        return blur;}
    /**
     * 
     * @param cmb
     * @return
     */
    private double calcBanishDamage(int cmb){
        double actbns = BanishInfo.getSeq() - 3 + cmb;
        return Math.pow (1.5, actbns);}
    /**
     * 
     * @param fel
     * @param comb
     * @return
     */
    public int calcAttackDamage(Monster fel, int comb){
        int pdmg = (int)((fel.getATK() - this.getMonster().getDFS() + this.playerparty.getPlayerLev() * 10) * this.calcBanishDamage(comb) * 
        Element.elementBoost(fel.getElement(), this.getMonster().getElement()) * this.blurDamage());
        return Math.max(pdmg, 1);
        //計算式中の（+ this.playerparty.getPlayerLev() * 10）はオリジナル要素。ダメージ計算をフェアにする

        //元の仕様に忠実なダメージ計算式は以下。
        // ((fel.getATK() - this.getMonster().getDFS()) * というダメージ計算式が原因で実プレイ時は3体目以降こちらからのダメージを
        //1ずつしか与えられない状態と化している。
        // int pdmg = (int)((fel.getATK() - this.getMonster().getDFS()) * this.calcBanishDamage(comb) * 
        // Element.elementBoost(fel.getElement(), this.getMonster().getElement()) * this.blurDamage());
        // return Math.max(pdmg, 1);
        
    }
    /**
     * 
     * @param party
     * @return
     */
    public int calcEnemyAttackDamage(Party party){
        
        int edmg = (int)((this.getMonster().getATK() - party.getPartyDFS()) * this.blurDamage());
        return Math.max(edmg, 1);
    }
    /**
     * 
     * @param comb
     * @return
     */
    public int calcRecoverDamage(int comb){
        int rcv = (int)(20 * this.calcBanishDamage(comb) * this.blurDamage());
        return rcv;
    }
    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if (o instanceof BattleField){
            BattleField bat = (BattleField) o;
            if((this.playerparty.equals(bat.playerparty)) 
            && this.mon.equals(bat.mon) 
            && this.gems == bat.gems){
                return true;
            }
        }return false;
    }
    @Override
    public String toString(){return "this.chara" + "\n" ;}
    
}
