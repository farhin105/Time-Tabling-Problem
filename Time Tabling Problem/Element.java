/**
 * Created by User on 11/12/2017.
 */
public class Element {
    public int teacher;
    public int class_no;
    public int room;
    public int credit;

    Element(){};
    Element(int teacher,int class_no,int room,int credit){
        this.teacher=teacher;
        this.class_no=class_no;
        this.room=room;
        this.credit=credit;
    }
    void PrintElement(){
        System.out.println("In Element, teacher="+teacher+" class="+class_no+"  room="+room+"   credit="+credit);
    }


}
