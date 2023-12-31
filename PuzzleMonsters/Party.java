package PuzzleMonsters;

import java.util.List;
import java.util.stream.Collectors;

public class Party {
    private String playername;
    private List<Monster> fellows;
    private int partyhp;
    private int partymaxhp;
    private int partydfs;
    /**テキストにないオリジナルのフィールド。メインクラス内のgoDungeon()メソッド内で、
     * 倒したモンスター数をレベルとして加算していく。 
     * レベル値はダメージ計算式に用いて、ダメージバランスを実プレイ可能なものに改良した。
     * @see PuzzleMonsters.goDungeon
     * 
     * */
    private int playerlev;//オリジナル要素
    void setPlayerLev(int i){this.playerlev = i;}//オリジナル要素
    int getPlayerLev(){return this.playerlev;}//オリジナル要素
/**
 * Partyクラスのコンストラクタ。
 * プレーヤー名と味方モンスターのリストを因数として渡す。
 * パーティのHP,最大HP,防御力は味方モンスターのパラメータから計算して取得する。
 * @param playername
 * @param fellows
 * @param partyhp
 * @param partymaxhp
 * @param partydfs
 */
    public Party(String playername, List<Monster> fellows){
        this.playername = playername;
        this.fellows = fellows;
        List<Integer> mhp = fellows.stream().map(m -> m.getMaxHp()).collect(Collectors.toList());
        //リストfellowsをストリームに変換し、map()でfellowsのmaxhpフィールドのみをとりだし、collect()で集めてCollectors.toList()でIntegerリストmhpに変換
        int pmhp = mhp.stream().mapToInt(Integer::intValue).sum();
        //リストmhpをストリームに変換し、mapToInt(Integer::intValue)で内部に格納されたIntegerクラスインスタンスを対応する値のint型データに変換し、
        //得られたIntstreamをsum()で合計して出た値をpmhpに代入
        List<Integer> dfs = fellows.stream().map(m -> m.getDFS()).collect(Collectors.toList());
        //リストfellowsをストリームに変換し、map()でfellowsのdfsフィールドのみをとりだし、collect()で集めてCollectors.toList()でIntegerリストdfsに変換
        int pdfs = (dfs.stream().mapToInt(Integer::intValue).sum()) / fellows.size(); 
        //リストdfsをストリームに変換し、mapToInt(Integer::intValue)で内部に格納されたIntegerクラスインスタンスを対応する値のint型データに変換し、
        //得られたIntstreamをsum()で合計して出た値を、さらに元リストfellowsの要素数で割ってpdfsに代入
        this.partyhp = pmhp;
        this.partymaxhp = pmhp;
        this.partydfs = pdfs;
    }
    public String getPlayerName(){return this.playername;}
    public List<Monster> getFellows(){return this.fellows;}
    
    
    public int getPartyHp(){return this.partyhp;}
    public void setPartyHP(int hp){this.partyhp = hp;}
    
    public int getPartyMaxHp(){return this.partymaxhp;}
    public int getPartyDFS(){return this.partydfs;}
    /**
     * 
     * @param php
     */
    public void calcPartyHp(int php){this.partyhp += php;}
    /**
     * 
     * @param party
     */
    public void showParty(Party party){
        System.out.println("<パーティ編成>----------");
        for(Monster m : this.getFellows()){
            System.out.println(m.printname() + " HP= " + m.getHp() + " 攻撃= " + m.getATK() + " 防御= " + m.getDFS());
        }
        System.out.println("-----------------------\n");
    }
    /**
     * Element列挙型を引数として渡すと、そのElemantを持つ味方モンスターのリストを戻り値として返すメソッド。
     * 内部プログラムでは、BanishInfoクラス内で消した宝石の属性に応じた味方の攻撃処理に使用している。
     * @param e Element列挙型
     * @return　そのElemantを持つ味方モンスターのリスト
     * @see PuzzleMonsters.BanishInfo.banishGems
     */
    public List<Monster> callMonster(Element e){
        List<Monster> calledmons = this.getFellows().stream().filter(m -> m.getElement() == e).collect(Collectors.toList());
    return calledmons;}

    @Override
    public boolean equals(Object o){
            if(this == o){return true;}
            if (o instanceof Party){
                Party p = (Party) o;
                if((this.playername.equals(p.playername)) && this.fellows.equals(p.fellows)){
                    return true;
                }
            }return false;
        }
    
    @Override
    public String toString(){
        String partymsg = null;
        return partymsg;
    }
}
