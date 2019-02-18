package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;
    private String errorMessage = "";
    private boolean isSuccess = true;
    public String getErrorMessage() {
        if(!isSuccess)
            return "  " + errorMessage;
        else
            return "";
    }

    public CashMachine(Bank bank) {

        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };

    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    public String[] getAccountIDs(){
        return bank.getAccountIDs();
    }

    public void deposit(int amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }
    }

    public void withdraw(int amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {
            accountData = null;
        }
    }

    @Override
    public String toString() {
        return accountData != null ? accountData.toString() : "Try account 1000, 2000, 100 or 200 and click submit.";
    }

    public String getName() {
        return accountData != null ? accountData.getName() : "Try account 1000 or 2000, 100 or 200  and click submit.";
    }

    public String getId() {
        return accountData != null ? new Integer(accountData.getId()).toString() : "Try account 1000 or 2000, 100 or 200  and click submit.";
    }

    public String getEmail() {
        return accountData != null ? accountData.getEmail() : "Try account 1000 or 2000, 100 or 200  and click submit.";
    }
    public String getBalance() {
        return accountData != null ? new Integer(accountData.getBalance()).toString() : "Try account 1000 or 2000, 100 or 200  and click submit.";
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                isSuccess = true;
                T data = result.getData();
                postAction.accept(data);
            } else {
                isSuccess = false;
                errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
