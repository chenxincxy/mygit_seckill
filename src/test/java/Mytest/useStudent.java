package Mytest;

public class useStudent {
    private Student stu;

    public static void main(String args[]){
        useStudent use=new useStudent();
        use.method();
    }
    public void method(){
        System.out.println(stu.getAge());
    }


}
