import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * Created by User on 11/12/2017.
 */
public class LocalBeamSearch {

    Comparator<State> comparator = new CostComparator();
    PriorityQueue<State> queue = new PriorityQueue<State>(comparator);
    ArrayList<State> list = new ArrayList<State>();
    public int num_node=5,period=6,day=5;
    public int num_teacher,num_class,num_room;
    ArrayList<Element> elements ;
    LocalBeamSearch(){}
    LocalBeamSearch(ArrayList<Element> elements,int num_teacher,int num_class,int num_room){
        this.elements=elements;
        this.num_teacher=num_teacher;
        this.num_class=num_class;
        this.num_room=num_room;
    }

    public static void selectionSort(State[] arr){
        for (int i = 0; i < arr.length - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < arr.length; j++){
                if (arr[j].cost < arr[index].cost){
                    index = j;//searching for lowest index
                }
            }
            int smallerNumber = arr[index].cost;
            arr[index].cost = arr[i].cost;
            arr[i].cost = smallerNumber;
        }
    }

    State getInitialState(ArrayList<Element> elements){
        State state=new State(day,period);
        for(int i=0;i<elements.size();i++){
            for(int j=0;j<elements.get(i).credit;j++){
                Random random = new Random();
                int index_i=random.nextInt(day);
                int index_j=random.nextInt(period);
                state.routine[index_i][index_j].add(elements.get(i));
                state.countCost(num_teacher,num_class,num_room);
            }
        }
        //state.PrintState();
        return state;
    }
    void PrintState(State state,PrintWriter out)
    {
        for(int i=0;i<state.routine.length;i++){
            for(int j=0; j<state.routine[i].length;j++){
                for (int k=0;k<state.routine[i][j].size();k++){
                    //state.routine[i][j].get(k).PrintElement();
                    out.println("In Element, teacher=" + state.routine[i][j].get(k).teacher + " class=" + state.routine[i][j].get(k).class_no + "  room=" + state.routine[i][j].get(k).room + "   credit=" + state.routine[i][j].get(k).credit);

                }out.print(state.countConflict(num_teacher,num_class,num_room,state.routine[i][j])+"  --- ");
            }
            out.println("***");
        }
    }
    int getStochasticSuccessor(State state){
        int max=0;
        PrintWriter out=null;
        try {
            out = new PrintWriter(new FileWriter("output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(state.countCost(num_teacher,num_class,num_room));
        PrintState(state, out);
        System.out.println("in get    state cost = " + state.countCost(num_teacher,num_class,num_room));
        State state1=new State(day,period);
        state1.copyState(state);
        System.out.println("in get    state1 cost = " + state1.cost);
        for(int i=0;i<state1.routine.length;i++){
            for (int j=0;j<state1.routine[i].length;j++){
                int con=state1.countConflict(num_teacher,num_class,num_room,state1.routine[i][j]);
                out.println("*****************i=" + i + " and j=" + j + "  here conflict is = " + con + "    ***************");
                if(con!=0){
                    for (int k=0;k<state1.routine[i][j].size();k++){
                        Element element= state1.routine[i][j].get(k);
                        //System.out.println("before removing length is :"+state1.routine[i][j].size());
                        state1.routine[i][j].remove(element);
                        //System.out.println("after removing length is :" + state1.routine[i][j].size());
                        out.print("----------------------------");//element.PrintElement();
                        out.println("In Element, teacher=" + element.teacher + " class=" + element.class_no + "  room=" + element.room + "   credit=" + element.credit);

                        for(int m=0;m<day;m++){
                            for (int n=0;n<period;n++){
                                if(m==i && n==j){}
                                else{
                                    out.println("here m= " + m + " and n=" + n);
                                    state1.routine[m][n].add(element);
                                    //state1.PrintState();
                                    PrintState(state1, out);
                                    state1.countCost(num_teacher, num_class, num_room);
                                    out.println("cost of state =" + state.cost + "  & state1 =" + state1.cost);
                                    State state2=new State(day,period);
                                    state2.copyState(state1);
                                    list.add(state2);
                                    if(state2.cost>max)max=state2.cost;
                                    state1.routine[m][n].remove(element);
                                }

                            }
                        }state1.routine[i][j].add(element);
                        //System.out.println("after adding length is :" + state1.routine[i][j].size());
                    }
                }

            }
        }
        return max;
    }

    PriorityQueue<State> getSuccessor(State state){
        PrintWriter out=null;
        try {
            out = new PrintWriter(new FileWriter("output.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(state.countCost(num_teacher,num_class,num_room));
        PrintState(state, out);
        System.out.println("in get    state cost = " + state.countCost(num_teacher,num_class,num_room));
        State state1=new State(day,period);
        state1.copyState(state);
        System.out.println("in get    state1 cost = " + state1.cost);
        for(int i=0;i<state1.routine.length;i++){
            for (int j=0;j<state1.routine[i].length;j++){
                int con=state1.countConflict(num_teacher,num_class,num_room,state1.routine[i][j]);
                out.println("*****************i=" + i + " and j=" + j + "  here conflict is = " + con + "    ***************");
                if(con!=0){
                    for (int k=0;k<state1.routine[i][j].size();k++){
                        Element element= state1.routine[i][j].get(k);
                        //System.out.println("before removing length is :"+state1.routine[i][j].size());
                        state1.routine[i][j].remove(element);
                        //System.out.println("after removing length is :" + state1.routine[i][j].size());
                        out.print("----------------------------");//element.PrintElement();
                        out.println("In Element, teacher=" + element.teacher + " class=" + element.class_no + "  room=" + element.room + "   credit=" + element.credit);

                        for(int m=0;m<day;m++){
                            for (int n=0;n<period;n++){
                                if(m==i && n==j){}
                                else{
                                    out.println("here m= " + m + " and n=" + n);
                                    state1.routine[m][n].add(element);
                                    //state1.PrintState();
                                    PrintState(state1,out);
                                    state1.countCost(num_teacher, num_class, num_room);
                                    out.println("cost of state =" + state.cost + "  & state1 =" + state1.cost);
                                    State state2=new State(day,period);
                                    state2.copyState(state1);
                                    queue.add(state2);
                                    state1.routine[m][n].remove(element);
                                }

                            }
                        }state1.routine[i][j].add(element);
                        //System.out.println("after adding length is :" + state1.routine[i][j].size());
                    }
                }

            }
        }
        return queue;
    }

    State basicLBS(){
        int pivot=0;
        State state[]= new State[num_node];
        for (int i=0;i<num_node;i++){
            state[i]=new State(day,period);
            state[i]=getInitialState(elements);
            System.out.println("state["+i+"] cost = "+state[i].cost);
        }
        selectionSort(state);
        pivot=state[0].cost;
        //System.out.println("after sorting:");;
        while (true) {
            for (int i = 0; i < num_node; i++) {
                System.out.println("state[" + i + "] cost = " + state[i].cost);
                getSuccessor(state[i]);
                queue.add(state[i]);
            }
            //System.out.println("after  state is "+state[0].cost+ " and peek is "+queue.peek().cost);
            if (state[0].cost <= queue.peek().cost)
            {
                //System.out.println("state is "+state[0].countCost(num_teacher,num_class,num_room)+ " and peek is "+queue.peek().countCost(num_teacher,num_class,num_room));
                //***************************************for random restart*****************************
                pivot=(pivot*30)/100;
                System.out.println("------------------------------------------------------ state[0] is ="+state[0].cost+" and pivot is= "+pivot);
                /*if (state[0].cost>pivot){
                    list.clear();
                    LocalBeamSearch localBeamSearch=new LocalBeamSearch(elements,num_teacher,num_class,num_room);
                    localBeamSearch.stochasticLBS();
                }
                //***************************************for random restart****************************
                */
                state[0].setPivot(pivot);
                return state[0];
            }

            for (int i = 0; i < num_node; i++) {
                //System.out.println("inserting");
                //State s=;
                state[i].copyState(queue.remove());
                //System.out.println("after inserting  state is " + state[i].countCost(num_teacher,num_class,num_room)+ " and peek is "+queue.peek().cost);
            }
            if(state[0].cost==0)return state[0];
            System.out.println("atlast "+state[0].countCost(num_teacher,num_class,num_room)+ " and peek is "+queue.peek().cost);
            queue.clear();
        }
       // System.out.println(queue.contains(state[0])+" and 1st elemnt is "+queue.remove().cost);
        //return state[0];
    }
    State stochasticLBS(){
        int pivot=0;
        State state[]= new State[num_node];
        for (int i=0;i<num_node;i++){
            state[i]=new State(day,period);
            state[i]=getInitialState(elements);
            System.out.println("state["+i+"] cost = "+state[i].cost);
        }
        selectionSort(state);
        //System.out.println("after sorting:");;
        pivot=state[0].cost;
        while (true) {
            int max=0;
            for (int i = 0; i < num_node; i++) {
                System.out.println("state[" + i + "] cost = " + state[i].cost);
                int m=getStochasticSuccessor(state[i]);
                if(m>max)max=m;
                list.add(state[i]);
            }
            int length=list.size();
            for(int i=0;i<length;i++){
                if(list.get(i).cost<state[0].cost){
                    for(int j=0;j<(max+1-list.get(i).cost)*(max+1-list.get(i).cost);j++){
                        list.add(list.get(i));
                    }
                }
            }
            Random rand = new Random();
            State best[]=new State[num_node];
            for(int i=0;i<num_node;i++){
                int rand_index = rand.nextInt(list.size()-1);
                best[i]=list.get(rand_index);
            }
            selectionSort(best);
            if (state[0].cost <= best[0].cost)
            {
                //***************************************for random restart*****************************
                pivot=(pivot*50)/100;
                System.out.println("------------------------------------------------------ state[0] is ="+state[0].cost+" and pivot is= "+pivot);
                /*if (state[0].cost>pivot){
                    list.clear();
                    LocalBeamSearch localBeamSearch=new LocalBeamSearch(elements,num_teacher,num_class,num_room);
                    localBeamSearch.stochasticLBS();
                }
                //***************************************for random restart****************************
                */
                state[0].setPivot(pivot);
                return state[0];
            }

            for (int i = 0; i < num_node; i++) {
                state[i].copyState(best[i]);
                System.out.println("state and best are "+state[i].cost+" & "+best[i].cost);
            }
            //for (int i = 0; i < list.size(); i++) {System.out.println(list.get(i).cost);}
            if(state[0].cost==0)return state[0];
            list.clear();
        }
    }
}
