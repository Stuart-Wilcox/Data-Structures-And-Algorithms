package ca.uwo.eng.se2205b.lab5.model;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Thrown if an Account can not be closed.
 */
@ParametersAreNonnullByDefault
public class AccountCloseException extends Exception {

    private final Account account;

    public AccountCloseException(Account account, String reason) {
        super(reason);
        this.account = checkNotNull(account, "account == null");
    }


    public Account getAccount() {
        return account;
    }
}
