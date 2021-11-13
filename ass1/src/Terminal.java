import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Terminal {
    Parser parser;
    public int checkCount=0;
    public int checkCount2=0;
    private String path ;

    public Terminal(){
        this.path = System.getProperty("user.dir");
    }
    //1 mark
    public String echo(String e){
        if( checkCount2==0 ){
            System.out.println(e);
            return e;
        }
        checkCount2 = 0;
        return e;
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
    public String ls(){
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        String content="";
        if(checkCount==0){
            for(int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    System.out.println("---------------------------------\n\tFile\nFile name -> : " + listOfFiles[i].getName()+"\nFile path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
                } else if (listOfFiles[i].isDirectory()) {
                    System.out.println("---------------------------------\n\tFolder\nFolder name -> : " + listOfFiles[i].getName()+"\nFolder path -> : "+ listOfFiles[i].getPath()+"\n----------------------------------");
                }
                content+=listOfFiles[i].getName();
                content+="\n";
            }
        }else {
            for (int i = 0; i < listOfFiles.length; i++) {
                content+=listOfFiles[i].getName();
                content+="\n";
            }
        }
        checkCount=0;
        return content;
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

    public void rm(String s){
        File file = new File(path+ "\\" +s);
        if(file.exists()){
            file.delete();
            echo(s + " deleted Successfully");
        }else {
            echo(s + " Not Found!");
        }

    }

    public void cat(String s1) throws IOException {
        FileInputStream file = new FileInputStream(path + "\\"+ s1);
        int i=0;String s="";
        while ((i=file.read())!=-1){
            s+=(char)i;
        }
        echo(s);
    }

    public void cat(String s1,String s2) throws IOException {
        File f1 = new File(path + "\\"+ s1);
        File f2 = new File(path + "\\"+ s2);

        int i=0,j=0;String s="";
        if(f1.exists() && f2.exists()){
            FileInputStream file1 = new FileInputStream(path + "\\"+ s1);
            FileInputStream file2 = new FileInputStream(path + "\\"+ s2);

            while ((i=file1.read())!=-1){
                s+=(char)i;
            }
            s+='\n';
            file1.close();
            while ((j=file2.read())!=-1){
                s+=(char)j;
            }
            file2.close();
            echo(s);
        }else{
            echo("not available files");
        }
    }

    public void write(Terminal obj,String s,String file,String e) throws IOException {
        checkCount = 1;
        checkCount2 = 1;
        File f1 = new File(path + "\\"+file);
        if (f1.exists()){
            FileOutputStream out = new FileOutputStream(f1);
            if(s.equals("pwd")){
                byte[] output = obj.pwd().getBytes();
                out.write(output);
            }else if (s.equals("ls")){
                byte[] output = obj.ls().getBytes();
                out.write(output);
            }else if(s.equals("echo")){
                byte[] output = obj.echo(e).getBytes();
                out.write(output);
            }
        }else {
            File f = new File(path + "\\"+file);
            f.createNewFile();
        }
    }

    public void writeAppend(Terminal obj,String s,String file,String e) throws IOException {
        checkCount = 1;
        checkCount2 = 1;

        FileInputStream fin = new FileInputStream(path +"\\"+file);
        int i=0;String str="";
        while((i=fin.read())!=-1){
            str+=(char)i;
        }
        FileOutputStream fout = new FileOutputStream(path +"\\"+file);
        File f1 = new File(path + "\\"+file);
        if (f1.exists()){

            String ob1 = obj.pwd();
            String ob2 = obj.ls();
            String ob3 = obj.echo(e);

            String fn1 = str +"\n"+ ob1;
            String fn2 = str + "\n"+ob2;
            String fn3 = str +"\n"+ ob3;

            if(s.equals("pwd")){
                fout.write(fn1.getBytes());
            }else if (s.equals("ls")){
                fout.write(fn2.getBytes());
            }else if(s.equals("echo")){
                fout.write(fn3.getBytes());
            }
        }else{
            File f = new File(path + "\\"+file);
            f.createNewFile();
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
//                    T.write(T,"echo","test.txt","sfhsdfgjhsdfgsdhgjhsdfgj");
                    //cat countSpace
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
                    }//rm ahmed.txt
                    else if(options.charAt(0)=='r' && options.charAt(1)=='m' && options.charAt(2)==' '){
                        String s = options.substring(3);
                        T.rm(s);
                    }//cat ahmed
                    else if(options.charAt(0)=='c' && options.charAt(1)=='a' && options.charAt(2)=='t'){
                        String ch = options.substring(4);
                        int countSpace = 0;String str="";
                        for (int i=0;i<ch.length();i++){
                            if (ch.charAt(i)==' ') countSpace++;
                        }
                        if(countSpace==0){
                            T.cat(ch);
                        }//cat ahmed sayed
                        else{
                            int i=0;String str1="",str2="";
                            while (ch.charAt(i)!=' '){
                                str1+=ch.charAt(i);
                                i++;
                            }i++;
                            while (ch.charAt(i)!=' '){
                                str2+=ch.charAt(i);
                                i++;
                                if(i==ch.length())break;
                            }
                            T.cat(str1,str2);
                        }
                        //pwd > file
                        //ls > file
                        //echo ahmed > file
                        //ahmed sayed > file
                    }else if(options.contains(">")){
                        int index = options.indexOf('>');
                        if(options.charAt(index+1)=='>'){
                            if(options.contains("pwd")){
                                String sub = options.substring(7);
                                T.writeAppend(T,"pwd",sub,"");
                            }
                            else if(options.contains("ls")){
                                String sub = options.substring(6);
                                T.writeAppend(T,"ls",sub,"");
                            }
                            else if(options.contains("echo")){
                                options=options.substring(5);
                                String str="";
                                int i=0;
                                for (;options.charAt(i)!='>' && options.charAt(i+1)!='>' && i!=options.length()-1 ;i++){
                                    str+=options.charAt(i);
                                }
                                i+=4;
                                String sub = options.substring(i);
                                T.writeAppend(T,"echo",sub,str);
                            }else{
                                T.echo("invalid command!");
                            }
                        }
                        else {
                            if(options.contains("pwd")){
                                String sub = options.substring(6);
                                T.write(T,"pwd",sub,"");
                            }
                            else if(options.contains("ls")){
                                String sub = options.substring(5);
                                T.write(T,"ls",sub,"");
                            }
                            else if(options.contains("echo")){
                                options=options.substring(5);
                                String str="";
                                int i=0;
                                for (;options.charAt(i)!='>';i++){
                                    str+=options.charAt(i);
                                }
                                i+=2;
                                String sub = options.substring(i);
                                T.write(T,"echo",sub,str);
                            }else{
                                T.echo("invalid command!");
                            }
                        }

                    }//pwd >> file
                    //ls >> file
                    //echo ahmed >> file
                    //ahmed sayed >> file
                    else{
                        T.echo("invalid command!");
                    }

                    }
        }
    }
}