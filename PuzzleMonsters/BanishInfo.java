package PuzzleMonsters;

import static PuzzleMonsters.Element.*;
/** バトルフィールド内の宝石を消す際の配置や属性など、パズルデータやメソッドをまとめて受け持つクラス。 */
public class BanishInfo {
    /**
     * 
     */
    private static Element gemelem = EMPTY;
    private static int start = 0;
    private static int seq = 0;
    private static boolean judge = false;

    public void setCheck(boolean boo){BanishInfo.judge = boo;}
    public boolean getCheck(){return BanishInfo.judge;}
    public static Element getGemelem(){return BanishInfo.gemelem;}
    public static int getSeq(){return BanishInfo.seq;}
    /**
     * バトルフィールド内の宝石の並びをチェックし、同じ属性の宝石が3個以上並んでいればその情報をフィールド変数に格納し、真偽値trueを返す静的メソッド。
     * @param bat
     * @return
     */
    public static boolean checkBanishable(BattleField bat){
        int loopc = 0;//
        for(int n = 0; n < (BattleField.MAX_GEMS - 2); n++){
            if(bat.getGems(n).equals(bat.getGems(n + 1)) 
            && bat.getGems(n + 1).equals(bat.getGems(n + 2)) 
            && bat.getGems(n).getElement() != EMPTY){
                if (BanishInfo.start <= 0 && BanishInfo.seq < 1 && BanishInfo.gemelem == EMPTY){
                    BanishInfo.judge = true; 
                    BanishInfo.start = n; 
                    BanishInfo.gemelem = bat.getGems(n).getElement(); 
                    BanishInfo.seq += 3;
                }else{
                    if(loopc > 0 && BanishInfo.gemelem == bat.getGems(n).getElement()){
                        BanishInfo.seq++;
                    }
                }//開始位置は正しいが連続数がおかしい時がある(余計に数えている) おそらくここの分岐条件が鍵 →解決。
                // System.out.println("現在:" + n);
            }
            loopc++;
        }
        // System.out.println(BanishInfo.toString());//テスト用
        return BanishInfo.judge;
    }
    /** フィールド変数に格納された情報に従い、バトルフィールド内の宝石を無属性の宝石に置き換える静的メソッド。 */
    /**
     * 
     * @param bat
     * @param combo
     */
    public static void banishGems(BattleField bat, int combo){
        Gem emp = new Gem(EMPTY.getId());//無属性の宝石を準備
        for(int g = BanishInfo.start; g < (BanishInfo.start + BanishInfo.seq) ; g++){
            bat.setGems(g, emp);//checkBanishableで格納した消すべき宝石の開始位置から、連続数だけ無属性の宝石を格納する(宝石を消す)
        }

        bat.printGems();
        //消した宝石の属性が命属性ならば回復処理、それ以外ならば対応する味方モンスターによる攻撃処理
        if(BanishInfo.gemelem == LIFE){
            PuzzleMonsters.doRecover(bat.getPlayerParty(), bat, combo);
        }else{
            for(int i = 0; i < bat.getPlayerParty().callMonster(BanishInfo.getGemelem()).size(); i++){
                PuzzleMonsters.doAttack
                (bat.getMonster(), bat, bat.getPlayerParty().callMonster(BanishInfo.getGemelem()), combo);
            }
        }
    }
    /**
     * プライベートメソッドcountGems()の戻り値に従い、バトルフィールド内の宝石を左詰めで移動させ、空欄を埋める静的メソッド。
     * @param bat
     */
    public static void shiftGems(BattleField bat){
        int[] array = countGems(bat, EMPTY);
        for(int g = 0; g < array[1] ; g++){
            char[] shift = {BattleField.chara[array[0]], BattleField.chara[BattleField.MAX_GEMS  - 1] };
            bat.moveGem(shift, BanishInfo.judge);
        }
        bat.printGems();
        BanishInfo.gemelem = EMPTY; BanishInfo.start = 0; BanishInfo.seq = 0; BanishInfo.judge = false;
    }
    /**
     * banishGems()で宝石を消した開始位置と連続数を調べ、int配列に詰めて戻り値として返すプライベート静的メソッド。
     * @param bat
     * @param e
     * @return
     */
     private static int[] countGems(BattleField bat, Element e){
        int firstgem = 0;//
        int gemseries = 0;//
        for(int i = 0; i < BattleField.MAX_GEMS; i++){
            if(bat.getGems(i).getElement().equals(e) ){
                if(gemseries == 0){
                    firstgem = i;
                }gemseries++;
            }
        }
        int[] countGems = {firstgem, gemseries};
        return countGems;
    }
    @Override
    public String toString(){
        return "属性:" + BanishInfo.gemelem+ "/開始位置:" + BanishInfo.start + "/連続数:" + BanishInfo.seq;
    }
}
