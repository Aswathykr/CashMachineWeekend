package rocks.zipcode.atm;

import javafx.scene.control.Label;
import javafx.scene.layout.*;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private CashMachine cashMachine = new CashMachine(new Bank());
    private TextField accountID;
    private TextField accountName;
    private TextField accountEmail;
    private TextField accountBalance ;




    private Parent createContent() {
        VBox vbox = new VBox(10);
        vbox.setPrefSize(600, 600);

        accountID = new TextField();
        accountName = new TextField();
        accountEmail = new TextField();
        accountBalance = new TextField();
        Label accountIDLabel = new Label("ID");
        Label accountNameLabel = new Label("Name");
        Label accountEmailLabel = new Label("Email");
        Label accountBalanceLabel = new Label("Balance");
        Label accountEmpyLabel = new Label("   ");

        accountID.setEditable(false);
        accountName.setEditable(false);
        accountEmail.setEditable(false);
        accountBalance.setEditable(false);

        Button btnSubmit = new Button("Set Account ID");
        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);

            writeAccountDetails();
        });

        Button btnDeposit = new Button("Deposit");
        btnDeposit.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.deposit(amount);

            writeAccountDetails();
        });

        Button btnWithdraw = new Button("Withdraw");
        btnWithdraw.setOnAction(e -> {
            int amount = Integer.parseInt(field.getText());
            cashMachine.withdraw(amount);

            writeAccountDetails();
        });

        Button btnExit = new Button("Exit");
        btnExit.setOnAction(e -> {
            cashMachine.exit();

            clearAccountDetails();
        });

        FlowPane flowpane = new FlowPane();
        flowpane.setVgap(20);
        flowpane.setHgap(20);
        flowpane.getChildren().add(new Label("  "));
        flowpane.getChildren().add(btnSubmit);
        flowpane.getChildren().add(btnDeposit);
        flowpane.getChildren().add(btnWithdraw);
        flowpane.getChildren().add(btnExit);

        GridPane accountDetailsPane = new GridPane();

        accountDetailsPane.setVgap(20);
        accountDetailsPane.setHgap(20);


        accountDetailsPane.addRow(1,new Label("  "), accountIDLabel, accountID);

        accountDetailsPane.addRow(2,new Label("  "), accountNameLabel,accountName);

        accountDetailsPane.addRow(3,new Label("  "), accountEmailLabel,accountEmail);
        accountDetailsPane.addRow(4,new Label("  "), accountBalanceLabel,accountBalance);

        vbox.getChildren().addAll(field, flowpane, accountDetailsPane);
        //vbox.(20);
        return vbox;
    }
    public void writeAccountDetails()
    {
        accountID.setText(cashMachine.getId());
        accountName.setText(cashMachine.getName());;
        accountEmail.setText(cashMachine.getEmail());;
        accountBalance.setText(cashMachine.getBalance());
    }
    public void clearAccountDetails()
    {
        accountID.setText("");
        accountName.setText("");;
        accountEmail.setText("");;
        accountBalance.setText("");
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
