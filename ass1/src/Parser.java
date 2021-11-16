import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

class Parser {
    String commandName="";
    String[] args;
    ArrayList<String> foundCommand = new ArrayList<>();

    public boolean parse(String input) throws IOException {
        boolean check = false;
        String[] options = input.split(" ");
        args = new String[options.length-1];
        help();
        if(foundCommand.contains(options[0]))check=true;
        if(check){
            System.out.println("here");
            commandName = options[0];
            for (int i=1,k=0;i<options.length;i++,k++){
                args[k] = options[i];
            }
        }
        System.out.println(commandName);
        System.out.println(Arrays.toString(args));
        return check;
    }

    public String help(){
        ArrayList<String> s =new ArrayList<>();
        s.add("echo");
        s.add("exit");//
        s.add("pwd");//
        s.add("cd");//
        s.add("cd ..");//
        s.add("ls");//
        s.add("ls -r");//
        s.add("mkdir");//
        s.add("rmdir");//
        s.add("touch");//
        s.add("cp");//
        s.add("cp -r");//
        s.add("rm");//e
        s.add("cat");//
        s.add(">");//
        s.add(">>");//
        foundCommand.addAll(s);
        return s.toString();
    }

    public String getCommandName(){
        return commandName;
    }

    public String[] getArgs(){
        return args;
    }
}