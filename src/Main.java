import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        String input = sc.next();

        String input = "2[3[x]y]";
        String output = repeat(input);
        System.out.println(input + " = " + output + "\n");

        input = "3[xyz]4[xy]z";
        System.out.println(input + " is valid: " + validate(input));
        output = repeat(input);
        System.out.println(input + " = " + output + "\n");

        input = "3[2[1[d]zfa]4[2[w]aa]z]";
        System.out.println(input + " is valid: " + validate(input));
        output = repeat(input);
        System.out.println(input + " = " + output + "\n");

        input = "21[h]i]]";
        System.out.println(input + " is valid: " + validate(input));
    }

    public static String repeat(String st) throws NumberFormatException {
        if (st.isEmpty() || st.matches("[a-zA-Z]+"))
            return st;
        if (!validate(st))
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


    //digit  is first symbol
    //digit  must be followed by digit  or [
    //letter must be followed by letter or ]
    //[      must be followed by digit  or letter
    //]      must be followed by digit  or letter or ]
    //amount of [ must be equal to amount of ]
    public static boolean validate(String st) {
        if (st.isEmpty())
            return false;
        if (!st.matches("[a-zA-Z0-9\\[\\]]+"))
            return false;
        if (getTypeOfChar(st.charAt(0)) > 0)
            return false;

        char[] arr = st.toCharArray();
        int bracketsCounter = 0;
        for (int i = 0; i < arr.length - 1; i++) {

            //0 for digits
            //1 for letter
            //2 for opening bracket
            //3 for closing bracket
            //may be replaced with some Map<Character, Integer>
            //and switch (map.get(arr[i]))
            int typeOfChar = getTypeOfChar(arr[i]);
            int typeOfNextChar = getTypeOfChar(arr[i + 1]);

            switch (typeOfChar) {
                case (0):
                    if (typeOfNextChar == 0)
                        continue;
                    else if (typeOfNextChar == 2) {
                        bracketsCounter++;
                        continue;
                    } else return false;
                case (1):
                    if (typeOfNextChar == 1)
                        continue;
                    else if (typeOfNextChar == 3) {
                        bracketsCounter--;
                        continue;
                    } else return false;
                case (2):
                    if (typeOfNextChar == 0 || typeOfNextChar == 1)
                        continue;
                    else return false;
                case (3):
                    if (typeOfNextChar == 0 || typeOfNextChar == 1)
                        continue;
                    else if (typeOfNextChar == 3) {
                        bracketsCounter--;
                        continue;
                    } else return false;
                case (-1):
                    return false;
            }
        }
        return bracketsCounter == 0;
    }

    //0 for digits
    //1 for letter
    //2 for opening bracket
    //3 for closing bracket
    public static int getTypeOfChar(char symbol) {
        if (symbol >= '0' && symbol <= '9') {
            return 0;
        } else if ((symbol >= 'a' && symbol <= 'z') || (symbol >= 'A' && symbol <= 'Z')) {
            return 1;
        } else if (symbol == '[')
            return 2;
        else if (symbol == ']')
            return 3;
        else return -1;
    }
}
