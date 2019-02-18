package rocks.zipcode.atm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField amountTxtField = new TextField();
    private Label errorMsgLabel;
    private CashMachine cashMachine = new CashMachine(new Bank());
    private TextField accountID;
    private TextField accountName;
    private TextField accountEmail;
    private TextField accountBalance ;




    private Parent createContent() {
        VBox vbox = new VBox(10);

        GridPane loginPane = new GridPane();
        GridPane accountDetailsPane = new GridPane();

        ObservableList<String> options = FXCollections.observableArrayList(cashMachine.getAccountIDs());

        final ComboBox comboBox = new ComboBox(options);
        Label accountLabel = new Label("Account ID");

        Button btnLogin = new Button("Login");
        btnLogin.setOnAction(e -> {
            int id = Integer.parseInt((String)comboBox.getValue());
            cashMachine.login(id);
            vbox.getChildren().remove(loginPane);
            vbox.getChildren().addAll( accountDetailsPane, errorMsgLabel);
            writeAccountDetails();
            amountTxtField.requestFocus();
        });
        loginPane.setVgap(20);
        loginPane.setHgap(20);
        loginPane.addRow(1,new Label("  "), accountLabel, comboBox);
        loginPane.addRow(2,new Label("  "), btnLogin);

        vbox.setPrefSize(600, 600);

        accountID = new TextField();
        accountName = new TextField();
        accountEmail = new TextField();
        accountBalance = new TextField();
        Label accountIDLabel = new Label("ID");
        Label accountNameLabel = new Label("Name");
        Label accountEmailLabel = new Label("Email");
        Label accountBalanceLabel = new Label("Balance");
        errorMsgLabel = new Label("                                                      ");


        accountID.setEditable(false);
        accountName.setEditable(false);
        accountEmail.setEditable(false);
        accountBalance.setEditable(false);



        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            String amountStr = amountTxtField.getText();

            if(!amountStr.isEmpty()) {
                int amount = Integer.parseInt(amountStr);
                cashMachine.deposit(amount);

                writeAccountDetails();
            }else{
                errorMsgLabel.setText("Enter a valid amount");
                amountTxtField.requestFocus();
            }
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            String amountStr = amountTxtField.getText();
            if(!amountStr.isEmpty()) {
                int amount = Integer.parseInt(amountStr);
                cashMachine.withdraw(amount);

                writeAccountDetails();
            }else{
                errorMsgLabel.setText("Enter a valid amount");
                amountTxtField.requestFocus();
            }

        });

        Button btnExit = new Button("Log out");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            clearAccountDetails();
            vbox.getChildren().removeAll(accountDetailsPane, errorMsgLabel);
            vbox.getChildren().addAll( loginPane);

        });





        accountDetailsPane.setVgap(20);
        accountDetailsPane.setHgap(20);


        accountDetailsPane.addRow(1,new Label("  "), accountIDLabel, accountID);

        accountDetailsPane.addRow(2,new Label("  "), accountNameLabel,accountName);

        accountDetailsPane.addRow(3,new Label("  "), accountEmailLabel,accountEmail);
        accountDetailsPane.addRow(4,new Label("  "), accountBalanceLabel,accountBalance);
        accountDetailsPane.addRow(5,new Label("  "), new Label("Amount"), amountTxtField);
        accountDetailsPane.addRow(6,new Label("  "), btnDeposit, btnWithdraw, btnExit);
        accountDetailsPane.addRow(7,new Label("    "));

        vbox.getChildren().addAll(loginPane);
        //vbox.(20);
        return vbox;
    }
    public void writeAccountDetails()
    {
        accountID.setText(cashMachine.getId());
        accountName.setText(cashMachine.getName());;
        accountEmail.setText(cashMachine.getEmail());;
        accountBalance.setText(cashMachine.getBalance());
        errorMsgLabel.setText(cashMachine.getErrorMessage());
    }
    public void clearAccountDetails()
    {
        accountID.setText("");
        accountName.setText("");;
        accountEmail.setText("");;
        accountBalance.setText("");
        amountTxtField.setText("");
        errorMsgLabel.setText("");
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
