/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package LogIn;

import Database.Connector;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Ervhyne
 */
public class LoginPageController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private JFXTextField login_username;

    @FXML
    private JFXTextField login_showPassword;

    @FXML
    private JFXPasswordField login_password;

    @FXML
    private Button login_btn;

    @FXML
    private JFXCheckBox login_selectShowPassword;

    @FXML
    private Button login_createAcc;

    @FXML
    private Hyperlink login_forgetPassword;

    @FXML
    private Label signin_alert;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private JFXTextField signup_userID;

    @FXML
    private JFXTextField signup_password;

    @FXML
    private Button signup_btn;

    @FXML
    private Button signup_loginAcc;

    @FXML
    private JFXTextField signup_confirmPassword;

    @FXML
    private JFXComboBox<?> signup_selectQuestion;

    @FXML
    private JFXTextField signup_answer;

    @FXML
    private Label signup_alert;

    @FXML
    private AnchorPane forgot_form;

    @FXML
    private JFXTextField forgot_userID;

    @FXML
    private Button forgot_proceedBtn;

    @FXML
    private Button forgot_backBtn;

    @FXML
    private JFXComboBox<?> forgot_selectQuestion;

    @FXML
    private JFXTextField forgot_answer;

    @FXML
    private Label forgot_alert;

    @FXML
    private AnchorPane changePass_form;

    @FXML
    private JFXTextField changePass_password;

    @FXML
    private Button changePass_proceedBtn;

    @FXML
    private Button changePass_backBtn;

    @FXML
    private JFXTextField changePass_confirmPassword;

    @FXML
    private Label changePass_alert;

    // SQL variables
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private Connector connectDB = new Connector();
    private boolean showPasswordChecked = false;

    //LOGIN FORM PART
    public void login() {
        AlertManager alert = new AlertManager(signin_alert);

        if (login_username.getText().isEmpty() || (login_password.getText().isEmpty() && login_showPassword.getText().isEmpty())) {
            alert.setAlertText("Please fill in all required fields.", "red");
        } else {
            String selectData = "SELECT username, password FROM signin_users WHERE " + "username = ?";

            connect = connectDB.connectDB();

            if (connect == null) {
                alert.setAlertText("Unable to connect to the database!", "red");
            } else {
                try {
                    prepare = connect.prepareStatement(selectData);
                    prepare.setString(1, login_username.getText());

                    result = prepare.executeQuery();

                    if (result.next()) {
                        // Check if the password matches the retrieved user's password
                        String storedPassword = result.getString("password");

                        String inputPassword = showPasswordChecked
                                ? login_showPassword.getText()
                                : login_password.getText();

                        if (storedPassword.equals(inputPassword)) {
                            alert.setAlertText("Successfully Login!", "green");
                        } else {
                            alert.setAlertText("Incorrect Password", "red");
                        }
                    } else {
                        alert.setAlertText("Account doesn't exist", "red");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void showPassword() {
        showPasswordChecked = login_selectShowPassword.isSelected();
        if (showPasswordChecked) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }

    //FORGOT PASS FORM PART
    public void forgotPass() {
        AlertManager alert = new AlertManager(forgot_alert);
        if (forgot_userID.getText().isEmpty()
                || (forgot_selectQuestion.getSelectionModel().getSelectedItem() == null
                || forgot_answer.getText().isEmpty())) {
            alert.setAlertText("Please fill in all required fields.", "red");
        } else {
            String checkData = "Select username, question, answer FROM signin_users "
                    + "WHERE username = ? AND question = ? AND answer = ?";

            connect = connectDB.connectDB();

            try {
                prepare = connect.prepareStatement(checkData);
                prepare.setString(1, forgot_userID.getText());
                prepare.setString(2, (String) forgot_selectQuestion.getSelectionModel().getSelectedItem());
                prepare.setString(3, forgot_answer.getText());

                result = prepare.executeQuery();

                //IF CORRECT
                if (result.next()) {
                    //PROCEED TO CHANGE PASSWORD
                    signup_form.setVisible(false);
                    login_form.setVisible(false);
                    forgot_form.setVisible(false);
                    changePass_form.setVisible(true);
                } else {
                    alert.setAlertText("Incorrect Information", "red");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void forgotListQuestions() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        forgot_selectQuestion.setItems(listData);
    }

    public void changePass() {
        AlertManager alert = new AlertManager(changePass_alert);

        //CHECK ALL FIELDS IF EMPTY OR NOT
        if (changePass_password.getText().isEmpty() || changePass_confirmPassword.getText().isEmpty()) {
            alert.setAlertText("Please fill in all required fields.", "red");

        } else if (!changePass_password.getText().equals(changePass_confirmPassword.getText())) {
            //CHECK IF THE PASSWORD AND CONFIRMATION ARE NOT MATCH
            alert.setAlertText("Password does not match", "red");
        } else if (signup_password.getText().length() < 8) {
            //CHECK IF THE PASSWORD IS LESS THAN 8
            alert.setAlertText("Invalid Password, at least 8 characters needed.", "red");
        } else {
            String updateData = "UPDATE sigin_users SET password = ?, update_date = ? "
                    + "WHERE username = '" + forgot_userID.getText() + "'";
            connect = connectDB.connectDB();

            try {
                prepare = connect.prepareStatement(updateData);
                prepare.setString(1, changePass_password.getText());

                Date date = new Date();
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                prepare.setString(2, String.valueOf(sqlDate));

                prepare.executeUpdate();
                alert.setAlertText("Succesfully changed Password", "green");

                //LOGIN WILL APPEAR
                signup_form.setVisible(false);
                login_form.setVisible(true);
                forgot_form.setVisible(false);
                changePass_form.setVisible(false);

                changePass_password.setText("");
                changePass_confirmPassword.setText("");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    //CREATE ACC FORM PART
    public void createAcc() {
        AlertManager alert = new AlertManager(signup_alert);

        // CHECK IF WE HAVE EMPTY FIELD
        if (signup_userID.getText().isEmpty()
                || signup_password.getText().isEmpty()
                || signup_confirmPassword.getText().isEmpty()
                || signup_selectQuestion.getSelectionModel().getSelectedItem() == null
                || signup_answer.getText().isEmpty()) {
            alert.setAlertText("Please fill in all required fields.", "red");
        } else if (!signup_password.getText().equals(signup_confirmPassword.getText())) {
            alert.setAlertText("Password does not match.", "red");
        } else if (signup_password.getText().length() < 8) {
            alert.setAlertText("Invalid Password, at least 8 characters needed.", "red");
        } else {
            // CHECK IF THE USERNAME IS ALREADY TAKEN
            String checkUsername = "SELECT * FROM signin_users WHERE username = '"
                    + signup_userID.getText() + "'";
            connect = connectDB.connectDB();

            if (connect == null) {
                alert.setAlertText("Unable to connect to the database!", "red");
            } else {

                try {
                    statement = connect.createStatement();
                    result = statement.executeQuery(checkUsername);

                    if (result.next()) {
                        alert.setAlertText(signup_userID.getText() + " is already taken", "red");
                    } else {
                        String insertData = "INSERT INTO signin_users"
                                + "(username, password, question, answer, date)"
                                + "VALUES (?,?,?,?,?)";

                        prepare = connect.prepareStatement(insertData);
                        prepare.setString(1, signup_userID.getText());
                        prepare.setString(2, signup_password.getText());
                        prepare.setString(3, (String) signup_selectQuestion.getSelectionModel().getSelectedItem());
                        prepare.setString(4, signup_answer.getText());

                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        prepare.setString(5, String.valueOf(sqlDate));

                        prepare.executeUpdate();

                        alert.setAlertText("Registered Successfully!", "green");

                        createAccClearFields();

                        // Clear and hide the alert after a certain period
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                alert.hideAlert();
                            }
                        }, 5000); // Hide the alert after 5 seconds (adjust as needed)
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //CLEAR FIELDS OF CREATE ACC FORM
    public void createAccClearFields() {
        signup_userID.setText("");
        signup_password.setText("");
        signup_selectQuestion.getSelectionModel().clearSelection();
        signup_confirmPassword.setText("");
        signup_answer.setText("");
    }

    //SWITCH FORMS WHERE YOU WANT
    public void switchForm(ActionEvent event) {
        if (event.getSource() == signup_loginAcc || event.getSource() == forgot_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        } else if (event.getSource() == login_createAcc) {
            signup_form.setVisible(true);
            login_form.setVisible(false);
            forgot_form.setVisible(false);
            changePass_form.setVisible(false);
        } else if (event.getSource() == login_forgetPassword) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);

            //TO SHOW THE DATA OF OUR COMBO-BOX
            forgotListQuestions();
        } else if (event.getSource() == changePass_backBtn) {
            signup_form.setVisible(false);
            login_form.setVisible(false);
            forgot_form.setVisible(true);
            changePass_form.setVisible(false);
        }
    }

    private String[] questionList = {"Who is the most handsome prof?", "What is OOP"};

    public void questions() {
        List<String> listQ = new ArrayList<>();

        for (String data : questionList) {
            listQ.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listQ);
        signup_selectQuestion.setItems(listData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        questions();
        forgotListQuestions();

    }

}
