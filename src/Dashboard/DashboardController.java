package Dashboard;

import Animations.SwitchForms;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

    @FXML
    private AnchorPane topPane;

    @FXML
    private JFXButton dashboard_menu;

    @FXML
    private AnchorPane topPane_line;

    @FXML
    private AnchorPane centerPane;

    @FXML
    private AnchorPane customSupp_form;

    @FXML
    private AnchorPane sales_form;

    @FXML
    private TextField sl_search;

    @FXML
    private Label sl_ticketSoldPrv;

    @FXML
    private Label sl_ticketSoldNo;

    @FXML
    private Label sl_bookedFlightsNo;

    @FXML
    private Label sl_earningsNoPrv;

    @FXML
    private Label sl_earningsNo;

    @FXML
    private TableView<?> sl_table;

    @FXML
    private AnchorPane ticketRecords_form;

    @FXML
    private TextField tr_search;

    @FXML
    private TableView<?> tr_table;

    @FXML
    private TableColumn<?, ?> trt_flightNo;

    @FXML
    private TableColumn<?, ?> trt_airplaneNo;

    @FXML
    private TableColumn<?, ?> trt_initialDep;

    @FXML
    private TableColumn<?, ?> trt_departure;

    @FXML
    private TableColumn<?, ?> trt_destination;

    @FXML
    private TableColumn<?, ?> trt_origin;

    @FXML
    private TableColumn<?, ?> trt_seatNo;

    @FXML
    private TableColumn<?, ?> trt_class;

    @FXML
    private AnchorPane flightManager_form;

    @FXML
    private Label fm_managerBtn;

    @FXML
    private Label fm_recordsBtn;

    @FXML
    private TextField fr_search;

    @FXML
    private AnchorPane fm_managerForm;

    @FXML
    private AnchorPane fm_recordsForm;

    @FXML
    private AnchorPane bookedFlights_form;

    @FXML
    private TextField bf_search;

    @FXML
    private TableView<?> bookedFlights_table;

    @FXML
    private TableColumn<?, ?> bft_name;

    @FXML
    private TableColumn<?, ?> bft_flightNo;

    @FXML
    private TableColumn<?, ?> bft_seatNo;

    @FXML
    private TableColumn<?, ?> bft_class;

    @FXML
    private TableColumn<?, ?> bft_departure;

    @FXML
    private TableColumn<?, ?> bft_dateOfBooking;

    @FXML
    private TableColumn<?, ?> bft_destFrom;

    @FXML
    private TableColumn<?, ?> bft_destTo;

    @FXML
    private JFXDatePicker bf_date;

    @FXML
    private JFXTimePicker bf_time;

    @FXML
    private JFXButton bf_addBtn;

    @FXML
    private JFXButton bf_updateBtn;

    @FXML
    private JFXButton bf_clearBtn;

    @FXML
    private JFXButton bf_deleteBtn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AnchorPane overlayPane;

    @FXML
    private AnchorPane dashboard_slider;

    @FXML
    private JFXButton dashboardForm_btn;

    @FXML
    private JFXButton bookedForm_btn;

    @FXML
    private JFXButton flightManForm_btn;

    @FXML
    private JFXButton ticketForm_btn;

    @FXML
    private JFXButton salesForm_btn;

    @FXML
    private JFXButton customerSupp_btn;

    @FXML
    private JFXButton logout_btn;

    @FXML
    private JFXButton slider_menu;

    private boolean isMenuVisible = false;
    private JFXButton currentSelectedButton;

    public void toggleAdminMenu() {
        if (isMenuVisible) {
            hideMenu();
        } else {
            showMenu();
        }
    }

    private void showMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(dashboard_slider);
        slide.setToX(0);
        slide.play();

        isMenuVisible = true;

        overlayPane.setVisible(true);
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    }

    private void hideMenu() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(dashboard_slider);
        slide.setToX(-dashboard_slider.getWidth());
        slide.play();

        isMenuVisible = false;

        overlayPane.setVisible(false);
    }

    public void logout() throws IOException {
        SwitchForms switchForms = new SwitchForms();
        switchForms.Logout((Stage) logout_btn.getScene().getWindow());
    }

    private void switchForm(AnchorPane targetForm, JFXButton selectedButton) {
        // Hide all forms
        dashboard_form.setVisible(false);
        bookedFlights_form.setVisible(false);
        flightManager_form.setVisible(false);
        ticketRecords_form.setVisible(false);
        sales_form.setVisible(false);
        customSupp_form.setVisible(false);

        // Show the selected form
        targetForm.setVisible(true);

        // Update button styles
        if (currentSelectedButton != null) {
            currentSelectedButton.getStyleClass().remove("selected-button");
        }

        currentSelectedButton = selectedButton;
        currentSelectedButton.getStyleClass().add("selected-button");
    }

    private void switchForm(AnchorPane targetForm, Label selectedLabel, Label unselectedLabel) {
        // Hide all forms
        fm_managerForm.setVisible(false);
        fm_recordsForm.setVisible(false);

        // Show the selected form
        targetForm.setVisible(true);

        // Update label styles
        selectedLabel.setTextFill(Color.WHITE);
        unselectedLabel.setTextFill(Color.web("#a18b8b"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ensure the menu is initially closed
        dashboard_slider.setTranslateX(-210);
        overlayPane.setVisible(false);

        // Add event handlers to the overlayPane and topPane
        overlayPane.setOnMouseClicked(event -> {
            if (isMenuVisible) {
                hideMenu();
            }
        });

        topPane.setOnMouseClicked(event -> {
            if (isMenuVisible) {
                hideMenu();
            }
        });

        // Set the dashboard_form as the default visible form
        dashboard_form.setVisible(true);

        // Apply the selected-button style to the dashboardForm_btn
        dashboardForm_btn.getStyleClass().add("selected-button");

        // Create a reference to the currently selected button
        currentSelectedButton = dashboardForm_btn;

        // Add button click event handlers
        dashboardForm_btn.setOnAction(e -> switchForm(dashboard_form, dashboardForm_btn));
        bookedForm_btn.setOnAction(e -> switchForm(bookedFlights_form, bookedForm_btn));
        ticketForm_btn.setOnAction(e -> switchForm(ticketRecords_form, ticketForm_btn));
        salesForm_btn.setOnAction(e -> switchForm(sales_form, salesForm_btn));
        flightManForm_btn.setOnAction(e -> switchForm(flightManager_form, flightManForm_btn));
        customerSupp_btn.setOnAction(e -> switchForm(customSupp_form, customerSupp_btn));

        // Add button click event handlers
        fm_managerBtn.setOnMouseClicked(e -> switchForm(fm_managerForm, fm_managerBtn, fm_recordsBtn));
        fm_recordsBtn.setOnMouseClicked(e -> switchForm(fm_recordsForm, fm_recordsBtn, fm_managerBtn));

        // Set the default form to fm_managerForm
        switchForm(fm_managerForm, fm_managerBtn, fm_recordsBtn);

    }
}
