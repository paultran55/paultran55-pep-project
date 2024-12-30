package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService{

    private AccountDAO accountDAO;


    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account createAccount(Account account) {
        if (account.username == null || account.username.isBlank()) {
            throw new IllegalArgumentException("");
        }
        
        if (account.password == null || account.password.length() < 4) {
            throw new IllegalArgumentException("");
        }

        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            throw new IllegalArgumentException("");
        }

        return accountDAO.createAccount(account.getUsername(),account.getPassword());
    }

    public Account login(Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            throw new IllegalArgumentException("");
        }

        Account account1 = accountDAO.getAccountByUsername(account.getUsername());
        if (account1 == null || !account1.getPassword().equals(account.getPassword())) {
            throw new IllegalArgumentException("");
        }

        return account1;
    }


}