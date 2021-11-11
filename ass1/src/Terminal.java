import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {
    Parser parser;
    private String path ;

    public Terminal(){
        this.path = System.getProperty("user.dir");
    }
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
    //تنازلي
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

    private int countSpaces(String s){
        int spaces=0;
        for (int i=0;i<s.length();i++){
            if (s.charAt(i)==' ')spaces++;
        }
        return spaces;
    }

    public void mkdir(String s) throws IOException {
        //ahmed.txt ahned.cpp ah.pdf
//        int spaces=0;
//        for (int i=0;i<s.length();i++){
//            if (s.charAt(i)==' ')spaces++;
//        }
        int spaces = countSpaces(s);
        int i=0;
        for (int j=0;j<=spaces;j++){
            String sub="";
            while (s.charAt(i)!=' '){
                sub+=s.charAt(i);
                i++;
                if(i==s.length())break;
            }
            i++;
            boolean check = false;
            for (int k =0;k<sub.length();k++){
                if (sub.charAt(k)=='.'){
                    check = true;
                    break;
                }
            }
            File newFile = null;
            if(check){
                 newFile = new File(path+"\\"+sub);
                if(newFile.createNewFile()){
//                echo(path+"\\"+sub);
                    echo(sub + " created");
                }else{
//                echo(path+"\\"+sub);
                    echo(sub + " is already exist");
                }
            }else{
                if (new File(path + "\\" +sub).exists()){
                    echo(sub +" is already exist");
                }else{
                    Files.createDirectories(Paths.get(path +"\\"+sub));
                    echo(sub + " is created");
                }

            }
        }
    }

    public void rmdir(String s) throws IOException {
        //rmdir ahmed.txt Sayed.txt
        int spaces = countSpaces(s);
        int i=0;
        for (int j=0;j<=spaces;j++){
            String sub="";
            while (s.charAt(i)!=' '){
                sub+=s.charAt(i);
                i++;
                if(i==s.length())break;
            }
            i++;

            boolean check = false;
            for (int k =0;k<sub.length();k++){
                if (sub.charAt(k)=='.'){
                    check = true;
                    break;
                }
            }
            File newFile = null;
            if(check){
                newFile = new File(path+"\\"+sub);
                if(newFile.exists()){
                    if(newFile.length()==0){
                        echo(sub + " is Empty not allowed remove it with this command");
                    }else{
                        newFile.delete();
                        echo(sub + " deleted successfully");
                    }
                }
            }else if(!check){
                File directory = new File(path+"\\"+sub);
                if (directory.length()==0){
                    boolean result = directory.delete();
                    if(result) {
                        echo(sub+" is Deleted");
                    }
                    else {
                        echo(sub+" not empty to delete");
                    }
                }

            }
            else{
                echo(sub + " not exist!");
            }

        }

    }

    public void touch(String paths) throws IOException {
        //ahmed\\text.txt
        int i=paths.length()-1;
        StringBuilder file = new StringBuilder();

        while(paths.charAt(i)!='\\'){
            file.append(paths.charAt(i));
            paths = paths.substring(0,i);
            i--;
        }//text.txt
        file.reverse();
        boolean check = false;
        for (int k =0;k<file.length();k++){
            if (file.charAt(k)=='.'){
                check = true;
                break;
            }

        }
        File obj = new File(paths);
        if(obj.isDirectory() && check){
            if(new File(String.valueOf(paths+file)).exists()){
                echo("this file already exists before");
            }else {
                File created = new File(paths+file);
                created.createNewFile();
                echo(file + " is created");
            }
        }else if(!check){
            echo("Enter valid File (Ex. newfile.Extension)");
        }
        else{
            echo("invalid path!");
        }

    }

    public void cp(File source , File dest) throws IOException {
        File f1 = new File(path +"\\"+source),f2 = new File(path+"\\"+dest);
        if(f1.exists() && f2.exists()){
            FileInputStream sor = new FileInputStream(path +"\\"+source);
            FileOutputStream des = new FileOutputStream(path+"\\"+dest);
            int i=0;
            String content="";
            while ((i=sor.read())!=-1){
                content+=(char)i;
            }
            sor.close();
            byte[] cons = content.getBytes();
            des.write(cons);
            des.flush();
            des.close();
            echo(source +" copied into " + dest);
        }else{
            if(!f1.exists()){
                echo(source + " not exist");
            }if(!f2.exists()){
                echo(dest + " not exist");
            }
        }

    }
    //بتضرب الوسخه
    public void cpr(File source , File dest) throws IOException {
        File src = new File(path +"\\"+source);
        File des = new File(path+"\\"+dest);

        File[] listOfFiles = src.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                File copy = new File(path+"\\"+des +"\\" +listOfFiles[i].getName());
                copy.createNewFile();
            } else if (listOfFiles[i].isDirectory()) {

            }
        }
    }

    public static void main(String[] args) throws IOException {
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
                case "ls":
                case "LS":
                    T.ls();
                    break;
                case "ls -r":
                case "LS -R":
                    T.lsr();
                    break;
                default:
                    //cd ahmed
                    if(options.charAt(0)=='c' && options.charAt(1)== 'd' && options.charAt(2)== ' '){
                        String p;
                        p=options.substring(3);
                        String[] arr = new String[1];
                        arr[0] = p;
                        T.cd(arr);
                        break;
                    }else if (options.equals("exit") || options.equals("EXIT")){
                        break;
                    }else if(options.charAt(0)=='m' && options.charAt(1)=='k' && options.charAt(2)=='d'){
                        String create = options.substring(6,options.length());
                        T.mkdir(create);
                    }
                    else if(options.charAt(0)=='r' && options.charAt(1)=='m' && options.charAt(2)=='d'){
                        String delete = options.substring(6,options.length());
                        T.rmdir(delete);
                    }
                    else if(options.charAt(0)=='t' && options.charAt(1)=='o' && options.charAt(2)=='u'){
                        //touch ahmed\\text.txt
                        String path = options.substring(6,options.length());
                        T.touch(path);
                        //cp -r ahmed sayed
                    }else if(options.charAt(0)=='c' && options.charAt(1)=='p' && options.charAt(2)==' ' && options.charAt(3) =='-'){
                        String p = options.substring(6,options.length());
                        String f = "";
                        int i=0;
                        while (p.charAt(i)!=' '){
                            f+=p.charAt(i);
                            i++;
                        }
                        p=p.substring(i+1,p.length());
                        File f1 = new File(f);
                        File f2 = new File(p);
                        T.cpr(f1,f2);
                    }
                    //cp ahmed.txt sayed.txt
                    else if(options.charAt(0)=='c' && options.charAt(1)=='p' && options.charAt(2)==' '){
                        String p = options.substring(3,options.length());
                        String f = "";
                        int i=0;
                        while (p.charAt(i)!=' '){
                            f+=p.charAt(i);
                            i++;
                        }
                        p=p.substring(i+1,p.length());
                        File f1 = new File(f);
                        File f2 = new File(p);
                        T.cp(f1,f2);
                    }
                    else{
                        T.echo("invalid command!");
                    }

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