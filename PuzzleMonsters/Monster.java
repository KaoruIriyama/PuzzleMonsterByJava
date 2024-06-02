package PuzzleMonsters;
/** */
public class Monster {
    private String name;
    private int hp;
    private int maxhp;
    private Element e;
    private int atk;
    private int dfs;
/**
 * 
 * @param name モンスターの名前
 * @param hp モンスターの現在HP
 * @param maxhp モンスターの最大HP
 * @param e モンスターの属性
 * @param atk モンスターの攻撃力
 * @param dfs モンスターの防御力
 */
public Monster(String name, int hp, int maxhp, Element e, int atk, int dfs)
    {
        this.name = name;
        this.hp = hp;
        this.maxhp = maxhp;
        this.e = e;
        this.atk = atk;
        this.dfs = dfs;
    }
    public String getName(){return this.name;}
    public int getHp(){return this.hp;}
    public int getMaxHp(){return this.maxhp;}
    public Element getElement(){return this.e;}
    public int getATK(){return this.atk;}
    public int getDFS(){return this.dfs;}
    /**
    *モンスターの名前をその属性色で染めて返すメソッド。内部でElement列挙型のdyeElement()メソッドを使用
     * @return　Monster.nameにdyeElement()メソッドを使用したString型インスタンス
     */
    public String printname(){
        String printname = this.getElement().dyeElement(
        		this.getElement().getSymbol() + this.getName() + this.getElement().getSymbol());
        return printname;
    }
    /**
     * モンスターのHPを加減算するメソッド。
     * @param n　HPから加減する数値(正の値を与えれば加算、負の値を与えれば減算になる)
     */
    public void calcHp(int n){this.hp = this.hp + n;}
    
    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if (o instanceof Monster){
            Monster m = (Monster) o;
            if((this.name.equals(m.name)) && (this.atk == (m.atk)) && (this.dfs == (m.dfs)) 
            && (this.hp == (m.hp)) && (this.maxhp == (m.maxhp)) && (this.e == (m.e))){
                return true;
            }
        }return false;
    }
    /** */
    @Override
    public String toString(){
        String monmsg = this.printname()  + "\n" + "HP=" + this.getHp() + "/" + this.getMaxHp() + "\n";
        return monmsg;}
}
