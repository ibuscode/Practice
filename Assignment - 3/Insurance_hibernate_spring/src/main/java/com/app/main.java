package com.app;

import com.app.Exceptions.RecordNotFoundException;
import com.app.config.AppConfig;
import com.app.dao.AuthDao;
import com.app.dao.PolicyDao;
import com.app.dao_impl.AuthDaoImpl;
import com.app.dao_impl.PolicyDaoImpl;
import com.app.enums.StatusType;
import com.app.enums.VehicleType;
import com.app.model.Policy;
import com.app.model.User;
import jakarta.persistence.NoResultException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        AuthDao authDao = context.getBean(AuthDaoImpl.class);
        PolicyDao policyDao = context.getBean(PolicyDaoImpl.class);
        Scanner sc = new Scanner(System.in);

        System.out.println("----------SupportFlow: LOGIN---------");
        System.out.println("Enter Username ");
        String username = sc.nextLine();
        System.out.println("Enter Password ");
        String password = sc.nextLine();

        try {
            User user = authDao.login(username, password);

            switch (user.getRole().toString()) {
                case "CUSTOMER":
                    System.out.println("Customer Menu");
                    System.out.println("WELCOME " + username);
                    System.out.println("1.Apply for Policy");
                    System.out.println("2.Add Vehicle");
                    System.out.println("3.Update Personal Info");
                    System.out.println("4.Cancel the policy");
                    System.out.println("5.View all Policy");
                    int customerOp = Integer.parseInt(sc.nextLine());

                    switch (customerOp) {
                        case 1:
                            System.out.println("Enter Vehicle Id");
                            int vehicleId = Integer.parseInt(sc.nextLine());
                            System.out.println("Enter Policy Number");
                            String policyNumber = sc.nextLine();
                            System.out.println("Enter Premium Amount");
                            float premiumAmount = Float.parseFloat(sc.nextLine());
                            policyDao.applyForPolicy(user.getId(), vehicleId, policyNumber, premiumAmount);
                            System.out.println("Policy applied successfully");
                            break;

                        case 2:
                            System.out.println("Enter Vehicle Registration Number");
                            String regNo = sc.nextLine();
                            System.out.println("Enter Vehicle Type: TRUCK, MOTORCYCLE, CAMPERVAN");
                            VehicleType vehicleType = VehicleType.valueOf(sc.nextLine().toUpperCase());
                            policyDao.addVehicle(user.getId(), regNo, vehicleType);
                            System.out.println("Vehicle added successfully");
                            break;

                        case 3:
                            System.out.println("Enter Customer Name");
                            String name = sc.nextLine();
                            System.out.println("Enter Customer Address");
                            String address = sc.nextLine();
                            System.out.println("Enter Age");
                            int age = Integer.parseInt(sc.nextLine());
                            System.out.println("Enter Aadhaar Number");
                            long aadhaarNumber = Long.parseLong(sc.nextLine());
                            System.out.println("Enter Pan Number");
                            long panNumber = Long.parseLong(sc.nextLine());
                            policyDao.updatePersonalInfo(user.getId(), name, address, age, aadhaarNumber, panNumber);
                            System.out.println("Personal info updated successfully");
                            break;

                        case 4:
                            System.out.println("Enter Policy Id");
                            int cancelPolicyId = Integer.parseInt(sc.nextLine());
                            policyDao.cancelPolicy(user.getId(), cancelPolicyId);
                            System.out.println("Policy cancelled successfully");
                            break;

                        case 5:
                            List<Policy> customerPolicies = policyDao.viewCustomerPolicies(user.getId());
                            printPolicies(customerPolicies);
                            break;

                        default:
                            System.out.println("Invalid option");
                    }
                    break;

                case "OFFICER":
                    System.out.println("Officer Menu");
                    System.out.println("WELCOME " + username);
                    System.out.println("1.View My Policies");
                    System.out.println("2.View Policy By Customer");
                    System.out.println("3.Filter Policy By Status");
                    System.out.println("4.Update Policy Status and Date");
                    int officerOp = Integer.parseInt(sc.nextLine());

                    switch (officerOp) {
                        case 1:
                            List<Policy> officerPolicies = policyDao.viewOfficerPolicies(user.getId());
                            printPolicies(officerPolicies);
                            break;

                        case 2:
                            System.out.println("Enter Customer Id");
                            int customerId = Integer.parseInt(sc.nextLine());
                            List<Policy> policiesByCustomer = policyDao.viewPolicyByCustomer(customerId);
                            printPolicies(policiesByCustomer);
                            break;

                        case 3:
                            System.out.println("Enter Status: PROPOSAL_SUBMITTED, QUOTE_GENERATED, ACTIVE, EXPIRED");
                            StatusType statusType = StatusType.valueOf(sc.nextLine().toUpperCase());
                            List<Policy> policiesByStatus = policyDao.filterPolicyByStatus(statusType);
                            printPolicies(policiesByStatus);
                            break;

                        case 4:
                            System.out.println("Enter Policy Id");
                            int policyId = Integer.parseInt(sc.nextLine());
                            System.out.println("Enter Status: PROPOSAL_SUBMITTED, QUOTE_GENERATED, ACTIVE, EXPIRED");
                            StatusType newStatus = StatusType.valueOf(sc.nextLine().toUpperCase());
                            System.out.println("Enter Start Date yyyy-MM-dd");
                            LocalDate startDate = LocalDate.parse(sc.nextLine());
                            System.out.println("Enter End Date yyyy-MM-dd");
                            LocalDate endDate = LocalDate.parse(sc.nextLine());
                            policyDao.updatePolicyStatusAndDate(policyId, newStatus, startDate, endDate);
                            System.out.println("Policy updated successfully");
                            break;

                        default:
                            System.out.println("Invalid option");
                    }
                    break;

                case "ADMIN":
                    System.out.println("Admin Menu");
                    System.out.println("WELCOME " + username);
                    System.out.println("1.View All Users");
                    System.out.println("2.View All Policies");
                    System.out.println("3.Delete user");
                    int adminOp = Integer.parseInt(sc.nextLine());

                    switch (adminOp) {
                        case 1:
                            List<User> users = policyDao.viewAllUsers();
                            printUsers(users);
                            break;

                        case 2:
                            List<Policy> allPolicies = policyDao.viewAllPolicies();
                            printPolicies(allPolicies);
                            break;

                        case 3:
                            System.out.println("Enter User Id");
                            int userId = Integer.parseInt(sc.nextLine());
                            policyDao.deleteUser(userId);
                            System.out.println("User deleted successfully");
                            break;

                        default:
                            System.out.println("Invalid option");
                    }
                    break;
            }
        } catch (NoResultException e) {
            System.out.println("Invalid Credentials");
        } catch (RecordNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter correct number");
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter correct enum value");
        } catch (DateTimeParseException e) {
            System.out.println("Please enter date in yyyy-MM-dd format");
        } catch (Exception e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        } finally {
            context.close();
            sc.close();
        }
    }

    public static void printPolicies(List<Policy> policies) {
        if (policies.isEmpty()) {
            System.out.println("No policies found");
        } else {
            for (Policy policy : policies) {
                System.out.println(policy);
            }
        }
    }

    public static void printUsers(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found");
        } else {
            for (User user : users) {
                System.out.println("User Id: " + user.getId());
                System.out.println("Username: " + user.getUserName());
                System.out.println("Role: " + user.getRole());
                System.out.println("----------------------");
            }
        }
    }
}
