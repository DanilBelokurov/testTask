package task;

import java.math.BigDecimal;


public class Person {

    private String name;
    private BigDecimal wallet;
    private BigDecimal appendFromBank;

    public Person(String name, String wallet) {
        this.name = name;
        this.wallet = new BigDecimal(wallet);

        this.appendFromBank = new BigDecimal(-1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public void setAppendFromBank(BigDecimal append) {
        this.appendFromBank = append;
    }

    public String getName() {
        return this.name;
    }

    public BigDecimal getWallet() {
        return this.wallet;
    }

    public String getWalletInString(){
        return wallet.toString();
    }

    public BigDecimal getAppendFromBank() {
        return this.appendFromBank;
    }

    public String getAppendFromBankInString(){
        return this.appendFromBank.toString();
    }

    public void changeWallet(BigDecimal addition) {
        this.wallet = wallet.add(addition);
    }

    public void changeAppendFromBank(BigDecimal addition) {
        this.appendFromBank = appendFromBank.add(addition);
    }
}