import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {
    Parser parser;
    private String path = System.getProperty("user.dir");

    //1 mark
    public void echo(String e){
        System.out.println(e);
    }

    //1 mark
    int c=0;
    public String pwd(){
        if(c==0){
            path = "user.dir";
            path = System.getProperty(path);
            c++;
        }else{
            path = getPath();
        }
        return path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void cd(String[] args){
        String p = getPath();
        if((args[0].equals(".")) && args[1].equals(".")){
//            System.out.println(p.length());
            int l = p.length();
            while ((l)>=0){
                if(p.charAt(l-1)!='\\'){
                    p = p.substring(0,(l-1));
                    l--;
                }else {
                    p = p.substring(0,(l-1));
                    break;
                }
            }
            System.out.println(p);
            setPath(p);
        }else{
             setPath(p+='\\'+args[0]);
             System.out.println(p);
        }
    }

    public String cd(){
        if(c==0){
            path = "user.dir";
            path = System.getProperty(path);
            c++;
        }else{
            path = getPath();
        }
        return path;
//        path = System.getProperty("user.home");
    }

    //تصاعدي
    public void ls(){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("---------------------------------\n\tFile\nFile name -> : " + listOfFiles[i].getName()+"\nFile path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("---------------------------------\n\tFolder\nFolder name -> : " + listOfFiles[i].getName()+"\nFolder path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
            }
        }
    }

    public void lsr(){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (int i = (listOfFiles.length)-1; i >=0 ; i--) {
            if (listOfFiles[i].isFile()) {
                System.out.println("---------------------------------\n\tFile\nFile name -> : " + listOfFiles[i].getName()+"\nFile path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("---------------------------------\n\tFolder\nFolder name -> : " + listOfFiles[i].getName()+"\nFolder path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
            }
        }
    }


    public void chooseCommandAction(){

    }


    public static void main(String[] args) {
        Terminal T = new Terminal();
        Scanner input = new Scanner(System.in);
        String options="";
        while (!(options.equals("exit"))){
            System.out.print("> ");
            options = input.nextLine();
            switch (options){
                case "pwd":
                case "PWD":
                    T.echo(T.pwd());
                    break;
                case "cd":
                case "CD":
                    T.echo(T.cd());
                    break;
                case "cd..":
                case "CD..":
                    String[] cdArr = new String[2];
                    cdArr[0]=".";
                    cdArr[1]=".";
                    T.cd(cdArr);
                    break;

                case "exit":
                case "EXIT":
                    break;
                case "ls":
                case "LS":
                    T.ls();
                    break;
                case "ls -r":
                case "LS -R":
                    T.lsr();
                    break;

                default:
                    String p;
                    p=options.substring(3);
                    String[] arr = new String[1];
                    arr[0] = p;
                    T.cd(arr);
                    break;
            }
        }

//        Terminal T = new Terminal();
//        T.echo(T.pwd());
//        T.echo(T.pwd());
//        String s;
//        s = input.nextLine();
//        T.setPath(s);
//        T.echo(T.getPath());
//        T.echo(T.getPath());
//        String[] a = new String[100];
//        a[0]=".";
//        a[1]=".";
//        T.cd(a);
//        T.cd(a);
//        T.cd(a);
//        T.cd(a);
//        a[0] = "ahmed\\sayed\\hassan";
//        T.cd(a);
//       System.out.println( T.getPath());

//        String b ="ahmed";
//        System.out.println(b.charAt(4));

    }
}