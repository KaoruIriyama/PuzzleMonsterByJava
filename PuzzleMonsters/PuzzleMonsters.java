package PuzzleMonsters;

import static PuzzleMonsters.Element.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ゲームのメインクラス。
 */
public class PuzzleMonsters{
    
    public static void main(String[] args) throws Exception{
    	//コマンドラインイン数なしで起動した場合、プレイヤー名を"ななし"にする
        String playername = (args.length > 0 ? args[0]:"ななし");
    	//タイトル表示
    	System.out.println("*** Puzzle & Monsters ***");
        //味方モンスターのリストを準備
        List<Monster> fellows = new ArrayList<>();
        fellows.add(new Monster("朱雀", 150, 150, FIRE, 25, 10));
        fellows.add(new Monster("青龍", 150, 150, WATER, 15, 10));
        fellows.add(new Monster("白虎", 150, 150, WIND, 20, 5));
        fellows.add(new Monster("玄武", 150, 150, EARTH, 20, 15));
        // パーティ生成
        Party party = new Party(playername, fellows);
        System.out.println(party.getPlayerName() + "のパーティ(HP=" + party.getPartyHp() + ")はダンジョンに到着した");
        party.showParty(party);
        // 敵モンスターのリストとダンジョンの準備
        List<Monster> enemys = new ArrayList<>();
        enemys.add(new Monster("スライム", 100, 100, WATER, 10, 5));
        enemys.add(new Monster("ゴブリン", 200, 200, EARTH, 20, 15));
        enemys.add(new Monster("オオコウモリ", 300, 300, WIND, 30, 25));
        enemys.add(new Monster("ウェアウルフ", 400, 400, WIND, 40, 30));
        enemys.add(new Monster("ドラゴン",800 , 800, FIRE, 50, 40));
        Dungeon dun1 = new Dungeon();
        dun1.setEnemy(enemys);

        int duncount = goDungeon(party, dun1);
        if(duncount == dun1.getEnemy().size()){
            System.out.println(party.getPlayerName() + "はダンジョンを制覇した!");//3
            System.out.println("*** GAME CLEARED! ***");
        }else{
            System.out.println("*** GAME OVER ***");
        }
        System.out.println("倒したモンスター数 = " + duncount);
    }
    public static int goDungeon(Party party, Dungeon dun){
        int bcount = 0;
        party.setPlayerLev(bcount);
        System.out.println(party.getPlayerName() + "はダンジョンに到着した");
        for(Monster m : dun.getEnemy()){
            bcount += doBattle(party, m);
            party.setPlayerLev(bcount);
            if(party.getPartyHp() <= 0){
                System.out.println(party.getPlayerName() + "はダンジョンから逃げ出した･･･");//2
                break;
            }else{
                System.out.println(party.getPlayerName() + "はさらに奥へと進んだ\n");
                System.out.println("===============\n");
            }
        }
        return bcount;
    }
    /**
     * 
     * @param party　味方パーティー
     * @param m　敵モンスター(一体)
     * @return 敵モンスターの撃破数。
     */
    public static int doBattle(Party party, Monster m){
        BattleField bat = new BattleField(party, m);
        int win = 0;
        System.out.println(m.printname() +"が現れた!");
        do{
            onPlayerTurn(bat, bat.getPlayerParty(), bat.getMonster());
            if(m.getHp() <= 0){
                System.out.println(m.printname() + "に勝利した!");
                win++;
                break;
            }onEnemyTurn(bat.getPlayerParty(), bat.getMonster(), bat);
            if(party.getPartyHp() <= 0){
                 System.out.println(party.getPlayerName() + "は倒れた･･･"); //1
                 win += 0;
                break;
            }
        }while(party.getPartyHp() > 0);
        return win;
    }
    /**
     * 
     * @param bat
     * @param party
     * @param m
     */
    @SuppressWarnings("resource")
	public static void onPlayerTurn(BattleField bat, Party party, Monster m){
        System.out.println("\n【"+ party.getPlayerName() +"のターン】");
        bat.showBattleField(party);
        String command;
        boolean check;
        do{
            System.out.print("コマンド？>");
            command = new java.util.Scanner(System.in).nextLine();
            check = checkValidCommand(command);
        } while (check == false);
        char[] cm = command.toCharArray();
        bat.moveGem(cm, false);
        evaluateGems(bat);
    }
    /**
     * 
     * @param party
     * @param m
     * @param bat
     */
    public static void onEnemyTurn(Party party, Monster m, BattleField bat){
        System.out.println("\n【"+ m.printname() +"のターン】");
        doEnemyAttack(party, bat);
    }
    /**
     * 
     * @param m
     * @param bat
     * @param fellows
     * @param com
     */
    public static void doAttack(Monster m, BattleField bat, List<Monster> fellows, int com){
        for(int i = 0; i < fellows.size(); i++){
            int dmg = bat.calcAttackDamage(fellows.get(i), com);
            m.calcHp(-(dmg));
            System.out.println(fellows.get(i).printname() + "の攻撃！\n" + dmg + "のダメージを与えた");
            
        }
    }
    /**
     * 
     * @param party
     * @param bat
     */
     public static void doEnemyAttack(Party party, BattleField bat){
        int pdmg = bat.calcEnemyAttackDamage(party);
        party.calcPartyHp(-(pdmg));
        System.out.println(bat.getMonster().printname() + "の攻撃\n" + pdmg + "のダメージを受けた");
    }
    /**
     * 
     * @param party
     * @param bat
     * @param com
     */
     public static void doRecover(Party party, BattleField bat, int com){
            int rcv = bat.calcRecoverDamage(com);
            party.calcPartyHp(rcv);
            if(party.getPartyHp() > party.getPartyMaxHp()){
                party.setPartyHP(party.getPartyMaxHp());
                System.out.println("HPが最大回復した");
            }else{
                System.out.println("HPが" + rcv + "回復した");
            }
    }
    /**
     * 
     * @param command
     * @return 引数commandが判定条件に合っているかどうかの真偽値
     */
    public static boolean checkValidCommand(String command){
        final String[] badpatterns = {"AA", "BB", "CC", "DD", "EE", "FF", "GG", "HH", "II", "JJ", "KK","LL" , "MM", "NN"};
        for (String str : badpatterns){if(command.matches(str)){return false;}}//同じ文字の繰り返し(2回)のみを抽出してはねる正規表現の記述法を入れる
        return command.matches(("[A-N]{2}"));
    }
    /**
     * 
     * @param bat
     */
    public static void evaluateGems(BattleField bat){
       int combo = 0;
        while(BanishInfo.checkBanishable(bat)){
            combo++;
            // System.out.println(combo);
            BanishInfo.banishGems(bat, combo);
            if(combo > 1){bat.printCombo(combo);}
            BanishInfo.shiftGems(bat);
            for(int i = 0; i < BattleField.MAX_GEMS; i++){
                if(bat.getGems(i).getElement() == EMPTY){
                    bat.spawnGems(i);
                }
            }
            bat.printGems();
        }
    }
}
