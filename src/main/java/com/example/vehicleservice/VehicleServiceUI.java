package com.example.vehicleservice;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class VehicleServiceUI extends JFrame {
    private final ServiceManager manager;

    private final JTextField customerNameField = new JTextField(20);
    private final JTextField customerEmailField = new JTextField(20);
    private final JTextField customerPhoneField = new JTextField(20);
    private final JTextField customerVehicleIdField = new JTextField(20);
    private final JTextField customerMakeField = new JTextField(20);
    private final JTextField customerModelField = new JTextField(20);
    private final JTextField customerYearField = new JTextField(6);
    private final JTextField customerDateField = new JTextField(10);
    private final JTextArea customerDescriptionArea = new JTextArea(4, 30);
    private final JTextArea customerBookingsArea = new JTextArea(12, 60);

    private final JTextField adminMakeField = new JTextField(20);
    private final JTextField adminModelField = new JTextField(20);
    private final JTextField adminYearField = new JTextField(6);
    private final JTextField adminOwnerField = new JTextField(20);
    private final JTextField adminVehicleIdField = new JTextField(20);
    private final JTextField adminDateField = new JTextField(10);
    private final JTextField adminDescriptionField = new JTextField(40);
    private final JTextField adminCostField = new JTextField(8);
    private final JTextField adminBookingIdField = new JTextField(12);
    private final JTextField adminBookingCostField = new JTextField(8);

    private final JTextArea adminVehiclesArea = new JTextArea(10, 60);
    private final JTextArea adminRecordsArea = new JTextArea(10, 60);
    private final JTextArea adminBookingsArea = new JTextArea(10, 60);
    private final JTextArea adminCustomersArea = new JTextArea(8, 60);

    public VehicleServiceUI(ServiceManager manager) {
        super("Vehicle Service Management");
        this.manager = manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildUI();
        refreshAll();
    }

    private void buildUI() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Customer", createCustomerPanel());
        tabbedPane.addTab("Admin", createAdminPanel());
        add(tabbedPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addLabelAndComponent(form, gbc, row++, "Customer name:", customerNameField);
        addLabelAndComponent(form, gbc, row++, "Email:", customerEmailField);
        addLabelAndComponent(form, gbc, row++, "Phone:", customerPhoneField);
        addLabelAndComponent(form, gbc, row++, "Vehicle ID (leave blank to add new vehicle):", customerVehicleIdField);
        addLabelAndComponent(form, gbc, row++, "New vehicle make:", customerMakeField);
        addLabelAndComponent(form, gbc, row++, "New vehicle model:", customerModelField);
        addLabelAndComponent(form, gbc, row++, "New vehicle year:", customerYearField);
        addLabelAndComponent(form, gbc, row++, "Service date (YYYY-MM-DD):", customerDateField);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        form.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane descriptionScroll = new JScrollPane(customerDescriptionArea);
        form.add(descriptionScroll, gbc);
        gbc.fill = GridBagConstraints.NONE;

        JButton bookButton = new JButton("Book Service");
        bookButton.addActionListener(e -> bookService());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(bookButton);

        customerBookingsArea.setEditable(false);
        JScrollPane bookingsScroll = new JScrollPane(customerBookingsArea);
        bookingsScroll.setBorder(BorderFactory.createTitledBorder("Existing Bookings"));

        panel.add(form, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(bookingsScroll, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        topPanel.add(createAdminVehiclePanel());
        topPanel.add(createAdminServicePanel());

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(createAdminBookingPanel(), BorderLayout.NORTH);
        bottomPanel.add(createAdminListsPanel(), BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAdminVehiclePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Vehicle"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addLabelAndComponent(panel, gbc, row++, "Make:", adminMakeField);
        addLabelAndComponent(panel, gbc, row++, "Model:", adminModelField);
        addLabelAndComponent(panel, gbc, row++, "Year:", adminYearField);
        addLabelAndComponent(panel, gbc, row++, "Owner name:", adminOwnerField);

        JButton addVehicleButton = new JButton("Create Vehicle");
        addVehicleButton.addActionListener(e -> addVehicleAdmin());
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(addVehicleButton, gbc);
        return panel;
    }

    private JPanel createAdminServicePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Add Service Record"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addLabelAndComponent(panel, gbc, row++, "Vehicle ID:", adminVehicleIdField);
        addLabelAndComponent(panel, gbc, row++, "Service date (YYYY-MM-DD):", adminDateField);
        addLabelAndComponent(panel, gbc, row++, "Description:", adminDescriptionField);
        addLabelAndComponent(panel, gbc, row++, "Cost:", adminCostField);

        JButton addRecordButton = new JButton("Create Record");
        addRecordButton.addActionListener(e -> addServiceRecordAdmin());
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(addRecordButton, gbc);
        return panel;
    }

    private JPanel createAdminBookingPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Complete Booking"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        int row = 0;
        addLabelAndComponent(panel, gbc, row++, "Booking ID:", adminBookingIdField);
        addLabelAndComponent(panel, gbc, row++, "Record cost:", adminBookingCostField);

        JButton completeButton = new JButton("Complete Booking");
        completeButton.addActionListener(e -> completeBookingAdmin());
        gbc.gridx = 1;
        gbc.gridy = row;
        panel.add(completeButton, gbc);
        return panel;
    }

    private JPanel createAdminListsPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Admin Dashboard"));

        adminVehiclesArea.setEditable(false);
        adminRecordsArea.setEditable(false);
        adminBookingsArea.setEditable(false);
        adminCustomersArea.setEditable(false);

        panel.add(new JScrollPane(adminVehiclesArea));
        panel.add(new JScrollPane(adminRecordsArea));
        panel.add(new JScrollPane(adminBookingsArea));
        panel.add(new JScrollPane(adminCustomersArea));
        return panel;
    }

    private void addLabelAndComponent(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    private void bookService() {
        try {
            String name = customerNameField.getText().trim();
            String email = customerEmailField.getText().trim();
            String phone = customerPhoneField.getText().trim();
            String vehicleId = customerVehicleIdField.getText().trim();
            String make = customerMakeField.getText().trim();
            String model = customerModelField.getText().trim();
            String yearText = customerYearField.getText().trim();
            String dateText = customerDateField.getText().trim();
            String description = customerDescriptionArea.getText().trim();

            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || description.isEmpty() || dateText.isEmpty()) {
                showError("Please fill in customer details, date, and description.");
                return;
            }

            LocalDate requestedDate;
            try {
                requestedDate = LocalDate.parse(dateText);
            } catch (DateTimeParseException ex) {
                showError("Invalid service date. Use YYYY-MM-DD.");
                return;
            }

            Customer customer = manager.findCustomerByEmail(email).orElseGet(() -> manager.addCustomer(name, email, phone));

            if (vehicleId.isEmpty()) {
                if (make.isEmpty() || model.isEmpty() || yearText.isEmpty()) {
                    showError("Enter a vehicle ID or fill in new vehicle details.");
                    return;
                }
                int year = Integer.parseInt(yearText);
                Vehicle vehicle = manager.addVehicle(make, model, year, name);
                vehicleId = vehicle.getId();
            } else {
                if (manager.findVehicleById(vehicleId).isEmpty()) {
                    showError("Vehicle not found with ID: " + vehicleId);
                    return;
                }
            }

            Booking booking = manager.addBooking(customer.getId(), vehicleId, requestedDate, description);
            showInfo("Booking created: " + booking.getId());
            clearCustomerFields();
            refreshAll();
        } catch (NumberFormatException ex) {
            showError("Year must be a number.");
        }
    }

    private void addVehicleAdmin() {
        try {
            String make = adminMakeField.getText().trim();
            String model = adminModelField.getText().trim();
            String yearText = adminYearField.getText().trim();
            String owner = adminOwnerField.getText().trim();
            if (make.isEmpty() || model.isEmpty() || yearText.isEmpty() || owner.isEmpty()) {
                showError("Please fill in all vehicle fields.");
                return;
            }
            int year = Integer.parseInt(yearText);
            Vehicle vehicle = manager.addVehicle(make, model, year, owner);
            showInfo("Vehicle added: " + vehicle.getId());
            clearAdminVehicleFields();
            refreshAll();
        } catch (NumberFormatException ex) {
            showError("Year must be a valid number.");
        }
    }

    private void addServiceRecordAdmin() {
        try {
            String vehicleId = adminVehicleIdField.getText().trim();
            String dateText = adminDateField.getText().trim();
            String description = adminDescriptionField.getText().trim();
            String costText = adminCostField.getText().trim();
            if (vehicleId.isEmpty() || dateText.isEmpty() || description.isEmpty() || costText.isEmpty()) {
                showError("Please fill in all service record fields.");
                return;
            }
            if (manager.findVehicleById(vehicleId).isEmpty()) {
                showError("Vehicle not found with ID: " + vehicleId);
                return;
            }
            LocalDate serviceDate = LocalDate.parse(dateText);
            double cost = Double.parseDouble(costText);
            ServiceRecord record = manager.addServiceRecord(vehicleId, serviceDate, description, cost);
            showInfo("Service record added: " + record.getId());
            clearAdminServiceFields();
            refreshAll();
        } catch (DateTimeParseException ex) {
            showError("Invalid service date. Use YYYY-MM-DD.");
        } catch (NumberFormatException ex) {
            showError("Cost must be numeric.");
        }
    }

    private void completeBookingAdmin() {
        try {
            String bookingId = adminBookingIdField.getText().trim();
            String costText = adminBookingCostField.getText().trim();
            if (bookingId.isEmpty() || costText.isEmpty()) {
                showError("Please enter booking ID and cost.");
                return;
            }
            double cost = Double.parseDouble(costText);
            Booking booking = manager.findBookingById(bookingId).orElse(null);
            if (booking == null) {
                showError("Booking not found: " + bookingId);
                return;
            }
            if (booking.getStatus() != BookingStatus.PENDING) {
                showError("Booking is already completed.");
                return;
            }
            manager.completeBooking(bookingId, cost);
            showInfo("Booking completed and service record created.");
            adminBookingIdField.setText("");
            adminBookingCostField.setText("");
            refreshAll();
        } catch (NumberFormatException ex) {
            showError("Cost must be a number.");
        } catch (IllegalArgumentException ex) {
            showError(ex.getMessage());
        }
    }

    private void refreshAll() {
        refreshVehicles();
        refreshRecords();
        refreshBookings();
        refreshCustomers();
    }

    private void refreshVehicles() {
        StringBuilder builder = new StringBuilder("Vehicles:\n");
        for (Vehicle vehicle : manager.listVehicles()) {
            builder.append(vehicle).append('\n');
        }
        adminVehiclesArea.setText(builder.toString());
    }

    private void refreshRecords() {
        StringBuilder builder = new StringBuilder("Service Records:\n");
        for (ServiceRecord record : manager.listServiceRecords()) {
            builder.append(record).append('\n');
        }
        adminRecordsArea.setText(builder.toString());
    }

    private void refreshBookings() {
        StringBuilder builder = new StringBuilder("Bookings:\n");
        for (Booking booking : manager.listBookings()) {
            builder.append(booking).append('\n');
        }
        adminBookingsArea.setText(builder.toString());

        StringBuilder customerBuilder = new StringBuilder("Bookings:\n");
        for (Booking booking : manager.listBookings()) {
            customerBuilder.append(booking).append('\n');
        }
        customerBookingsArea.setText(customerBuilder.toString());
    }

    private void refreshCustomers() {
        StringBuilder builder = new StringBuilder("Customers:\n");
        for (Customer customer : manager.listCustomers()) {
            builder.append(customer).append('\n');
        }
        adminCustomersArea.setText(builder.toString());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void clearCustomerFields() {
        customerVehicleIdField.setText("");
        customerMakeField.setText("");
        customerModelField.setText("");
        customerYearField.setText("");
        customerDateField.setText("");
        customerDescriptionArea.setText("");
    }

    private void clearAdminVehicleFields() {
        adminMakeField.setText("");
        adminModelField.setText("");
        adminYearField.setText("");
        adminOwnerField.setText("");
    }

    private void clearAdminServiceFields() {
        adminVehicleIdField.setText("");
        adminDateField.setText("");
        adminDescriptionField.setText("");
        adminCostField.setText("");
    }
}
