package FirstPackage;

class SecondClass {
    private int firstNumber;
    private int secondNumber;

    public void setFirstNumber(int number) {
        this.firstNumber = number;
    }

    public void setSecondNumber(int number) {
        this.secondNumber = number;
    }

    public int performAction() {
        return firstNumber * secondNumber; // Действие: умножение
    }
}