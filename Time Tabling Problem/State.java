import java.util.ArrayList;

/**
 * Created by User on 11/12/2017.
 */
public class State {
    public ArrayList<Element> routine[][];
    public int cost;
    public int pivot;
    State(){}
    State(int day,int period){
        ArrayList<Element> r[][]=new ArrayList[day][period];
        this.routine=r;
        for(int i=0;i<routine.length;i++){
            for(int j=0;j<routine[i].length;j++){
                routine[i][j]=new ArrayList<>();
            }
        }
    }

    void PrintState()
    {
        for(int i=0;i<routine.length;i++){
            for(int j=0; j<routine[i].length;j++){
                for (int k=0;k<routine[i][j].size();k++){
                    routine[i][j].get(k).PrintElement();

                }System.out.print("  --- ");
            }
            System.out.println("***");
        }
    }

    int countCost(int num_teacher,int num_class,int num_room){
    int teacher_conflict=0,class_conflit=0,room_conflict=0;
        for(int i=0;i<routine.length;i++){
            for (int j=0;j<routine[i].length;j++){
                int teachers[]=new int[num_teacher];
                for(int m=0;m<num_teacher;m++)teachers[m]=0;
                int classes[]=new int[num_class];
                for(int m=0;m<num_class;m++)classes[m]=0;
                int rooms[]=new int[num_room];
                for(int m=0;m<num_room;m++)rooms[m]=0;
                for(int k=0;k<routine[i][j].size();k++){
                    teachers[(routine[i][j].get(k).teacher)-1]++;
                    classes[(routine[i][j].get(k).class_no)-1]++;
                    rooms[(routine[i][j].get(k).room)-1]++;
                }
                for(int m=0;m<num_teacher;m++){
                    if(teachers[m]!=0)teacher_conflict=teacher_conflict+teachers[m]-1;
                }
                for(int m=0;m<num_class;m++){
                    if(classes[m]!=0)class_conflit=class_conflit+classes[m]-1;
                }
                for(int m=0;m<num_room;m++){
                    if(rooms[m]!=0)room_conflict=room_conflict+rooms[m]-1;
                }
            }
        }

        int total = teacher_conflict+class_conflit+room_conflict;
        this.cost=total;
        return total;
    }
    int countConflict(int num_teacher,int num_class,int num_room,ArrayList<Element> elements){
        int teacher_conflict=0,class_conflit=0,room_conflict=0;

                int teachers[]=new int[num_teacher];
                for(int m=0;m<num_teacher;m++)teachers[m]=0;
                int classes[]=new int[num_class];
                for(int m=0;m<num_class;m++)classes[m]=0;
                int rooms[]=new int[num_room];
                for(int m=0;m<num_room;m++)rooms[m]=0;
                for(int k=0;k<elements.size();k++){
                    teachers[elements.get(k).teacher-1]++;
                    classes[elements.get(k).class_no-1]++;
                    rooms[elements.get(k).room-1]++;
                }
                for(int m=0;m<num_teacher;m++){
                    if(teachers[m]!=0)teacher_conflict=teacher_conflict+teachers[m]-1;
                }
                for(int m=0;m<num_class;m++){
                    if(classes[m]!=0)class_conflit=class_conflit+classes[m]-1;
                }
                for(int m=0;m<num_room;m++){
                    if(rooms[m]!=0)room_conflict=room_conflict+rooms[m]-1;
                }


        int total = teacher_conflict+class_conflit+room_conflict;
        return total;
    }

    void copyState(State state){
        //this.routine=state.routine;
        for(int i=0;i<state.routine.length;i++){
            for(int j=0;j<state.routine[i].length;j++){
                //for(int k=0;k<state.routine[i][j].size();k++){
                    //this.routine[i][j].add(state.routine[i][j].get(k));
                   // this.routine[i][j]= state.routine[i][j];
                    this.routine[i][j].clear();
                    this.routine[i][j].addAll(state.routine[i][j]);
                //}
            }
        }
        this.cost=state.cost;
    }
    void setPivot(int pivot){
        this.pivot=pivot;
    }
    int getPivot(){
        return this.pivot;
    }

}
