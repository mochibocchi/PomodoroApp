package com.example.pomodoroapp.controller;
import com.example.pomodoroapp.model.*;
import com.example.pomodoroapp.model.IAccountDAO;
import com.example.pomodoroapp.model.SqliteAccountDAO;
import com.example.pomodoroapp.model.IStudy_SessionDAO;
import com.example.pomodoroapp.model.SqliteStudy_SessionDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;


public class MainController {

    @FXML
    private IAccountDAO accountDAO;
    private IStudy_SessionDAO Study_SessionDAO;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private VBox accountContainer;
    @FXML
    private ListView<Account> accountsListView;

    public MainController() {
        accountDAO = new SqliteAccountDAO();
        Study_SessionDAO = new SqliteStudy_SessionDAO();
    }


    private void selectAccount(Account account) {
        accountsListView.getSelectionModel().select(account);
        firstNameTextField.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        emailTextField.setText(account.getEmail());
    }

    /**
     * Renders a cell in the contacts list view by setting the text to the contact's full name.
     * @param accountsListView The list view to render the cell for.
     * @return The rendered cell.
     */
    private ListCell<Account> renderCell(ListView<Account> accountsListView) {
        return new ListCell<>() {
            /**
             * Handles the event when a contact is selected in the list view.
             * @param mouseEvent The event to handle.
             */
            private void onAccountSelected(MouseEvent mouseEvent) {
                ListCell<Account> clickedCell = (ListCell<Account>) mouseEvent.getSource();
                // Get the selected contact from the list view
                Account selectedAccount = clickedCell.getItem();
                if (selectedAccount != null) selectAccount(selectedAccount);
            }

            /**
             * Updates the item in the cell by setting the text to the contact's full name.
             * @param account The contact to update the cell with.
             * @param empty Whether the cell is empty.
             */
            @Override
            protected void updateItem(Account account, boolean empty) {
                super.updateItem(account, empty);
                // If the cell is empty, set the text to null, otherwise set it to the contact's full name
                if (empty || account == null || account.getFullName() == null) {
                    setText(null);
                    super.setOnMouseClicked(this::onAccountSelected);
                } else {
                    setText(account.getFullName());
                }
            }
        };
    }

    /**
     * Synchronizes the contacts list view with the contacts in the database.
     */
    private void syncAccounts() {
        accountsListView.getItems().clear();
        List<Account> accounts = accountDAO.getAllAccounts();
        boolean hasAccount = !accounts.isEmpty();
        if (hasAccount) {
            accountsListView.getItems().addAll(accounts);
        }
        // Show / hide based on whether there are contacts
        accountContainer.setVisible(hasAccount);
    }

    @FXML
    public void initialize() {
        accountsListView.setCellFactory(this::renderCell);
        syncAccounts();
        // Select the first contact and display its information
        accountsListView.getSelectionModel().selectFirst();
        Account firstAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (firstAccount != null) {
            selectAccount(firstAccount);
        }
    }

    @FXML
    private void onEditConfirm() {
        // Get the selected contact from the list view
        Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            selectedAccount.setFirstName(firstNameTextField.getText());
            selectedAccount.setLastName(lastNameTextField.getText());
            selectedAccount.setEmail(emailTextField.getText());
            accountDAO.updateAccount(selectedAccount);
            syncAccounts();
        }
    }

    @FXML
    private void onDelete() {
        // Get the selected contact from the list view
        Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            accountDAO.deleteAccount(selectedAccount);
            syncAccounts();
        }
    }

    @FXML
    private void onAdd() {
        // Default values for a new contact
        final String DEFAULT_FIRST_NAME = "New";
        final String DEFAULT_LAST_NAME = "Account";
        final String DEFAULT_EMAIL = "";
        Account newAccount = new Account(DEFAULT_FIRST_NAME, DEFAULT_LAST_NAME, DEFAULT_EMAIL);
        // Add the new contact to the database
        accountDAO.addAccount(newAccount);
        syncAccounts();
        // Select the new contact in the list view
        // and focus the first name text field
        selectAccount(newAccount);
        firstNameTextField.requestFocus();
    }

    @FXML
    private void onCancel() {
        // Find the selected contact
        Account selectedAccount = accountsListView.getSelectionModel().getSelectedItem();
        if (selectedAccount != null) {
            // Since the contact hasn't been modified,
            // we can just re-select it to refresh the text fields
            selectAccount(selectedAccount);
        }
    }
}
