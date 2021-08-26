import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by User on 11/12/2017.
 */
public class Main {
    public static void main(String[] args){
        Scanner reader1 = null,reader2=null;
        ArrayList<Element> elements =new ArrayList<>();
        int num_teacher=0,num_class=0,num_room=0;
        try {
            reader1 = new Scanner(new File("hdtt/hdtt4note.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            reader2 = new Scanner(new File("hdtt/hdtt4req.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (reader1.hasNext()){
            String str = reader1.nextLine();
            Scanner in = new Scanner(str).useDelimiter("[^0-9]+");
            int integer = in.nextInt();
            if(str.contains("TEACHERS")){num_teacher=integer;}
            if(str.contains("CLASSES")){num_class=integer;}
            if(str.contains("ROOM")){num_room=integer;}
        }
        System.out.println("# teachers = "+ num_teacher +" # of classes "+num_class+" # of room = "+num_room);
        int iteration_req_file=0;
        while (reader2.hasNext()){
            String str = reader2.nextLine();
            System.out.println(str);
            String[] str_split = str.split("  ");
            System.out.println(str_split.length);
            for(int i=0; i<str_split.length;i++){
                System.out.print(str_split[i] + " ");
                if(Integer.parseInt(str_split[i]) != 0) {
                    Element element = new Element((i + 1), (int) (iteration_req_file % num_class + 1), (int) (iteration_req_file / num_class + 1), Integer.parseInt(str_split[i]));
                    //element.PrintElement();
                    elements.add(element);
                }
            }iteration_req_file++;
            System.out.println();
        }
        LocalBeamSearch localBeamSearch =new LocalBeamSearch(elements,num_teacher,num_class,num_room);
        State state=localBeamSearch.basicLBS();
        //State state=localBeamSearch.stochasticLBS();
        /*while (state.cost>state.getPivot()){
            LocalBeamSearch localBeamSearch1 =new LocalBeamSearch(elements,num_teacher,num_class,num_room);
            state=localBeamSearch1.basicLBS();
            //state=localBeamSearch1.stochasticLBS();
        }*/

       // state.PrintState();
        System.out.println(state.cost);

    }
}
