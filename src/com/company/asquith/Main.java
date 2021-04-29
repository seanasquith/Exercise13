package com.company.asquith;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

class CheckingAccount {
    // create a fair mutex

    private int balance;

    public CheckingAccount(int initialBalance) {
        balance = initialBalance;
    }

    public int withdraw(int amount) {
        synchronized (this) {
            if (amount <= balance) {
                try {
                    Thread.sleep((int) (Math.random() * 200));
                } catch (InterruptedException ie) {

                }

                balance -= amount;
            }
        }
        return balance;
    }
}

class AccountHolder implements Runnable {
    private String name;
    private CheckingAccount account;

    AccountHolder(String name, CheckingAccount account) {
        this.name = name;
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(name + " tries to withdraw $10, balance: " +
                    account.withdraw(10));
        }

    }
}




public class Main {
    public static void main(String[] args) {
        CheckingAccount account = new CheckingAccount(100);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new AccountHolder("Wife", account));
        executor.submit(new AccountHolder("Husband", account));

    }
}