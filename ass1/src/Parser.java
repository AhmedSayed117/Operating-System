import java.io.IOException;
import java.util.ArrayList;

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
            commandName = options[0];
            for (int i=1,k=0;i<options.length;i++,k++){
                args[k] = options[i];
            }
        }
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
        s.add("mkdir");/////
        s.add("rmdir *");//
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