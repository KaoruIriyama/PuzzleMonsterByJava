package PuzzleMonsters;
/** モンスターや宝石が持つ６つの属性を表す列挙型。 */
public enum Element {
    /** ６つの属性の一覧は以下の通り(カッコ内はそれぞれのフィールド変数symbol(char型)とid(int型))。
     * 火属性('$', 0)、水属性('~', 1)、風属性('@', 2)、土属性('#', 3)、命属性('&', 4)、無属性(' ', 5) 
     */
    FIRE('$', 0), 
    WATER('~', 1),
    WIND('@', 2), 
    EARTH('#', 3), 
    LIFE('&', 4), 
    EMPTY(' ', 5);
    /** 
     * 各属性はフィールドとしてchar型変数symbolとint型変数idをもつ。 
     */
    private char symbol;
    private int id;
    /**
     *  Elementのprivateコンストラクタ。 
     */
    private Element(char symbol, int id) {   //コンストラクタはprivateで宣言
        this.symbol = symbol;
        this.id = id;
    }
    /**
     * idを引数にして、対応する属性のElementインスタンスを返す静的メソッド。
     * @param id　int型変数(0 ~ 5まで)
     * @return　対応する属性のElementインスタンス
     */
    public static Element callElement(int id){
        Element ele = null;
        for (Element e : Element.values()) {
            if (e.getId() == id) { // id が一致するものを探す
                ele = e;
            }
        }
        return ele;
    }
   /**
    * idを引数として渡すと、対応するElementの持つsymbolを返す静的メソッド。
    * @param id Elementのid(0 ~ 5までのint型整数)
    * @return　idに対応するchar型変数
    */
    public static char callSymbol(int id){
        char sym = '?';
        for (Element e : Element.values()) {
            if (id == e.getId()) { // id が一致するものを探す
                sym = e.getSymbol();
            }
        }
        return sym;
    }
    /** 攻撃側の属性と防御側の属性を引数として渡すと、対応する属性攻撃補正倍率をdouble型の数値で返す静的メソッド。
     *  火属性が風属性を、風属性が土属性を、土属性が水属性を、水属性が火属性を攻撃したときは補正倍率が2.0倍になる(攻撃有利)。
     * 上記の攻撃側と防御側が逆転した場合、補正倍率は0.5倍になる(攻撃不利)。
     * 上記の組み合わせ以外の補正倍率は1.0倍になる(等倍)。
     * 
     * @param atk 攻撃側の属性
     * @param dfs 防御側の属性
     * @return 対応する属性攻撃補正倍率(2.0, 0.5, 1.0 のいずれか)
     */
    public static double elementBoost(Element atk, Element dfs){//攻撃側が先
        double[][] boost = {{1.0, 0.5, 2.0, 1.0, 1.0, 1.0},//火属性が攻撃したときの防御側属性補正
                            {2.0, 1.0, 1.0, 0.5, 1.0, 1.0},//水属性が攻撃したときの防御側属性補正
                            {0.5, 1.0, 1.0, 2.0, 1.0, 1.0},//風属性が攻撃したときの防御側属性補正
                            {1.0, 2.0, 0.5, 1.0, 1.0, 1.0},//土属性が攻撃したときの防御側属性補正
                            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0},//命属性が攻撃したときの防御側属性補正
                            {1.0, 1.0, 1.0, 1.0, 1.0, 1.0} //無属性が攻撃したときの防御側属性補正
                        };
    return boost[atk.getId()][dfs.getId()];}
    /**
     * フィールド変数symbolのgetterメソッド
     * @return　フィールド変数symbol
     */
    public char getSymbol() { return this.symbol; }
    /**
     * フィールド変数idのgetterメソッド
     * @return　フィールド変数id
     */
    public int getId() { return this.id; }
}

