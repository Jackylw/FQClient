/**
 * @description: 此类必须严格保持与双端一致
 */
package top.fexample.fq.model;

import java.io.Serializable;

public class User implements Serializable {
    public String accountId;
    public String password;

    public User(String accountId, String password) {
        this.accountId = accountId;
        this.password = password;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
