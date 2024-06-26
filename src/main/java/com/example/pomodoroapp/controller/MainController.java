package com.example.pomodoroapp.Controller;
import com.example.pomodoroapp.Model.*;
import com.example.pomodoroapp.Model.IAccountDAO;
import com.example.pomodoroapp.Model.SqliteAccountDAO;
import com.example.pomodoroapp.Model.IStudy_SessionDAO;
import com.example.pomodoroapp.Model.SqliteStudy_SessionDAO;
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
    private TextField passwordTextField;
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
        passwordTextField.setText(account.getPassword());
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

}
