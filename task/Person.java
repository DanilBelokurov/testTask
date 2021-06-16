package task;


/**
 * Person
 */
public class Person {

    private String name;
    private int wallet;
    private int appendFromBank;

    public Person(String name, String wallet) {
        this.name = name;
        this.wallet = (int)( Float.valueOf(wallet) * 100);
        this.appendFromBank = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getName() {
        return this.name;
    }

    public int getWallet() {
        return this.wallet;
    }

    public String getWalletInString(){
        return String.valueOf((float)this.wallet/100);
    }

    public int getAppendFromBank() {
        return this.appendFromBank;
    }

    public String getAppendFromBankInString(){
        return String.valueOf((float)this.appendFromBank/100);
    }

    public void changeWallet(int addition) {
        this.wallet += addition;
    }

    public void changeAppendFromBank(int addition) {
        this.appendFromBank += addition;
    }
}