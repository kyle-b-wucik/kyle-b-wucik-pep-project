package Service;
import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account register(Account account) {
        // null check
        if (account == null) {
            return null;
        }

        //checks if user name is in database
        Account existingAccount = accountDAO.getAccountByUsername(account.getUsername());
        if (existingAccount != null) {
            return null;
        }

        //checks for the verification
        if (account.getUsername() == null || account.getUsername().isBlank() ||
            account.getPassword() == null || account.getPassword().length() < 4) {
                return null;
            }

        // if passes all verification adds to database
        return accountDAO.insertAccount(account);
    }
    
}
