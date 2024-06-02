package PuzzleMonsters;
/**バトルフィールド内で動かす宝石を表すクラス。 */ 
public class Gem {
    /*Gemクラスの唯一のフィールド。 */
    private Element element;
    /**
     * Gemクラスのコンストラクタ。
     * 列挙型Elementの各列挙型に対応する整数(0から5まで)を引数として渡すと、
     * その整数をidフィールドに持つElementをフィールドとして格納したGemインスタンスが生成される。
     * @param n 列挙型Elementの各列挙型に対応する整数(0から5まで)
     * @see PuzzleMonsters.Element
     */
    public Gem(int n){
        this.element = Element.callElement(n);
    }
    /**
     * Gemクラスの変数elementのsetter。
     * @param element Gemインスタンスに格納するElement列挙型
     */
    public void setElement(Element element) {
        this.element = element;
    }
    /**
     * Gemクラスの変数elementのgetter。
     * @return　そのGemインスタンスが持つElement列挙型
     */
    public Element getElement() {
        return this.element;
    }
    /**
     * Gemをその属性色で染めて返すメソッド。内部でElement列挙型のdyeElement()メソッドを使用
     * @return　そのGemインスタンスにdyeElement()メソッドを使用したString型インスタンス
     */

    public String dyeGem() {
    	return this.getElement().dyeElement(String.valueOf(this.getElement().getSymbol()));
    }
    
    /**
     * Gemクラスのequalsメソッド。
     * フィールド変数であるElement列挙型が等値であるとき、そのインスタンスを等しいと判定する。
     */
    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if (o instanceof Gem){
            Gem g = (Gem) o;
            if(this.element == g.element){
                return true;
            }
        }return false;
    }
}
