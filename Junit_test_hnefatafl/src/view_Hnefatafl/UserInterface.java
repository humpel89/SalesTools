package view_Hnefatafl;

import java.util.Scanner;

public class UserInterface {

    private Scanner sc = new Scanner(System.in);
    private String input;


    public String getUserInput(){
        input = sc.nextLine();
        input = input.toUpperCase();
        input = input.trim();
        return input;
    }
    /**
     * These methods functions as a substitute for "System.out.print(ln)" 
     * and allow for easy addition of a gui later on. by only these rows the text output
     * location can be changed. 
     * @param text
     */
    public void printText(String text){
        System.out.print(text);
    }
    public void printTextln(String text){
        System.out.println(text);
    }
    public void printTextln(){
        System.out.println();
    }
}
