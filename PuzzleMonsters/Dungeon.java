package PuzzleMonsters;

import java.util.List;
/**ゲーム内の敵モンスターが生息するダンジョンを表すクラス。 */
public class Dungeon {
    /**Dungeonクラスの唯一のフィールド。 */
    private List<Monster> enemys;
    
    public void setEnemy(List<Monster> mons){this.enemys= mons;}

    public List<Monster> getEnemy(){return this.enemys;}
    @Override
    public boolean equals(Object o){
        if(this == o){return true;}
        if (o instanceof Dungeon){
            Dungeon dun = (Dungeon) o;
            if(this.enemys.equals(dun.enemys)){
                return true;
            }
        }return false;
    }
    /** */
    @Override
    public String toString(){
        String dunmsg = null;
        for (Monster m : this.getEnemy()){
            dunmsg += m.toString() + "\n";
        }
        return dunmsg;
    }
}

