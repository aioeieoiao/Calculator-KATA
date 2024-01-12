import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println(calc(in.nextLine())); // передаем в метод calc строку и выводим результат в консоль
    }

    public static String calc(String input) throws Exception {
        String newInput = input.replace(" ", "");
        char[] inputCharArray = newInput.toCharArray();
        String firstOperand = "";
        String secondOperand = "";
        String operator = "";
        int arabicOrRoman = 0; // 1 = arabic, 2 = roman

        for (char i: inputCharArray) {
            if (isArabicNumberSymbol(i)) {
                if (arabicOrRoman == 2) {
                    throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");
                }
                if (operator.isEmpty()) {
                    if (firstOperand.isEmpty() && i == '0' || !firstOperand.isEmpty() && i != '0') {
                        throw new Exception("На вход принимаются числа от 1 до 10 включительно");
                    }
                    firstOperand += i;
                } else {
                    if (secondOperand.isEmpty() && i == '0' || !secondOperand.isEmpty() && i != '0') {
                        throw new Exception("На вход принимаются числа от 1 до 10 включительно");
                    }
                    secondOperand += i;
                }
                arabicOrRoman = 1;
            } else if (isRomanNumberSymbol(i)) {
                if (arabicOrRoman == 1) {
                    throw new Exception("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");
                }
                if (operator.isEmpty()) firstOperand += i;
                else secondOperand += i;
                arabicOrRoman = 2;
            } else if (isMathOperatorSymbol(i)) {
                if (operator.isEmpty()) {
                    operator += i;
                } else {
                    throw new Exception("Формат математической опе1рации не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                }
            } else {
                throw new Exception("Неподдерживаемые символы");
            }
        }

        if (operator.isEmpty() || firstOperand.isEmpty() || secondOperand.isEmpty()) {
            throw new Exception("Строка не является математической операцией");
        }

        String result = "";
        int a, b, c = 0;
        if (arabicOrRoman == 2) {
            a = convertRomanToArabic(firstOperand);
            b = convertRomanToArabic(secondOperand);
            if (a == 0 || a > 10 || b == 0 || b > 10) {
                throw new Exception("На вход принимаются числа от 1 до 10 включительно");
            }
            c = calculateOperation(a, b, operator);
            if (c < 1) {
                throw new Exception("Результатом работы калькулятора с римскими числами могут быть только положительные числа");
            }
            result = convertArabicToRoman(c);
        } else {
            a = Integer.parseInt(firstOperand);
            b = Integer.parseInt(secondOperand);
            c = calculateOperation(a, b, operator);
            result += c;
        }

        return result;
    }

    static boolean isArabicNumberSymbol(char symbol) {
        int value = Character.getNumericValue(symbol);
        return value >= 0 && value <= 9;
    }

    static boolean isRomanNumberSymbol(char symbol) {
        return switch (symbol) { // IDE предложила заключить switch так, я подтвердил
            case 'I', 'V', 'X' -> true;
            default -> false;
        };
    }

    static boolean isMathOperatorSymbol(char symbol) {
        return switch (symbol) {
            case '+', '-', '/', '*' -> true;
            default -> false;
        };
    }

    static String[] romanNumbers = {
            "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X",
            "XI", "XII", "XIII", "XIV", "XV", "XVI", "XVII", "XVIII", "XIX", "XX",
            "XXI", "XXII", "XXIII", "XXIV", "XXV", "XXVI", "XXVII", "XXVIII", "XXIX", "XXX",
            "XXXI", "XXXII", "XXXIII", "XXXIV", "XXXV", "XXXVI", "XXXVII", "XXXVIII", "XXXIX", "XL",
            "XLI", "XLII", "XLIII", "XLIV", "XLV", "XLVI", "XLVII", "XLVIII", "XLIX", "L",
            "LI", "LII", "LIII", "LIV", "LV", "LVI", "LVII", "LVIII", "LIX", "LX",
            "LXI", "LXII", "LXIII", "LXIV", "LXV", "LXVI", "LXVII", "LXVIII", "LXIX", "LXX",
            "LXXI", "LXXII", "LXXIII", "LXXIV", "LXXV", "LXXVI", "LXXVII", "LXXVIII", "LXXIX", "LXXX",
            "LXXXI", "LXXXII", "LXXXIII", "LXXXIV", "LXXXV", "LXXXVI", "LXXXVII", "LXXXVIII", "LXXXIX", "XC",
            "XCI", "XCII", "XCIII", "XCIV", "XCV", "XCVI", "XCVII", "XCVIII", "XCIX", "C"
    };

    static String convertArabicToRoman(int index) {
        return romanNumbers[index - 1];
    }

    static int convertRomanToArabic(String str) {
        int result = 0;

        for (int i = 0; i < romanNumbers.length; i++) {
            if (romanNumbers[i].equals(str)) {
                result = i + 1;
                break;
            }
        }

        return result;
    }

    static int calculateOperation(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "/" -> a / b;
            case "*" -> a * b;
            default -> 0;
        };
    }
}