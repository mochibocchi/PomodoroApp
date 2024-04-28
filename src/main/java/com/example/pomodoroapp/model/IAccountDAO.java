package com.example.pomodoroapp.Model;

import com.example.pomodoroapp.Model.Account;

import java.util.List;


public interface IAccountDAO {
    public void addAccount(Account account);
    /**
     * Updates an existing contact in the database.
     * @param account The contact to update.
     */
    public void updateAccount(Account account);
    /**
     * Deletes a contact from the database.
     * @param account The contact to delete.
     */
    public void deleteAccount(Account account);
    /**
     * Retrieves a contact from the database.
     * @param id The id of the contact to retrieve.
     * @return The contact with the given id, or null if not found.
     */
    public Account getAccountId(int id);
    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<Account> getAllAccounts();

}
