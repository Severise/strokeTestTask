
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String input = sc.next();

        String input = "2[3[x]y]";
        String output = repeat(input);
        System.out.println(output);

        input = "3[xyz]4[xy]z";
        output = repeat(input);
        System.out.println(output);
    }

    public static String repeat(String st) throws NumberFormatException {
        if (st.isEmpty() || st.matches("[a-zA-Z]+"))
            return st;

        int numberOfOpBrackets = 0;
        int clBracketIdx = 0;

        for (int i = st.indexOf('[') + 1; i < st.length(); i++) {
            if (st.charAt(i) == '[')
                numberOfOpBrackets++;
            else if (st.charAt(i) == ']' && numberOfOpBrackets > 0)
                numberOfOpBrackets--;
            else if (st.charAt(i) == ']' && numberOfOpBrackets == 0)
                clBracketIdx = i;
        }
        String newSt = st.substring(st.indexOf('[') + 1, clBracketIdx);
        String tailSt = st.substring(clBracketIdx + 1);

        int q = Integer.parseInt(st.substring(0, st.indexOf('[')));
        String ans = repeatStroke(repeat(newSt), q);

        return ans.concat(repeat(tailSt));
    }

    public static String repeatStroke(String st, int n) {
        StringBuilder ans = new StringBuilder();
        while (n-- > 0)
            ans.append(st);
        return ans.toString();
    }
}
